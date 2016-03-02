package com.example.ns.tasks;

import com.example.ns.model.Model;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.os.AsyncTask;

public class RequestTokenTask extends AsyncTask<Void, Void, String> {
	
	private OAuthConsumer consumer;
	private OAuthProvider prodiver;
	
	
	public RequestTokenTask(OAuthConsumer consumer,OAuthProvider provider){
		this.consumer=consumer;
		this.prodiver=provider;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		String requestUrl = "";
		try {
			requestUrl = prodiver.retrieveRequestToken(consumer, Model.getCallBackUrl());
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestUrl;
	}

}
