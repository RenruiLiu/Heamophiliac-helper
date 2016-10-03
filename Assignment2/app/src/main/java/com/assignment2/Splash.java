package com.assignment2;
//Renrui Liu 216166456, SIT207 assignment2.

/*
This is the launch activity.
It will appear for 3 seconds and then jump to main activity.
 *  */

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class Splash extends Activity {
    //handle how many seconds the splash activity will show for.
    private static final long SPLASH_DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    //go to homePage
    private void goHome() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(intent);
        Splash.this.finish();
    }
}
