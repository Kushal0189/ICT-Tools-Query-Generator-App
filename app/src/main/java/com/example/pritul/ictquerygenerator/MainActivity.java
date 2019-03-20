
package com.example.pritul.ictquerygenerator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int COUNT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent HomeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(HomeIntent);
                finish();
            }
        },COUNT);
    }

    protected void onStart()
    {
        super.onStart();
        Toast.makeText(this,"Welcome!!!",Toast.LENGTH_LONG).show();
    }

}
