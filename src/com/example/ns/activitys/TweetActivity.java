package com.example.ns.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ns.R;
import com.example.ns.model.Model;

public class TweetActivity extends Activity {
	private EditText editText;
	private Button tweet;
	private Model model = Model.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		
		//finding the views
		tweet = (Button) findViewById(R.id.buttonTweet);
		editText = (EditText) findViewById(R.id.editTextTweet);
		
		tweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tweetText = editText.getText().toString();
				editText.setText("");
				if(!tweetText.equals("")){
					model.postTweet(tweetText);
					Toast.makeText(getApplicationContext(), "Tweeted:"+tweetText, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "The tweet cannot be nothing", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
}
