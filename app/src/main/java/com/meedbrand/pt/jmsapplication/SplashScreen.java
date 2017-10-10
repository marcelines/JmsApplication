package com.meedbrand.pt.jmsapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


public class SplashScreen extends ActionBarActivity {


    private Thread mSplashThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        setContentView(R.layout.activity_splash_screen);

        ProgressBar pbBarra = (ProgressBar)findViewById(R.id.pbBarra);

        pbBarra.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(Color.rgb(6,53,95), Color.rgb(6,53,95)));

        final SplashScreen sPlashScreen = this;


        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){

                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                //finish();

                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);
                startActivity(intent);

            }
        };

        mSplashThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    /*@Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }*/
}
