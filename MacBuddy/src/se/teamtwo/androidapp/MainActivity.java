package se.teamtwo.androidapp;

import se.teamtwo.androidapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.*;

import android.widget.Button;

import android.view.View.*;

import android.content.Intent;

public class MainActivity extends Activity {
	// This class displays the start screen

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_screen);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			// onclicklistenser for the login button
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);
				// navigates from start screen to login screen
			}
		});
		Button buttonReg = (Button) findViewById(R.id.button2);
		buttonReg.setOnClickListener(new OnClickListener() {
			// onclicklistener for registration
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, RegActivity.class);
				startActivity(i);
				// navigates from start screen to registration screen
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}