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

public class TimelineActivity extends Activity {
	private Model model = Model.getInstance();
	private ListView listView;
	private Button home,user,mention,search,profile,tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		//finding the items
		listView = (ListView) findViewById(R.id.listViewTweets);
		home = (Button) findViewById(R.id.buttonHome);
		user = (Button) findViewById(R.id.buttonUser);
		mention = (Button) findViewById(R.id.buttonMention);
		search = (Button) findViewById(R.id.buttonGoToSearch);
		profile = (Button) findViewById(R.id.buttonOwnProfile);
		tweet = (Button) findViewById(R.id.buttonGoToTweet);
		
		//get the tweets
		final ArrayList<Tweet> timeline1 = model.getTimeline(Timeline.HOME);
		
		//setting a adapter
		final TweetAdapter adapter1 = new TweetAdapter(getApplicationContext(), R.layout.list_item, timeline1);
		listView.setAdapter(adapter1);
		
		//getting the rest of the tweets
		final ArrayList<Tweet> timeline2 = model.getTimeline(Timeline.USER);
		final ArrayList<Tweet> timeline3 = model.getTimeline(Timeline.MENTION);
		
		//generating the rest of the adapters
		final TweetAdapter adapter2 = new TweetAdapter(getApplicationContext(), R.layout.list_item, timeline2);
		final TweetAdapter adapter3 = new TweetAdapter(getApplicationContext(), R.layout.list_item, timeline3);
		
		//setting the buttons
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapter1);
			}
		});
		
		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapter2);
			}
		});
		
		mention.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.setAdapter(adapter3);
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimelineActivity.this,SearchActivity.class));
			}
		});
		
		tweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimelineActivity.this,TweetActivity.class));
			}
		});
	}

}
