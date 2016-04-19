package com.example.ns.activitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import oauth.signpost.OAuthConsumer;

import com.example.ns.R;
import com.example.ns.R.layout;
import com.example.ns.model.Model;
import com.example.ns.model.Model.Timeline;
import com.example.ns.model.Tweet;

import adapter.TweetAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity implements Observer{
	private Model model = Model.getInstance();
	private ListView listView;
	private Button home, user, mention, search, profile, tweet, refresh,logout;
	private ArrayList<Tweet> timelineHome, timelineMention, timelineUser;
	private TweetAdapter adapterHome, adapterUser, adapterMention;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		model.addObserver(this);
		
		// finding the items
		listView = (ListView) findViewById(R.id.listViewTweets);
		home = (Button) findViewById(R.id.buttonHome);
		user = (Button) findViewById(R.id.buttonUser);
		mention = (Button) findViewById(R.id.buttonMention);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		search = (Button) findViewById(R.id.buttonGoToSearch);
		profile = (Button) findViewById(R.id.buttonOwnProfile);
		tweet = (Button) findViewById(R.id.buttonGoToTweet);
		logout = (Button) findViewById(R.id.buttonLogOut);
		
		adapterHome = new TweetAdapter(getApplicationContext(), R.layout.list_item, new ArrayList<Tweet>());
		adapterUser = new TweetAdapter(getApplicationContext(), R.layout.list_item, new ArrayList<Tweet>());
		adapterMention = new TweetAdapter(getApplicationContext(), R.layout.list_item, new ArrayList<Tweet>());
		
		listView.setAdapter(adapterHome);
		// setting the buttons
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapterHome);
			}
		});

		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapterUser);
			}
		});

		mention.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapterMention);
			}
		});

		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				model.refresh();
			}
		});

		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimelineActivity.this,
						SearchActivity.class));
			}
		});

		tweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimelineActivity.this,
						TweetActivity.class));
			}
		});

		profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimelineActivity.this,
						ProfileActivity.class));
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("TOKEN", "").commit();
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SECRET", "").commit();
				startActivity(new Intent(TimelineActivity.this,MainActivity.class));
			}
			
		});
		
		updateView();
	}
	
	private void updateView(){
		timelineHome = model.getTimeline(Timeline.HOME);
		if(timelineHome != null){
			adapterHome.clear();
			adapterHome.addAll(timelineHome);
		}
		
		timelineUser = model.getTimeline(Timeline.USER);
		if(timelineUser != null){
			adapterUser.clear();
			adapterUser.addAll(timelineUser);
		}
		
		timelineMention = model.getTimeline(Timeline.MENTION);
		if(timelineMention != null){
			adapterMention.clear();
			adapterMention.addAll(timelineMention);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		updateView();
	}

}
