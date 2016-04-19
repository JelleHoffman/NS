package com.example.ns.activitys;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.ls.LSInput;

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

public class SearchActivity extends Activity implements Observer{
	private ListView listview;
	private EditText editText;
	private Button searchButton;
	private Model model = Model.getInstance();
	private TweetAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		model.addObserver(this);
		
		//finding views
		listview = (ListView) findViewById(R.id.listViewSearchResult);
		editText = (EditText) findViewById(R.id.editTextSearch);
		searchButton = (Button) findViewById(R.id.buttonSearch);
		
		adapter = new TweetAdapter(getApplicationContext(), R.layout.list_item, new ArrayList<Tweet>());
		listview.setAdapter(adapter);
		
		
		//setting on click
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String word = editText.getText().toString();
				editText.setText("");
				Log.d("Word:",word+"word end");
				if(!word.equals("")){
					model.pullSearch(word);
				}else{
					Toast.makeText(getApplicationContext(), "The search text cannot be nothing", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		
	}

	@Override
	public void update(Observable observable, Object data) {
		ArrayList<Tweet> tweets = model.getSearch();
		if(tweets!=null){
			adapter.clear();
			adapter.addAll(tweets);
			
		}
	}
}
