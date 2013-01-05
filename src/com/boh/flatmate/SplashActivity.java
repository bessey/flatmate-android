package com.boh.flatmate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.boh.flatmate.R;
import com.boh.flatmate.connection.ServerConnection;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Profile;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends Activity {

	ServerConnection connection = new ServerConnection();
	String FILENAME;
	final Handler myHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the splash screen
		setContentView(R.layout.splash);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
		StrictMode.setThreadPolicy(policy);
		
		FILENAME = this.getFilesDir().getPath().toString() + "UserAuthCode.txt";
		
	}

	@Override
	public void onStart(){
		super.onStart();

		Thread splashWait = new Thread(){
			@Override
			public void run(){
				try {
					int i = 0;
					int showSplashFor = 20;
					while(i < showSplashFor){
						sleep(100);
						i += 1;
					}
				} catch(InterruptedException e){
				}finally {
					int loggedin = userLogin();

					if(loggedin == 1){
						startApp();
						finish();
					}else{
						myHandler.post(newLogin);
					}
				}
			}
		};
		splashWait.start();
	}

	final Runnable newLogin = new Runnable() {
		public void run() {
			newLogin();
		}
	};

	private int userLogin(){
		File logFile = new File(FILENAME);
		String authCode = "null";
		if (logFile.exists())
		{
			BufferedReader input;
			try {
				input = new BufferedReader(new FileReader(FILENAME));
			} catch (FileNotFoundException e1) {
				return 0;
			}
			try {
				String line = null;
				if (( line = input.readLine()) != null){
					authCode = line;
				}
			}catch (Exception e){

			}
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(authCode);
			/* sucessfull login here return 1; test return 0;
			 * return 1;
			 */
			return 0;
		}

		return 0;

	}

	private void newLogin(){
		View loginBox = findViewById(R.id.loginBox);
		View flatLogo = findViewById(R.id.splashLogo);
		
		findViewById(R.id.loginBox).setVisibility(View.VISIBLE);
		
		TranslateAnimation anim = new TranslateAnimation( 0, 0 , 125, 0 );
	    anim.setDuration(250);
	    anim.setFillAfter( false );
	    anim.setAnimationListener(new AnimationListener() {
	    	@Override
	        public void onAnimationEnd(Animation animation) {
	    		TranslateAnimation anim2 = new TranslateAnimation( 0, 0 , 0, 0 );
	    		anim2.setDuration(1);
	    		findViewById(R.id.splashLogo).startAnimation(anim2);
	    	}
	    	@Override
	        public void onAnimationRepeat(Animation animation) { }
	    	@Override
	        public void onAnimationStart(Animation animation) { }
	    });
	    flatLogo.startAnimation(anim);
	    
	    AlphaAnimation anim2 = new AlphaAnimation( 0, 1 );
	    anim2.setDuration(250);
	    anim2.setStartOffset(150);
	    anim2.setFillAfter( false );
	    anim2.setAnimationListener(new AnimationListener() {
	    	@Override
	        public void onAnimationEnd(Animation animation) {
	    		//TranslateAnimation anim2 = new TranslateAnimation( 0, 0 , 0, 0 );
	    		//anim2.setDuration(1);
	    		//findViewById(R.id.splashLogo).startAnimation(anim2);
	    	}
	    	@Override
	        public void onAnimationRepeat(Animation animation) { }
	    	@Override
	        public void onAnimationStart(Animation animation) { }
	    });
	    loginBox.startAnimation(anim2);
		
		
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccountsByType("com.google");
		String email;
		if(accounts.length > 0){
			Account googleAccount = accounts[0];
			email = googleAccount.name;
		} else {
			email = "";
		}
		EditText emailBox = (EditText) findViewById(R.id.emailTbox);

		emailBox.setText(email);

		//temp for testing ---- use above line for real
		//emailBox.setText("adam9@bla.com");
		//EditText passwordBox1 = (EditText) findViewById(R.id.passwordBox);
		//passwordBox1.setText("Password");


		Button login = (Button) findViewById(R.id.loginButton);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText emailBox2 = (EditText) findViewById(R.id.emailTbox);		
				String emailInput = emailBox2.getText().toString();
				EditText passwordBox1 = (EditText) findViewById(R.id.passwordBox);			
				String passwordInput = passwordBox1.getText().toString();
				Button login2 = (Button) findViewById(R.id.loginButton);
				login2.setVisibility(View.GONE);
				Button register = (Button) findViewById(R.id.registerButton);
				register.setVisibility(View.GONE);
				ProgressBar spinner = (ProgressBar) findViewById(R.id.loginSpinner);
				spinner.setVisibility(View.VISIBLE);
				new serverLogin().execute(emailInput,passwordInput);
			}
		});
		
		Button register = (Button) findViewById(R.id.registerButton);
		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				registerButtonPressed();
			}
		});

	}
	
	@TargetApi(14)
	private void registerButtonPressed(){
		View flatLogo = findViewById(R.id.splashLogo);
		View loginBox = findViewById(R.id.loginBox);
		
		Button login = (Button) findViewById(R.id.loginButton);
		login.setVisibility(View.GONE);
		Button register = (Button) findViewById(R.id.registerButton);
		register.setVisibility(View.GONE);
		View registerBox = findViewById(R.id.registerBox);
		registerBox.setVisibility(View.VISIBLE);
		
		EditText phoneInput = (EditText)findViewById(R.id.phoneTextBox);
		EditText firstInput = (EditText)findViewById(R.id.firstBox);
		EditText lastInput = (EditText)findViewById(R.id.lastBox);
		
		Uri pesonUri = Profile.CONTENT_URI;
		
		Cursor people = getContentResolver().query(pesonUri, null, null, null, null);

		int firstName = people.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
		int lastName = people.getColumnIndex(Profile.DISPLAY_NAME);

		people.moveToFirst();
		String[] name = people.getString(firstName).split(" ");
		if(name.length > 1){
			String first = name[0];
			String last = name[1];
			System.out.println("first = "+first+", last = "+last);
			firstInput.setText(first);
			lastInput.setText(last);
		}
		
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		Cursor phoneNumber = getContentResolver().query(uri, null, null, null, null);

		int userID = phoneNumber.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID);
		int userNumber = phoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		
		phoneNumber.moveToFirst();
		do {
		    String id   = phoneNumber.getString(userID);
		    String number = phoneNumber.getString(userNumber);
		    System.out.println("id = "+id+", number = "+number);
		    if(id.equals("1")){
		    	phoneInput.setText(number);
		    	break;
		    }
		} while (phoneNumber.moveToNext());
		
		TranslateAnimation anim = new TranslateAnimation( 0, 0 , 140, 0 );
	    anim.setDuration(250);
	    anim.setFillAfter( false );
	    anim.setAnimationListener(new AnimationListener() {
	    	@Override
	        public void onAnimationEnd(Animation animation) {
	    		TranslateAnimation anim2 = new TranslateAnimation( 0, 0 , 0, 0 );
	    		anim2.setDuration(1);
	    		findViewById(R.id.splashLogo).startAnimation(anim2);
	    		System.out.println(findViewById(R.id.splashLogo).getY());
	    	}
	    	@Override
	        public void onAnimationRepeat(Animation animation) { }
	    	@Override
	        public void onAnimationStart(Animation animation) { }
	    });
	    flatLogo.startAnimation(anim);
	    loginBox.startAnimation(anim);
	    
	    AlphaAnimation anim2 = new AlphaAnimation( 0, 1 );
	    anim2.setDuration(250);
	    anim2.setStartOffset(50);
	    anim2.setFillAfter( false );
	    anim2.setAnimationListener(new AnimationListener() {
	    	@Override
	        public void onAnimationEnd(Animation animation) {
	    		//TranslateAnimation anim2 = new TranslateAnimation( 0, 0 , 0, 0 );
	    		//anim2.setDuration(1);
	    		//findViewById(R.id.splashLogo).startAnimation(anim2);
	    	}
	    	@Override
	        public void onAnimationRepeat(Animation animation) { }
	    	@Override
	        public void onAnimationStart(Animation animation) { }
	    });
	    registerBox.startAnimation(anim2);
		
	}

	private void serverLogin(String key){
		if(key != "failed"){
			File logFile = new File(FILENAME);
			if (!logFile.exists())
			{
				try
				{
					logFile.createNewFile();
				} 
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try
			{
				//BufferedWriter for performance, true to set append to file flag
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
				buf.append(key);
				buf.newLine();
				buf.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startApp();
			finish();
		}else{
			Button login2 = (Button) findViewById(R.id.loginButton);
			login2.setVisibility(View.VISIBLE);
			Button register = (Button) findViewById(R.id.registerButton);
			register.setVisibility(View.VISIBLE);
			ProgressBar spinner = (ProgressBar) findViewById(R.id.loginSpinner);
			spinner.setVisibility(View.GONE);
			Toast toast = Toast.makeText(getApplicationContext(), "Failed to Login... Please Try Again!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void startApp() {
		Intent intent = new Intent(SplashActivity.this, FlatMate.class);
		startActivity(intent);
	}

	private class serverLogin extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... details) {
			String key = connection.login(details[0], details[1]);
			return key;
		}

		protected void onPostExecute(String result) {
			serverLogin(result);
		}

	}
}
