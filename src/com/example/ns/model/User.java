package com.example.ns.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class User {
	private String name, screen_name, profile_image_url, id_str, description, url,
			locatie;
	private int followers_count, friends_count;
	private Drawable profileImage;

	public User(String name, String screen_name, String profile_image_url,
			String id_str, String description, String url, String locatie,
			int followers_count, int friends_count) {
		super();
		this.name = name;
		this.screen_name = screen_name;
		this.profile_image_url = profile_image_url;
		this.id_str = id_str;
		this.description = description;
		this.url = url;
		this.locatie = locatie;
		this.followers_count = followers_count;
		this.friends_count = friends_count;
	}

	public User(String name, String screen_name, String profile_image,
			String id_str, String description, String url) {
		super();
		this.name = name.trim();
		this.screen_name = screen_name.trim();
		this.profile_image_url = profile_image;
		this.id_str = id_str;
		this.description = description.trim();
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screen_name;
	}

	public String getIdstr() {
		return id_str;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getProfileImageUrl() {
		return profile_image_url;
	}


	@Override
	public String toString() {
		return "User{ Name: " + name + ", Screen name: " + screen_name
				+ ", ID: " + id_str + ", Desription: " + description
				+ ", Url: " + url + ", Location: " + locatie + ", Followers: "
				+ followers_count + ", Friends: " + friends_count + "}";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description=description;
	}

	public void setUrl(String url){
		this.url=url;
	}
	
	public Drawable getProfileImage(){
		return profileImage;
	}
	
	public void setProfileImage(Drawable profileImage){
		this.profileImage = profileImage;
	}
}
