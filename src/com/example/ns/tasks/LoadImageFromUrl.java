package com.example.ns.tasks;

import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class LoadImageFromUrl extends AsyncTask<String, Void, Drawable> {
	private String url;
	private Context context;
	private Drawable d;
	
	public LoadImageFromUrl(String url,Context context) {
		this.url=url;
		this.context=context;
	}
	
	@Override
	protected Drawable doInBackground(String... params) {
		try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        d = resize(d);
	        return d;
	    } catch (Exception e) {
	    	Log.d("Exception in load Drawable","oops");
	        return null;
	    }
		
	}
	
	private Drawable resize(Drawable image) {
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
	    return new BitmapDrawable(context.getResources(), bitmapResized);
	}

}
