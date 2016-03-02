package com.example.ns.model;

import java.util.Scanner;

public class Tweet {
	private String id_str, created_at, text;
	private int favorite_count, retweet_count;
	private User user;

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

	public User getUser() {
		return user;
	}
	
	public String getCreatedAt(){
		return created_at;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}
	
	public String getIdStr(){
		return id_str;
	}

}