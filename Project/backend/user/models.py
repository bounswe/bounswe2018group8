# Created by Umut Barış Öztunç
# Author: Umut Barış Öztunç, Ozan Kınasakal
from django.db import models
from django.contrib.auth.models import AbstractUser

class Skill(models.Model):
    name = models.CharField(unique=True, max_length=50)

class User(AbstractUser):
    is_client = models.BooleanField(default=False)
    bio = models.CharField(max_length=1000, blank=True)

class Client(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)

class Freelancer(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    skills = models.ManyToManyField(Skill, related_name='freelancers')

class Project(models.Model):
    client = models.ForeignKey(User, related_name='projects', on_delete=models.CASCADE)
    freelancer = models.ForeignKey(Freelancer, on_delete=models.SET_NULL, null=True)
    title = models.CharField(max_length=100)
    description = models.TextField()
    deadline = models.DateTimeField()
    max_price = models.IntegerField()
    min_price = models.IntegerField()
    status = models.CharField(max_length=30)


class Bid(models.Model):
    amount = models.IntegerField()
    project = models.ForeignKey(Project, on_delete=models.CASCADE)
    freelancer = models.ForeignKey(Freelancer, on_delete=models.CASCADE)
