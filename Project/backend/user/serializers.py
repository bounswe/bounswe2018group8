# Created by Umut Barış Öztunç
from rest_framework import serializers
from rest_auth.serializers import UserDetailsSerializer
from rest_auth.registration.serializers import RegisterSerializer
from .models import User, Client, Freelancer, Skill
from project.models import Project
from django.conf import settings
from .validators import FirstNameValidator, LastNameValidator, SkillValidator


class RegisterSerializer(RegisterSerializer):

    first_name = serializers.CharField(
        max_length=User._meta.get_field('first_name').max_length,
        required=True,
        validators=[FirstNameValidator()]
    )

    last_name = serializers.CharField(
        max_length=User._meta.get_field('last_name').max_length,
        required=True,
        validators=[LastNameValidator()]
    )

    email = serializers.EmailField(
        max_length=User._meta.get_field('email').max_length,
        required=settings.ACCOUNT_EMAIL_REQUIRED
    )

    is_client = serializers.BooleanField(required=True)

    def custom_signup(self, request, user):
        if user.is_client:
            client = Client.objects.create(user=user)
        else:
            freelancer = Freelancer.objects.create(user=user)
        return user

    def get_cleaned_data(self):
        cleaned_data = super(RegisterSerializer, self).get_cleaned_data()
        cleaned_data['first_name'] = self.validated_data.get('first_name', '')
        cleaned_data['last_name'] = self.validated_data.get('last_name', '')
        cleaned_data['is_client'] = self.validated_data.get('is_client', '')
        return cleaned_data
        

class SkillSerializer(serializers.ModelSerializer):
    class Meta:
        model = Skill
        fields = ('name',)
        extra_kwargs = {
            'name': {'validators': [SkillValidator()]},
        }


class UserSerializer(UserDetailsSerializer):
    skills = SkillSerializer(
        many=True,
        source='freelancer.skills'
    )
    
    class Meta(UserDetailsSerializer.Meta):
        fields = ('id',) + UserDetailsSerializer.Meta.fields + ('is_client', 'bio', 'skills', 'balance',)
        read_only_fields = UserDetailsSerializer.Meta.read_only_fields + ('id', 'username', 'email', 'is_client',)
        
    def __init__(self, *args, **kwargs):
        super(UserSerializer, self).__init__(*args, **kwargs)
        del self.fields['pk']
        if not kwargs['context'].get('self', False):
            del self.fields['balance']
        
    def to_representation(self, instance):
        ret = super(UserSerializer, self).to_representation(instance)
        if ret['is_client']:
            del ret['skills']
        else:
            ret['skills'] = [skill['name'] for skill in ret['skills']]
        return ret
        
    def to_internal_value(self, data):
        if data['skills']:
            skills = []
            for skill in data['skills']:
                skills.append({'name': skill})
            data['skills'] = skills
        return super(UserSerializer, self).to_internal_value(data)
        
                
    
    def update(self, instance, validated_data):
        freelancer = validated_data.pop('freelancer', None)
        instance = super(UserSerializer, self).update(instance, validated_data)
        if not instance.is_client:
            instance.freelancer.skills.clear()
            for skill in freelancer.get('skills', {}):
                name = skill.get('name')
                if name:
                    try:
                        s = Skill.objects.get(name=name)
                    except:
                        s = Skill.objects.create(name=name)
                    instance.freelancer.skills.add(s)
        return instance



