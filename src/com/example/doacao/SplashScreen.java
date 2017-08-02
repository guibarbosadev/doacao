package com.example.doacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreen extends Activity 
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		getActionBar().hide();
		setContentView(R.layout.layout_splash_screen);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run()
			{
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				
				finish();
			}
		}, 3000);
	}
}
