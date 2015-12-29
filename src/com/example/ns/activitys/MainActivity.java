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
	private Model model = Model.getInstance();
	private OAuthProvider provider = model.getProvider();
	private OAuthConsumer consumer = model.getConsumer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
						model.setoAuthToken(acces);
						model.setVerifier(verifier);
						
						//setting the accesstoken
						consumer.setTokenWithSecret(consumer.getToken(),
								consumer.getTokenSecret());

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
