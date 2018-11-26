# Created by Umut Barış Öztunç
from rest_framework import serializers
from rest_auth.serializers import UserDetailsSerializer
from rest_auth.registration.serializers import RegisterSerializer
from .models import User, Client, Freelancer, Skill, Project, Bid
from django.conf import settings
from .validators import FirstNameValidator, LastNameValidator, SkillValidator

# Author: Umut Barış Öztunç, Ozan Kınasakal

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


class ClientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Client
        fields = ()


class FreelancerSerializer(serializers.ModelSerializer):
    skills = SkillSerializer(many=True)
    class Meta:
        model = Freelancer
        fields = ('skills',)


class UserSerializer(UserDetailsSerializer):
    projects = serializers.PrimaryKeyRelatedField(many=True, queryset=Project.objects.all())
    class Meta(UserDetailsSerializer.Meta):
        fields = UserDetailsSerializer.Meta.fields + ('is_client', 'bio','projects',)
        read_only_fields = UserDetailsSerializer.Meta.read_only_fields + ('pk', 'username', 'is_client','projects',)

    def __init__(self, *args, **kwargs):
        super(UserSerializer, self).__init__(*args, **kwargs)
        if self.instance.is_client:
            self.fields['profile'] = ClientSerializer(source='client')
        else:
            self.fields['profile'] = FreelancerSerializer(source='freelancer')

    def update(self, instance, validated_data):
        if instance.is_client:
            profile_data = validated_data.pop('client', {})
            instance = super(UserSerializer, self).update(instance, validated_data)
        else:
            profile_data = validated_data.pop('freelancer', {})
            instance = super(UserSerializer, self).update(instance, validated_data)
            instance.freelancer.skills.clear()
            skills_data = profile_data.get('skills', {})
            for skill_data in skills_data:
                skill_name = skill_data['name'].lower()
                try:
                    skill = Skill.objects.get(name=skill_name)
                except Skill.DoesNotExist:
                    skill = Skill.objects.create(name=skill_name)
                instance.freelancer.skills.add(skill)
        return instance


class ProjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = Project
        fields = "__all__"
        read_only_fields =('client',)

class ProjectCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Project
        exclude = ('client','freelancer','status',)

class BidSerializer(serializers.ModelSerializer):
    class Meta:
        model = Bid
        fields = "__all__"
