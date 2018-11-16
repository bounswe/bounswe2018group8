# Created by Umut Barış Öztunç
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
    
