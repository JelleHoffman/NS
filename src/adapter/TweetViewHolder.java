package adapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View holder class, beter voor de performances.
 * @author Jelle
 *
 */
public class TweetViewHolder {
	ImageView profileImage;
	TextView name;
	TextView screenName;
	TextView createdAt;
	TextView text;
	Button retweet;
}
