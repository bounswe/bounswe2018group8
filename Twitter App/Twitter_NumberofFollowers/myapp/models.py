from django.db import models

# Write a tweet to push to our Twitter account
#tweet = 'Hello, world!'
#api.update_status(status=tweet)


class User(models.Model):
    userName = models.CharField(max_length=200)
    followersNum = models.CharField(max_length=200)

    def show(self):
        self.save()

    def __str__(self):
        return self.userName
