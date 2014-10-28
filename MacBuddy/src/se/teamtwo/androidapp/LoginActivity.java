package se.teamtwo.androidapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import se.teamtwo.androidapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// This class focuses on Login authentication of user
public class LoginActivity extends Activity {

	// Variable declarations
	HttpResponse response;
	
	// Set the preferences
	public static final String PREFS_NAME = "LoginPrefs";
	ProgressDialog dialog = null;
	EditText edittext_name = null;
	EditText edittext_password = null;
	Button button_login = null;
	HttpPost httpPost;
	HttpClient httpClient;
	List<NameValuePair> nameValuePairs;
	TextView textview_password = null;

	// Overridden method onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		
		// Fetch details from text boxes
		edittext_name = (EditText) findViewById(R.id.username);
		edittext_password = (EditText) findViewById(R.id.password);
		
		// Set to the login button
		Button button = (Button) findViewById(R.id.loginButton);
		
		// Bind a listener to the button to be performed on click of the button
		button.setOnClickListener(new OnClickListener() {
			
			// Initiate Progress dialog
			public void onClick(View v) {
				dialog = ProgressDialog.show(LoginActivity.this, "",
						"Validate user credentials", true);
				// Start a new thread
				new Thread(new Runnable() {
					public void run() {
						loginUser();
					}
				}).start();
			}
		});
	}

	// Method to pass parameters and send HTTP request
	public void loginUser() {
		
		try {
			
			// Get the HTTP client
			httpClient = new DefaultHttpClient();
			
			// Send the HTTP request
			httpPost = new HttpPost("http://129.107.150.47:8080/LoginActions/loginUser.php");
			
			// Bind the parameters
			nameValuePairs = new ArrayList<NameValuePair>(2);

			nameValuePairs.add(new BasicNameValuePair("username", edittext_name
					.getText().toString().trim())); 
			nameValuePairs.add(new BasicNameValuePair("password",
					edittext_password.getText().toString().trim()));
			
			// Pass the bounded parameters to HTTP post request by encoding the URL
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Receive response from HTTP
			response = httpClient.execute(httpPost);

			// prepare an Handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			// Execute the HTTP request
			final String responseLogin = httpClient.execute(httpPost,
					responseHandler);

			// Progress Dialog should be dismissed
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
			
			// Verify the message from database to confirm user authorization
			if (responseLogin.equalsIgnoreCase("Authorized successfully")) {
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(LoginActivity.this, "Login Success",
								Toast.LENGTH_SHORT).show();

					}
				});
				
				// Start the activity once the user credentials validation is approved
				startActivity(new Intent(LoginActivity.this, Dashboard.class));
			} 
			// If user details not found in Database, display appropriate error message
			else {
				LoginActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								LoginActivity.this);
						builder.setTitle("Unable to Login");
						builder.setMessage("User not Found!!!")
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
											}
										});
						AlertDialog alert = builder.create();
						alert.show();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Overridden method for create options 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Overridden method for selected items
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
