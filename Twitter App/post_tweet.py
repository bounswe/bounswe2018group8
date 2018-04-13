import tweepy
from tweepy import OAuthHandler

def post_tweet(text):
  new_tweet = api.update_status(status=text)
