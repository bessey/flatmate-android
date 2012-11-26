package com.Ballin_Octo_Hipster.flatmate;

import com.Ballin_Octo_Hipster.flatmate.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.splash);

        // Start your loading
        Handler handler = new Handler();
        
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
        	return 1;
        }
    }
    
    private void newLogin(){
    	AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.google");
        Account googleAccount = accounts[0];
        String email = googleAccount.name;
        EditText emailBox = (EditText) findViewById(R.id.emailTbox);
        emailBox.setText(email);
        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
        });
        
    }
    
    private String testServerResponse(){
    	return "e83489jksdjkdf9038";
    }

    private void startApp() {
		Intent intent = new Intent(SplashActivity.this, FlatMate.class);
		startActivity(intent);
	}
}