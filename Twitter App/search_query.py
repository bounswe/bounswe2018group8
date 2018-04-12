query = raw_input("Please enter a query. \n>>")

tweets = api.search(q=query, lang='tr')

tweets_dict = [tweet._json['text'] for tweet in tweets]
for t in tweets_dict:
	print("---------------"
    print(t + '\n')