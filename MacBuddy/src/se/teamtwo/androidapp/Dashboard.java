package se.teamtwo.androidapp;

import se.teamtwo.androidapp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Dashboard extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		Button createButton = (Button) findViewById(R.id.create_event_button);
		createButton.setOnClickListener(new OnClickListener() {
			// onclicklistener for create event
			public void onClick(View v) {
				Intent i = new Intent(Dashboard.this, CreateEvent.class);
				startActivity(i);
				// navigates from the dashboard to create event screen
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// This adds items to the action bar if it is present.
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