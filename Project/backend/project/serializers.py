# Created by Ozan Kınasakal
from rest_framework import serializers
from .models import Project, Bid
from user.serializers import UserSerializer


class ProjectSerializer(serializers.ModelSerializer):
    client_id = serializers.IntegerField(source='client.id', required=False)
    client_username = serializers.CharField(source='client.username', required=False)
    freelancer_id = serializers.IntegerField(source='freelancer.id', default=None, required=False)
    freelancer_username = serializers.CharField(source='freelancer.username', default=None, required=False)
    status = serializers.CharField(required=False)

    class Meta():
        model = Project
        fields = '__all__'
        read_only_fields = ('id', 'client_id', 'client_username', 'freelancer_id', 'freelancer_username', 'average_bid', 'bid_count',)

    def create(self, validated_data):
        project = Project.objects.create(
            client = self.context['request'].user,
            freelancer = None,
            title = validated_data['title'],
            description = validated_data['description'],
            deadline = validated_data['deadline'],
            min_price = validated_data['min_price'],
            max_price = validated_data['max_price'],
            status = 'active'
        )
        return project


class BidSerializer(serializers.ModelSerializer):
    project_id = serializers.IntegerField(source='project.id', required=False)
    freelancer_id = serializers.IntegerField(source='freelancer.id', required=False)

    class Meta:
        model = Bid
        fields = ('id', 'amount', 'project_id', 'freelancer_id',)
        read_only_fields = ('id', 'project_id', 'freelancer_id',)
        
