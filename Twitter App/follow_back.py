import tweepy
from tweepy import OAuthHandler
import pandas as pd
from pprint import pprint

follower_ids = api.followers_ids(id=me._json['id'])
for idx in range(len(follower_ids)):
    api.create_friendship(id=follower_ids[idx])