package com.example.ns.activitys;

import java.util.ArrayList;
import java.util.List;

import com.example.ns.R;
import com.example.ns.R.layout;
import com.example.ns.model.Model;
import com.example.ns.model.Model.Timeline;
import com.example.ns.model.Tweet;

import adapter.TweetAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity {
	private Model model = Model.getInstance();
	private ListView listView;
	private Button home, user, mention, search, profile, tweet, refresh;
	private ArrayList<Tweet> timelineHome, timelineMention, timelineUser;
	private TweetAdapter adapterHome, adapterUser, adapterMention;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		// finding the items
		listView = (ListView) findViewById(R.id.listViewTweets);
		home = (Button) findViewById(R.id.buttonHome);
		user = (Button) findViewById(R.id.buttonUser);
		mention = (Button) findViewById(R.id.buttonMention);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		search = (Button) findViewById(R.id.buttonGoToSearch);
		profile = (Button) findViewById(R.id.buttonOwnProfile);
		tweet = (Button) findViewById(R.id.buttonGoToTweet);

		// get the tweets
		timelineHome = model.getTimeline(Timeline.HOME);

		// setting a adapter
		adapterHome = new TweetAdapter(getApplicationContext(),
				R.layout.list_item, timelineHome);
		listView.setAdapter(adapterHome);

		// getting the rest of the tweets
		timelineUser = model.getTimeline(Timeline.USER);
		timelineMention = model.getTimeline(Timeline.MENTION);

		// generating the rest of the adapters
		adapterUser = new TweetAdapter(getApplicationContext(),
				R.layout.list_item, timelineUser);
		adapterMention = new TweetAdapter(getApplicationContext(),
				R.layout.list_item, timelineMention);

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
				// getting the new tweets
				timelineHome = model.getTimeline(Timeline.HOME);
				timelineUser = model.getTimeline(Timeline.USER);
				timelineMention = model.getTimeline(Timeline.MENTION);

				// setting the adapters
				adapterHome = new TweetAdapter(getApplicationContext(),
						R.layout.list_item, timelineHome);
				adapterUser = new TweetAdapter(getApplicationContext(),
						R.layout.list_item, timelineUser);
				adapterMention = new TweetAdapter(getApplicationContext(),
						R.layout.list_item, timelineMention);
				Toast.makeText(getApplicationContext(),
						"Refreshed; please select your timeline",
						Toast.LENGTH_SHORT).show();
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
	}

}
