package com.example.ns.model;

public class Tweet {
	private String id_str, created_at, text;
	private int favorite_count, retweet_count;
	private User user;
	
	/**
	 * Creates a new Tweet Object.
	 * @param id_str			The tweet id
	 * @param created_at		Timestamp of when the tweet was created
	 * @param text				The text of the tweet
	 * @param favorite_count	The favorite count of the tweet
	 * @param retweet_count		The retweet count of the tweet
	 * @param user				The User that created this tweet
	 */
	public Tweet(String id_str, String created_at, String text,
			int favorite_count, int retweet_count, User user) {
		super();
		this.id_str = id_str;
		this.created_at = created_at.substring(0,11)+ created_at.substring(26, 30);
		this.text = text;
		this.favorite_count = favorite_count;
		this.retweet_count = retweet_count;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Tweet{ ID:" + id_str + ", Created_at:" + created_at + ", Text:"
				+ text + ", favorite_count:" + favorite_count
				+ ", retweet_count:" + retweet_count + ", " + user + "}";
	}
	
	/**
	 * 
	 * @return the User that created this tweet
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * 
	 * @return the timestamp off this tweet when it was created
	 */
	public String getCreatedAt(){
		return created_at;
	}
	
	/**
	 * 
	 * @return the text that is in this tweet
	 */
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}
	
	/**
	 * 
	 * @return the tweet id
	 */
	public String getIdStr(){
		return id_str;
	}

}