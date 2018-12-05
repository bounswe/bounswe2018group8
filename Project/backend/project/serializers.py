# Created by Ozan Kınasakal
from rest_framework import serializers
from .models import Project, Bid
from user.serializers import UserSerializer


class ProjectSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='client.user.username')
    class Meta():
        model = Project
        fields = '__all__'
        read_only_fields =('client','username',)

class ProjectCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Project
        exclude = ('client','freelancer','status',)

class BidSerializer(serializers.ModelSerializer):
    class Meta:
        model = Bid
        fields = "__all__"
