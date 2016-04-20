package com.example.ns.model;

import android.graphics.drawable.Drawable;

public class User {
	private String name, screen_name, profile_image_url, id_str, description, url,
			locatie;
	private int followers_count, friends_count;
	private Drawable profileImage;
	
	/**
	 * Creates a new User
	 * @param name				The name of the user
	 * @param screen_name		The screen name of the user
	 * @param profile_image		The url that contains the profile image
	 * @param id_str			The id of the user
	 * @param description		The description of the user
	 * @param url				The url of the user
	 */
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
	
	/**
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return the screen name of the user
	 */
	public String getScreenName() {
		return screen_name;
	}
	
	/**
	 * 
	 * @return the id of the user
	 */
	public String getIdstr() {
		return id_str;
	}
	
	/**
	 * 
	 * @return the description of the user
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @return the url of the user
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 
	 * @return the url of the profile image
	 */
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
	
	/**
	 * 
	 * @param name, specific name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param description, specific description to be set
	 */
	public void setDescription(String description){
		this.description=description;
	}

	/**
	 * 
	 * @param url, specific url to be set
	 */
	public void setUrl(String url){
		this.url=url;
	}
		
	/**
	 * 
	 * @return the profile image of the user, in a Drawable object
	 */
	public Drawable getProfileImage(){
		return profileImage;
	}
	
	/**
	 * 
	 * @param profileImage, set the Drawable profile image
	 */
	public void setProfileImage(Drawable profileImage){
		this.profileImage = profileImage;
	}
}
