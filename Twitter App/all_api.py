import tweepy
from tweepy import OAuthHandler
import pandas as pd
from pprint import pprint

def chain_follow():
    me = api.me()
    following_ids = api.friends_ids(id=me._json['id'])
    following_users = []
    for idx in enumerate(following_ids):
        following_users.append(api.get_user(id=idx[1]))
    numOfusers = len(following_ids)
    idsToFollow = []
    for idx in range(numOfusers):
        idsToFollow = api.friends_ids(id=following_users[idx]._json['id'])
    usersToFollow = []
    for idx in range(len(idsToFollow)):
        usersToFollow.append(api.create_friendship(id=idsToFollow[idx]))

def follow_back():
    follower_ids = api.followers_ids(id=me._json['id'])
    for idx in range(len(follower_ids)):
        api.create_friendship(id=follower_ids[idx])

def post_tweet(text):
  new_tweet = api.update_status(status=text)

def search_query(query):
	tweets = api.search(q=query)
	return tweets