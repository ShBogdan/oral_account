package com.bogdan.android.oral_account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class StartActivity extends Activity {
    String appPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        appPackageName = getApplication().getPackageName();
    }

    public void btnInfo(View v){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);

    }
    public void btnWorkout(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public  void btnLike(View v){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }
}
