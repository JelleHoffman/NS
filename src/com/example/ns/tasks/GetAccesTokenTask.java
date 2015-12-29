package com.example.ns.tasks;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.os.AsyncTask;

public class GetAccesTokenTask extends AsyncTask<String, Void, String>{
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	private String verifier;

	public GetAccesTokenTask(OAuthProvider provider, OAuthConsumer consumer, String verifier) {
		this.provider = provider;
		this.consumer = consumer;
		this.verifier=verifier;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			provider.retrieveAccessToken(consumer, verifier);
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}

		return consumer.getToken();
	}
}
