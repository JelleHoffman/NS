package com.example.ns.activitys;

import org.w3c.dom.Text;

import com.example.ns.R;
import com.example.ns.R.id;
import com.example.ns.R.layout;
import com.example.ns.R.menu;
import com.example.ns.model.Model;
import com.example.ns.model.User;
import com.example.ns.tasks.LoadImageFromUrl;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	private Model model = Model.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		ImageView iv = (ImageView) findViewById(R.id.imageViewProfile);
		TextView tvName = (TextView) findViewById(R.id.textViewProfileNameSet);
		TextView tvScreenName = (TextView) findViewById(R.id.textViewProfileSreenNameSet);
		TextView tvDescription = (TextView) findViewById(R.id.textViewProfileDescriptionSet);
		TextView tvID = (TextView) findViewById(R.id.textViewProfileIdSet);
		TextView tvUrl = (TextView) findViewById(R.id.textViewProfileUrlSet);

		final EditText etName = (EditText) findViewById(R.id.editTextProfileUpdateName);
		final EditText etDescription = (EditText) findViewById(R.id.editTextProfileUpdateDescription);
		final EditText etUrl = (EditText) findViewById(R.id.editTextProfileUpdateUrl);

		final User u = model.getProfile();

		tvName.setText(u.getName());
		tvScreenName.setText(u.getScreenName());
		if (!u.getUrl().equals("null")) {
			tvUrl.setText(u.getUrl());
			etUrl.setText(u.getUrl());
		} else {
			tvUrl.setText("No url at this point");
		}

		if (u.getDescription().length() == 0) {
			tvDescription.setText("No description at this point");
		} else {
			tvDescription.setText(u.getDescription());
		}

		tvID.setText(u.getIdstr());

		etName.setText(u.getName());
		etDescription.setText(u.getDescription());
		if (!u.getUrl().equals("null")) {
			etUrl.setText(u.getUrl());
		}
		LoadImageFromUrl t = new LoadImageFromUrl(u.getProfileImageUrl(),
				getApplicationContext());
		try {
			iv.setImageDrawable(t.execute().get());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Button btnUpdate = (Button) findViewById(R.id.buttonProfileUpdate);
		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = etName.getText().toString();
				String description = etDescription.getText().toString();
				String url = etUrl.getText().toString();
				if (name.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Name cannot be empty", Toast.LENGTH_SHORT).show();
				} else if (name.length() > 20) {
					Toast.makeText(getApplicationContext(),
							"Name cannot be longer then 20 characters",
							Toast.LENGTH_SHORT).show();
				} else if (description.length() > 160) {
					Toast.makeText(getApplicationContext(),
							"Description cannot be longer then 160 characters",
							Toast.LENGTH_SHORT).show();
				} else if (url.length() > 100) {
					Toast.makeText(getApplicationContext(),
							"Url cannot be longer then 100 characters",
							Toast.LENGTH_SHORT).show();
				} else {
					User user = u;
					user.setName(name);
					user.setDescription(description);
					user.setUrl(url);

					model.postUpdatProfile(user);

					Toast.makeText(getApplicationContext(), "Profile updated",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(ProfileActivity.this,
							TimelineActivity.class));
				}

			}
		});

	}

}
