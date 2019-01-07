from django.db import models
from user.models import User

#Author Ozan Kinasakal
class Project(models.Model):
    client = models.ForeignKey(User, related_name='projects', on_delete=models.CASCADE)
    freelancer = models.ForeignKey(User, on_delete=models.SET_NULL, null=True)
    title = models.CharField(max_length=100)
    description = models.TextField()
    deadline = models.DateTimeField()
    max_price = models.IntegerField()
    min_price = models.IntegerField()
    status = models.CharField(max_length=30)
    average_bid = models.FloatField(default=0.0)
    bid_count = models.IntegerField(default=0)
    image = models.ImageField(
        upload_to = 'images/projects', 
        default = 'images/projects/no-image.jpg'
    )
    


class Bid(models.Model):
    amount = models.FloatField()
    project = models.ForeignKey(Project, related_name='bids', on_delete=models.CASCADE)
    freelancer = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return '%s: %f' % (self.freelancer.username,self.amount)
