package com.example.ns.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.ns.model.Model.Timeline;
import com.example.ns.tasks.GetProfileTask;
import com.example.ns.tasks.GetTimeLineTask;
import com.example.ns.tasks.PostTweetTask;
import com.example.ns.tasks.RequestTokenTask;
import com.example.ns.tasks.SearchTask;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

public class Model {
	
	private static Model instance;
	
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private static final String CONSUMER_KEY = "nzTfxIn5kDXKaq9lpit7IB8qC";
	private static final String CONSUMER_SECRET = "ZVx1RSILHGULSsuFt7L6dqZG4OTbtik90ph3rm2QNRxgJEZjEm";
	private static final String OAUTH_REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String OAUTH_ACCESSTOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static String OAUTH_AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private static final String OAUTH_CALLBACK_URL = "http://www.saxion.nl/pgrtwitter/authenticated";
	
	private String oAuthToken = "";
	private String verifier = "";
	
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
	
	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	
	public String getConsumerSecret(){
		return CONSUMER_SECRET;
	}

	public ArrayList<Tweet> getTimeline(Timeline timeline) {
		GetTimeLineTask task = new GetTimeLineTask(consumer, timeline);
		try {
			ArrayList<Tweet> result = task.execute().get();
			return result;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getProfile() {
		GetProfileTask task = new GetProfileTask(consumer);
		try {
			String result = task.execute().get();
			return result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Tweet> search(String word) {
		SearchTask task = new SearchTask(word, consumer);
		try {
			ArrayList<Tweet> result = task.execute().get();
			return result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String postTweet(String status) {
		PostTweetTask task = new PostTweetTask(consumer, status);
		try {
			return task.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
