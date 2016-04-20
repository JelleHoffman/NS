package com.example.ns.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;

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

public class Model extends Observable {

	private static Model instance;

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private static final String CONSUMER_KEY = "nzTfxIn5kDXKaq9lpit7IB8qC";
	private static final String CONSUMER_SECRET = "ZVx1RSILHGULSsuFt7L6dqZG4OTbtik90ph3rm2QNRxgJEZjEm";
	private static final String OAUTH_REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String OAUTH_ACCESSTOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static String OAUTH_AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private static final String OAUTH_CALLBACK_URL = "http://www.saxion.nl/pgrtwitter/authenticated";

	private ArrayList<Tweet> home, user, mention, search;

	private Context context;

	private User currentUser;

	public enum Timeline {
		HOME, USER, MENTION
	}

	private Model() {
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(OAUTH_REQUEST_URL,
				OAUTH_ACCESSTOKEN_URL, OAUTH_AUTHORIZE_URL);
	}

	/**
	 * 
	 * @return a instance of Model
	 */
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	/**
	 * 
	 * @return the OAuth consumer
	 */
	public OAuthConsumer getConsumer() {
		return consumer;
	}
	
	/**
	 * Sets the consumer
	 * 
	 * @param consumer
	 *            , the OAuthConsumer to be set
	 */
	public void setConsumer(OAuthConsumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * 
	 * @return the OAuth provider
	 */
	public OAuthProvider getProvider() {
		return provider;
	}

	/**
	 * 
	 * @return the authorize url in string
	 */
	public static String getOauthAuthorizeUrl() {
		return OAUTH_AUTHORIZE_URL;
	}

	/**
	 * 
	 * @return the final callback url in string
	 */
	public static String getCallBackUrl() {
		return OAUTH_CALLBACK_URL;
	}

	/**
	 * 
	 * @return the request url for twitter.
	 */
	public String getRequestUrl() {
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

	/**
	 * 
	 * @param timeline
	 *            , a specific Timeline
	 * @return the specified timeline. Null if empty
	 */
	public ArrayList<Tweet> getTimeline(Timeline timeline) {
		switch (timeline) {
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

	/**
	 * Method used in GetTimeLineTask
	 * 
	 * @param timeline
	 *            , the specific timeline to be set
	 * @param result
	 *            the ArrayList with tweets
	 */
	public void setTimeline(Timeline timeline, ArrayList<Tweet> result) {
		switch (timeline) {
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
			Log.d("ERROR", "ERROR IN TIMELINE SET");
		}
	}

	/**
	 * Makes a new GetTimeLineTask with the specified timeline
	 * 
	 * @param timeline
	 *            , the specific timelines
	 */
	public void pullTimeline(Timeline timeline) {
		switch (timeline) {
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
			Log.d("ERROR", "ERROR IN TIMELINE PULL");
		}
		GetTimeLineTask task = new GetTimeLineTask(timeline);
		task.execute();
	}

	/**
	 * 
	 * @return the current user in a User object
	 */
	public User getProfile() {
		return currentUser;
	}

	/**
	 * Method used in GetProfileTask
	 * 
	 * @param result
	 *            , the current user in a User object
	 */
	public void setCurrentUser(User result) {
		currentUser = result;
	}

	/**
	 * Creates and executes a new GetProfileTask
	 */
	public void pullUser() {
		GetProfileTask task = new GetProfileTask();
		task.execute();
	}

	/**
	 * 
	 * @return the ArrayList with tweets returnt from the SearchTask. Null if
	 *         empty.
	 */
	public ArrayList<Tweet> getSearch() {
		return search;
	}

	/**
	 * Method used in SearchTask
	 * 
	 * @param result
	 *            , the result from SearchTask in ArrayList with Tweet objects
	 *            form.
	 */
	public void setSearch(ArrayList<Tweet> result) {
		search = result;
	}

	/**
	 * Creates a new SearchTask with the specified word.
	 * 
	 * @param word
	 *            , a string that is the argument of the SearchTask.
	 */
	public void pullSearch(String word) {
		SearchTask task = new SearchTask(word);
		task.execute();
	}

	/**
	 * Creates a PostTweetTask with the specified status.
	 * 
	 * @param status
	 *            , a string that is the status to be posted
	 */
	public void postTweet(String status) {
		PostTweetTask task = new PostTweetTask(status);
		task.execute();
	}

	/**
	 * Creates a PostRetweetTask with the specified tweet id.
	 * 
	 * @param id_str
	 *            , the id of the tweet to be retweeted.
	 */
	public void postRetweet(String id_str) {
		PostRetweetTask task = new PostRetweetTask(id_str);
		task.execute();
	}

	/**
	 * Creates a PostUpdateProfile task with the specified user information.
	 * 
	 * @param user
	 *            , a User object that contains all the information of the new
	 *            User. Even if some things are not changed.
	 */
	public void postUpdatProfile(User user) {
		PostUpdateProfile task = new PostUpdateProfile(user);
		task.execute();
	}

	/**
	 * Notifies all the observers that something is changed.
	 */
	public void update() {
		setChanged();
		notifyObservers();

	}

	/**
	 * Refreshes the following data in the app: All the Timelines and the
	 * current User.
	 */
	public void refresh() {
		currentUser = null;
		home = null;
		user = null;
		mention = null;
		pullUser();
		pullTimeline(Timeline.HOME);
		pullTimeline(Timeline.USER);
		pullTimeline(Timeline.MENTION);
	}

	/**
	 * 
	 * @return the application context. Set at the beginning.
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * 
	 * @param context
	 *            , Set the context of this app. Used in some tasks.
	 */
	public void setContext(Context context) {
		this.context = context;
	}

}
