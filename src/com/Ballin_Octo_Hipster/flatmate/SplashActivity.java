package com.Ballin_Octo_Hipster.flatmate;

import com.Ballin_Octo_Hipster.flatmate.R;
import com.Ballin_Octo_Hipster.flatmate.connection.ServerConnection;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends Activity {

	ServerConnection connection = new ServerConnection();
	String UserAuthCodeFile = "flatmateDataFile";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the splash screen
		setContentView(R.layout.splash);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
		StrictMode.setThreadPolicy(policy);

		int loggedin = userLogin();

		if(loggedin == 1){
			finish();
			startApp();
		}
	}

	private int userLogin(){
		AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.Ballin_Octo_Hipster");
        if(accounts.length == 0 || accounts == null){
        	newLogin();
        	return 0;
        }else{
        	//connection setAuthCode;
        	return 1;
        }
	}

	private void newLogin(){
		View loginBox = findViewById(R.id.loginBox);
		loginBox.setVisibility(View.VISIBLE);
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccountsByType("com.google");
		Account googleAccount = accounts[0];
		String email = googleAccount.name;
		EditText emailBox = (EditText) findViewById(R.id.emailTbox);
		
		//emailBox.setText(email);
		
		//temp for testing ---- use above line for real
		emailBox.setText("adam9@bla.com");
		EditText passwordBox1 = (EditText) findViewById(R.id.passwordBox);
		passwordBox1.setText("Password");
		
		
		Button login = (Button) findViewById(R.id.loginButton);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText emailBox2 = (EditText) findViewById(R.id.emailTbox);			
				String emailInput = emailBox2.getText().toString();
				EditText passwordBox1 = (EditText) findViewById(R.id.passwordBox);			
				String passwordInput = passwordBox1.getText().toString();
				Button login2 = (Button) findViewById(R.id.loginButton);
				login2.setVisibility(View.GONE);
				ProgressBar spinner = (ProgressBar) findViewById(R.id.loginSpinner);
				spinner.setVisibility(View.VISIBLE);
				new serverLogin().execute(emailInput,passwordInput);
			}
		});

	}

	private void serverLogin(String key){
		if(key != "failed"){
			/*AccountManager am = AccountManager.get(this);
			final Account account = new Account("email", "com.Ballin_Octo_Hipster");
			am.addAccountExplicitly(account, key, null);
			*/
			finish();
			startApp();
		}else{
			Button login2 = (Button) findViewById(R.id.loginButton);
			login2.setVisibility(View.VISIBLE);
			ProgressBar spinner = (ProgressBar) findViewById(R.id.loginSpinner);
			spinner.setVisibility(View.GONE);
			Toast toast = Toast.makeText(getApplicationContext(), "Failed to Login... Please Try Again!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@SuppressWarnings("unused")
	private String testServerResponse(String email, String password){
		if(email == "sprog31@gmail.com" && password == "flatmate"){
			return "e83489jksdjkdf9038";
		}
		return "false";
	}

	private void startApp() {
		Intent intent = new Intent(SplashActivity.this, FlatMate.class);
		startActivity(intent);
	}
	
	private class serverLogin extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String[] details) {
			String key = connection.login(details[0], details[1]);
			return key;
		}
		
		protected void onPostExecute(String result) {
			serverLogin(result);
	    }
		
	}
}