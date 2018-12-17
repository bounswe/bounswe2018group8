# Created by Umut Barış Öztunç
from django.db import models
from django.contrib.auth.models import AbstractUser

class Skill(models.Model):
    name = models.CharField(unique=True, max_length=50)

    def __str__(self):
        return '%s' % (self.name)

class User(AbstractUser):
    bio = models.CharField(max_length=1000, blank=True)
    balance = models.FloatField(default=0.0)
    skills = models.ManyToManyField(Skill, related_name='users')

    
