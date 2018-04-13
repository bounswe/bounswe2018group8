""" This code is for giving people breaking news in twitter"""
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
query = "Son Dakika"
tweets = api.search(q=query, lang='tr')
tweets_dict = [tweet._json['text'] for tweet in tweets]
for t in tweets_dict:
     print(t + "\n'")
api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True)
query = "Breaking News"
tweets = api.search(q=query, lang='en')
tweets_dict = [tweet._json['text'] for tweet in tweets]
for t in tweets_dict:
     print(t + "\n'")
query = "dernières nouvelles"
tweets = api.search(q=query, lang='fr')
tweets_dict = [tweet._json['text'] for tweet in tweets]
for t in tweets_dict:
     print(t + "\n'")
query = "aktuelle Nachrichten"
tweets = api.search(q=query, lang='de')
tweets_dict = [tweet._json['text'] for tweet in tweets]
for t in tweets_dict:
     print(t + "\n'")
