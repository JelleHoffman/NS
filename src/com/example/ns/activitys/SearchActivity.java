package com.example.ns.activitys;

import java.util.ArrayList;

import adapter.TweetAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ns.R;
import com.example.ns.model.Model;
import com.example.ns.model.Tweet;

public class SearchActivity extends Activity {
	private ListView listview;
	private EditText editText;
	private Button searchButton;
	private Model model = Model.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		//finding views
		listview = (ListView) findViewById(R.id.listViewSearchResult);
		editText = (EditText) findViewById(R.id.editTextSearch);
		searchButton = (Button) findViewById(R.id.buttonSearch);
		
		//setting on click
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String word = editText.getText().toString();
				editText.setText("");
				Log.d("Word:",word+"word end");
				if(!word.equals("")){
					ArrayList<Tweet> tweets = model.search(word);
					TweetAdapter adapter = new TweetAdapter(getApplicationContext(), R.id.list_item, tweets);
					listview.setAdapter(adapter);
				}else{
					Toast.makeText(getApplicationContext(), "The search text cannot be nothing", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		
	}
}
