package com.example.ns.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import adapter.TweetAdapter;
import android.content.Context;
import android.util.Log;

import com.example.ns.model.Model.Timeline;
import com.example.ns.tasks.GetProfileTask;
import com.example.ns.tasks.GetTimeLineTask;
import com.example.ns.tasks.PostRetweetTask;
import com.example.ns.tasks.PostTweetTask;
import com.example.ns.tasks.PostUpdateProfile;
import com.example.ns.tasks.RequestTokenTask;
import com.example.ns.tasks.SearchTask;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class Model extends Observable{
	
	private static Model instance;
	
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private static final String CONSUMER_KEY = "nzTfxIn5kDXKaq9lpit7IB8qC";
	private static final String CONSUMER_SECRET = "ZVx1RSILHGULSsuFt7L6dqZG4OTbtik90ph3rm2QNRxgJEZjEm";
	private static final String OAUTH_REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String OAUTH_ACCESSTOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static String OAUTH_AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private static final String OAUTH_CALLBACK_URL = "http://www.saxion.nl/pgrtwitter/authenticated";
	
	private ArrayList<Tweet> home,user,mention,search;
	
	private Context context;
	
	private User currentUser;
	
	public enum Timeline{HOME,USER,MENTION}
	

	private Model(){
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(
				OAUTH_REQUEST_URL,
				OAUTH_ACCESSTOKEN_URL, 
				OAUTH_AUTHORIZE_URL);
	}
	
	public static Model getInstance(){
        if(instance == null)
        {
            instance =  new Model();
        }
        return instance;
    }

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public OAuthProvider getProvider() {
		return provider;
	}

	public static String getOauthAuthorizeUrl() {
		return OAUTH_AUTHORIZE_URL;
	}
	
	public static String getCallBackUrl(){
		return OAUTH_CALLBACK_URL;
	}
	
	public String getRequestUrl(){
		RequestTokenTask task = new RequestTokenTask(consumer, provider);
		try {
			return OAUTH_AUTHORIZE_URL = task.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return OAUTH_AUTHORIZE_URL;
	}
	
	public ArrayList<Tweet> getTimeline(Timeline timeline) {
		switch(timeline){
			case HOME:
				return home;
			case USER:
				return user;
			case MENTION:
				return mention;
			default:
				return null;
		}
		
	}
	
	public void setTimeline(Timeline timeline, ArrayList<Tweet> result) {
		switch(timeline){
		case HOME:
			home = result;
			break;
		case USER:
			user = result;
			break;
		case MENTION:
			mention = result;
			break;
		default:
			Log.d("ERROR","ERROR IN TIMELINE SET");
	}
	}
	
	public void pullTimeline(Timeline timeline){
		switch(timeline){
		case HOME:
			home = null;
			break;
		case USER:
			user = null;
			break;
		case MENTION:
			mention = null;
			break;
		default:
			Log.d("ERROR","ERROR IN TIMELINE PULL");
		}
		GetTimeLineTask task = new GetTimeLineTask(timeline);
		task.execute();
	}

	public User getProfile() {
		return currentUser;
	}
	
	public void setCurrentUser(User result) {
		currentUser = result;
	}
	
	public void pullUser() {
		GetProfileTask task = new GetProfileTask();
		task.execute();
	}

	public ArrayList<Tweet> getSearch() {
		return search;
	}
	
	public void setSearch(ArrayList<Tweet> result){
		search = result;
	}
	
	public void pullSearch(String word){
		SearchTask task = new SearchTask(word);
		task.execute();
	}

	public void postTweet(String status) {
		PostTweetTask task = new PostTweetTask(status);
		task.execute();
	}
	
	public void postRetweet(String id_str){
		PostRetweetTask task = new PostRetweetTask(id_str);
		task.execute();
	}
	
	public void postUpdatProfile(User user){
		PostUpdateProfile task = new PostUpdateProfile(user);
		task.execute();
	}
	
	public void setConsumer(OAuthConsumer consumer){
		this.consumer=consumer;
	}

	public void update() {
		setChanged();
		notifyObservers();
		
	}

	public void refresh() {
		pullUser();
		pullTimeline(Timeline.HOME);
		pullTimeline(Timeline.USER);
		pullTimeline(Timeline.MENTION);
	}

	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}


	
}
