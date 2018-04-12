import tweepy
from tweepy import OAuthHandler

text = input("Please enter a query. \n>>")
new_tweet = api.update_status(status=text)