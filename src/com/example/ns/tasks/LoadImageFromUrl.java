package com.example.ns.tasks;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class LoadImageFromUrl extends AsyncTask<String, Void, Drawable> {
	private String url;
	private Drawable d;
	
	public LoadImageFromUrl(String url) {
		this.url=url;
	}
	
	@Override
	protected Drawable doInBackground(String... params) {
		try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	    	Log.d("Exception in load Drawable","oops");
	        return null;
	    }
		
	}

}
