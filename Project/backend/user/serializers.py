# Created by Umut Barış Öztunç
from rest_framework import serializers
from rest_auth.serializers import UserDetailsSerializer
from rest_auth.registration.serializers import RegisterSerializer
from .models import User, Skill
from project.models import Project
from django.conf import settings
from .validators import FirstNameValidator, LastNameValidator, SkillValidator

# Extended the RegisterSerializer from REST AUTH module to add custom fields and validations.
# Author: Umut Baris Oztunc
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

    def get_cleaned_data(self):
        cleaned_data = super(RegisterSerializer, self).get_cleaned_data()
        cleaned_data['first_name'] = self.validated_data.get('first_name', '')
        cleaned_data['last_name'] = self.validated_data.get('last_name', '')
        return cleaned_data
        

# Serializer for Skill model.
# Author: Umut Baris Oztunc
class SkillSerializer(serializers.ModelSerializer):
    class Meta:
        model = Skill
        fields = ('name',)
        extra_kwargs = {
            'name': {'validators': [SkillValidator()]},
        }



# Extended the UserDetailsSerializer from REST AUTH module to add custom fields and override the update function.
# Author: Umut Baris Oztunc
class UserSerializer(UserDetailsSerializer):
    skills = SkillSerializer(many=True, required=False)
    balance = serializers.FloatField(required=False)
    avatar = serializers.ImageField(required=False, read_only=True)

    class Meta(UserDetailsSerializer.Meta):
        fields = ('id',) + UserDetailsSerializer.Meta.fields + ('bio', 'skills', 'balance', 'avatar',)
        read_only_fields = UserDetailsSerializer.Meta.read_only_fields + ('id', 'username', 'email', 'balance', 'avatar',)
        
    def __init__(self, *args, **kwargs):
        super(UserSerializer, self).__init__(*args, **kwargs)
        del self.fields['pk']
        if not kwargs['context'].get('self', False):
            del self.fields['balance']
        
    def to_representation(self, instance):
        ret = super(UserSerializer, self).to_representation(instance)
        ret['skills'] = [skill['name'] for skill in ret['skills']]
        return ret
        
    def to_internal_value(self, data):
        skills_data = data.get('skills')
        if isinstance(skills_data, list):
            skills = []
            for skill in skills_data:
                skills.append({'name': skill})
            data['skills'] = skills
        return super(UserSerializer, self).to_internal_value(data)

    def update(self, instance, validated_data):
        skills = validated_data.pop('skills', None)
        instance = super(UserSerializer, self).update(instance, validated_data)
        if skills != None:
            instance.skills.clear()
            for skill in skills:
                name = skill.get('name')
                if name:
                    try:
                        s = Skill.objects.get(name=name)
                    except Skill.DoesNotExist:
                        s = Skill.objects.create(name=name)
                    instance.skills.add(s)
        return instance



