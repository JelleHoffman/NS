package com.example.ns.tasks;

import java.io.InputStream;
import java.net.URL;

import oauth.signpost.OAuthConsumer;

import com.example.ns.model.Model;
import com.example.ns.model.User;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadImageFromUrl extends AsyncTask<String, Void, Drawable> {
	private Model model = Model.getInstance();
	private String url;
	private User user;
	private Context context;
	
	public LoadImageFromUrl(User user, Context context) {
		this.url=user.getProfileImageUrl();
		this.user = user;
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
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false);
	    return new BitmapDrawable(context.getResources(), bitmapResized);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		user.setProfileImage(result);
		model.update();
	}

}
