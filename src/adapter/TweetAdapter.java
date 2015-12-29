package adapter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.example.ns.R;
import com.example.ns.model.Tweet;
import com.example.ns.model.User;
import com.example.ns.tasks.LoadImageFromUrl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	private LayoutInflater inflater;
	private ArrayList<Tweet> tweets;
	
	public TweetAdapter(Context context, int resource, 
			List<Tweet> objects) {
		super(context, resource, objects);
		tweets = (ArrayList<Tweet>) objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//creating holders
		TweetViewHolder holder = null;
		if(convertView == null){
			holder = new TweetViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			holder.profileImage = (ImageView) convertView.findViewById(R.id.imageViewProfileImage);
			holder.name=(TextView) convertView.findViewById(R.id.textViewName);
			holder.screenName=(TextView) convertView.findViewById(R.id.textViewScreenName);
			holder.createdAt=(TextView) convertView.findViewById(R.id.textViewCreatedAt);
			holder.text=(TextView) convertView.findViewById(R.id.textViewText);
			convertView.setTag(holder);
		}else{
			holder = (TweetViewHolder) convertView.getTag();
		}
		
		//setting the data
		Tweet tweet = tweets.get(position);
		User user = tweet.getUser();
		Log.d("Image url: ",user.getProfileImageUrl());
		LoadImageFromUrl task = new LoadImageFromUrl(user.getProfileImageUrl());
		try {
			holder.profileImage.setImageDrawable(task.execute().get());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		holder.name.setText(user.getName());
		holder.screenName.setText("@"+user.getScreenName());
		holder.createdAt.setText(tweet.getCreatedAt());
		holder.text.setText(tweet.getText());
		return convertView;
		
	}
	
	public Bitmap LoadBitmapFromUrl(String imageUrl){
		try{
			URL url = new URL(imageUrl);
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			return bmp;
		}catch(Exception e){
			Log.d("Exception in load Bitmap","oops");
			e.printStackTrace();
		}
		return null;
		
	}

}
