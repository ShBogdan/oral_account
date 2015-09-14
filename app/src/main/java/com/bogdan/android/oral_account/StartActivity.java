package com.bogdan.android.oral_account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
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

    }
}
