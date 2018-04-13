import tweepy 
from tweepy import OAuthHandler
import pandas as pd
from pprint import pprint
TWITTER_CONSUMER_KEY="hD31w9jRVvxaTKt48f6hI8Q83"
TWITTER_CONSUMER_SECRET="Y3zLK7R3X1ITLuX61LKJwkV4llPsO8SmSBkueE7Gf7XBco0BWo"
TWITTER_ACCESS_TOKEN="982118998719414272-WZ36m0cSsA1lRWBmSPrvkVkGd30n37X"
TWITTER_ACCESS_SECRET="kdXSbMZnzJAXN6SkF0WuINJKE4IWMCPTmw300pOQ6mq7D"
auth = OAuthHandler(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET)
auth.set_access_token(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_SECRET)
api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True)
tweetlocation = api.search(geocode = '41.08356,29.0506,1km')
tweets_dict1 = [tweet._json['text'] for tweet in tweetlocation]
for t in tweets_dict1:
     print(t + "\n'")
