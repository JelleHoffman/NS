package com.example.ns.activitys;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import com.example.ns.R;
import com.example.ns.model.Model;
import com.example.ns.model.User;
import com.example.ns.tasks.LoadImageFromUrl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements Observer {
	private Model model = Model.getInstance();
	private User currentUser;
	private ImageView iv;
	private TextView tvName, tvScreenName, tvDescription, tvID, tvUrl;
	private EditText etName, etDescription, etUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		model.addObserver(this);

		iv = (ImageView) findViewById(R.id.imageViewProfile);
		tvName = (TextView) findViewById(R.id.textViewProfileNameSet);
		tvScreenName = (TextView) findViewById(R.id.textViewProfileSreenNameSet);
		tvDescription = (TextView) findViewById(R.id.textViewProfileDescriptionSet);
		tvID = (TextView) findViewById(R.id.textViewProfileIdSet);
		tvUrl = (TextView) findViewById(R.id.textViewProfileUrlSet);

		etName = (EditText) findViewById(R.id.editTextProfileUpdateName);
		etDescription = (EditText) findViewById(R.id.editTextProfileUpdateDescription);
		etUrl = (EditText) findViewById(R.id.editTextProfileUpdateUrl);
		
		updateView();

	}
	
	private void updateView(){
		currentUser = model.getProfile();

		if (currentUser != null) {
			tvName.setText(currentUser.getName());
			etName.setText(currentUser.getName());

			tvScreenName.setText(currentUser.getScreenName());

			if (!currentUser.getUrl().equals("null")) {
				tvUrl.setText(currentUser.getUrl());
				etUrl.setText(currentUser.getUrl());
			} else {
				tvUrl.setText("No url at this point");
			}

			if (currentUser.getDescription().length() == 0) {
				tvDescription.setText("No description at this point");
			} else {
				tvDescription.setText(currentUser.getDescription());
			}
			etDescription.setText(currentUser.getDescription());

			tvID.setText(currentUser.getIdstr());
			
			if(currentUser.getProfileImage() == null){
				LoadImageFromUrl t = new LoadImageFromUrl(
						currentUser,getApplicationContext());
				t.execute();
//				try {
//					iv.setImageDrawable(t.execute().get());
//				} catch (Exception e) {
//					e.printStackTrace();
//				} 
			}else{
				iv.setImageDrawable(currentUser.getProfileImage());
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
								"Name cannot be empty", Toast.LENGTH_SHORT)
								.show();
					} else if (name.length() > 20) {
						Toast.makeText(getApplicationContext(),
								"Name cannot be longer then 20 characters",
								Toast.LENGTH_SHORT).show();
					} else if (description.length() > 160) {
						Toast.makeText(
								getApplicationContext(),
								"Description cannot be longer then 160 characters",
								Toast.LENGTH_SHORT).show();
					} else if (url.length() > 100) {
						Toast.makeText(getApplicationContext(),
								"Url cannot be longer then 100 characters",
								Toast.LENGTH_SHORT).show();
					} else {
						User user = currentUser;
						user.setName(name);
						user.setDescription(description);
						user.setUrl(url);

						model.postUpdatProfile(user);

						Toast.makeText(getApplicationContext(),
								"Profile updated", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(ProfileActivity.this,
								TimelineActivity.class));
					}

				}
			});
		} else {
			Log.d("ERROR", "ERROR IN PROFILE ACTIVITY");
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		updateView();
	}

}
