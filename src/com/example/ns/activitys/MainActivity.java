package com.example.ns.activitys;

import java.util.concurrent.ExecutionException;

import com.example.ns.R;
import com.example.ns.R.id;
import com.example.ns.R.layout;
import com.example.ns.model.Model;
import com.example.ns.tasks.GetAccesTokenTask;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	private WebView wv;
	private Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		model = Model.getInstance();
		model.setContext(getApplicationContext());
		
		final OAuthConsumer consumer = model.getConsumer();
		final OAuthProvider provider = model.getProvider();
		
		String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("TOKEN", "");
		String secret  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SECRET", "");
		
		if(!token.isEmpty()&&!secret.isEmpty()){
			consumer.setTokenWithSecret(token, secret);
			Log.d("Prefence token:",token);
			Log.d("Prefence secret:",secret);
			Log.d("Consumer token:",consumer.getToken());
			Log.d("Consumer secret:",consumer.getTokenSecret());
			model.refresh();	
			startActivity(new Intent(MainActivity.this,TimelineActivity.class));
			Log.d("blaat", "Zien we dit?");
		} else {
			wv = (WebView) findViewById(R.id.wv);
			wv.loadUrl(model.getRequestUrl());

			wv.setWebViewClient(new WebViewClient() {

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.startsWith(Model.getCallBackUrl())) {
						try {
							//getting the url
							Uri uriurl = Uri.parse(url);
							Log.d("url", uriurl.toString());
							
							//getting the verifier
							String verifier = uriurl.getQueryParameters(
									"oauth_verifier").get(0);
							Log.d("veriffier", verifier);
							
							//getting the accesstoken
							GetAccesTokenTask task = new GetAccesTokenTask(
									provider, consumer, verifier);
							String acces = task.execute().get();
							Log.d("Accesstoken:", acces);
							
							//setting the variables
							//model.setoAuthToken(acces);
							//model.setVerifier(verifier);
							
							//setting the accesstoken
							consumer.setTokenWithSecret(consumer.getToken(),
									consumer.getTokenSecret());
							
							PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("VERIFIER", verifier).commit();
							PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("TOKEN", consumer.getToken()).commit();
							PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SECRET", consumer.getTokenSecret()).commit();
							
							String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("TOKEN", "");
							String secret  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SECRET", "");
							
							
							Log.d("Prefence token:",token);
							Log.d("Prefence secret:",secret);
							Log.d("Consumer token:",consumer.getToken());
							Log.d("Consumer secret:",consumer.getTokenSecret());
							
							model.refresh();
							// Starting the next activity
							startActivity(new Intent(MainActivity.this,
									TimelineActivity.class));
							return true;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return false;
				}

			});	
		}
			
	}

}
