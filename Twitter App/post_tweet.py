import tweepy
from tweepy import OAuthHandler

text = input("Please enter your tweet: \n>>")
new_tweet = api.update_status(status=text)
