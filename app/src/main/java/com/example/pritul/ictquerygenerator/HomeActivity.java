package com.example.pritul.ictquerygenerator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton butn_full;
    ImageButton butn_adv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        butn_full=(ImageButton)findViewById(R.id.btn_full);
        butn_adv=(ImageButton) findViewById(R.id.btn_advance);
        butn_full.setOnClickListener(HomeActivity.this);
        butn_adv.setOnClickListener(HomeActivity.this);



    }

    public void onClick(View view)
    {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_full:
                intent = intent.setClass(this,FullSearch.class);
                startActivity(intent);
                break;
            case R.id.btn_advance:
                //Toast.makeText(this,"advance search",Toast.LENGTH_LONG).show();
                intent.setClass(this,AdvanceSearch.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int id = item.getItemId();
        switch(id)
        {
            case R.id.menu_info:
                intent.setClass(this,AboutUs.class);
                startActivity(intent);
                break;
            case R.id.menu_Developers:
                intent.setClass(this,developer.class);
                startActivity(intent);
                break;
            case R.id.menu_contact:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:7265881581")));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
