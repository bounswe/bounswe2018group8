# Created by Umut Baris Oztunc
from .models import Bid
from django.db.models.signals import pre_save, pre_delete
from django.dispatch import receiver

# Updates average bid and bid counts for the projects whenever a bid is created or updated.
# Author: Umut Baris Oztunc
@receiver(pre_save, sender=Bid)
def update_bid_info_on_save(sender, instance, *args, **kwargs):
    project = instance.project
    total = project.bid_count * project.average_bid
    total += instance.amount
    if instance.id:
        old_bid = Bid.objects.get(id=instance.id)
        if project.id == old_bid.project.id:
            total -= old_bid.amount
        else:
            old_project = old_bid.project
            old_total = old_project.bid_count * old_project.average_bid
            old_total -= old_bid.amount
            old_project.bid_count -= 1
            old_project.average_bid = old_total / old_project.bid_count
            old_project.save()
            project.bid_count += 1  
    else:
        project.bid_count += 1
    project.average_bid = total / project.bid_count
    project.save()
    

# Updates average bid and bid counts for the projects whenever a bid is deleted.
# Author: Umut Baris Oztunc
@receiver(pre_delete, sender=Bid)
def update_bid_info_on_delete(sender, instance, *args, **kwargs):
    project = instance.project
    total = project.bid_count * project.average_bid
    total -= instance.amount
    project.bid_count -= 1
    if project.bid_count == 0:
        project.average_bid = 0.0
    else:
        project.average_bid = total / project.bid_count
    project.save()
