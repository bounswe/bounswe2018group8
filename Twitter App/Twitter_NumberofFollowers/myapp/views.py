from django.shortcuts import render
from .models import User
import tweepy

# Create variables for each key, secret, token
consumer_key = 'hD31w9jRVvxaTKt48f6hI8Q83'
consumer_secret = 'Y3zLK7R3X1ITLuX61LKJwkV4llPsO8SmSBkueE7Gf7XBco0BWo'
access_token = '982118998719414272-WZ36m0cSsA1lRWBmSPrvkVkGd30n37X'
access_token_secret = 'kdXSbMZnzJAXN6SkF0WuINJKE4IWMCPTmw300pOQ6mq7D'

# Set up OAuth and integrate with API
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)


def user_list(request):
    if request.method == "POST":
        userName = request.POST.get("a", None)
        myuser = api.get_user(userName)
        followersNum2 = myuser.followers_count
        return render(request, 'myapp/user_list.html', {'followersNum2': followersNum2})
    else:
        return render(request, 'myapp/user_list.html', {})


