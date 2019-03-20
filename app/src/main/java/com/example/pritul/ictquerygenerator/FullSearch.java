package com.example.pritul.ictquerygenerator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Locale;

public class FullSearch extends AppCompatActivity implements View.OnClickListener {
    private EditText SearchField;
    private ImageView SearchBtn;
    private RecyclerView recyclerView;
    private ImageView Image;
    private DatabaseReference databaseReference;
    //private static String str[];
    //final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Full Search");


        /*Initializing the instances*/

        databaseReference = FirebaseDatabase.getInstance().getReference("ICT");//Here ICT is key where list is present*/
        SearchField = (EditText) findViewById(R.id.Search_field);
        SearchBtn = (ImageView) findViewById(R.id.imageView);

        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        Image = (ImageView) findViewById(R.id.imageView7) ;

        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()

        recyclerView.setLayoutManager(new LinearLayoutManager(FullSearch.this));

        databaseReference.keepSynced(true);
        recyclerView.setHasFixedSize(true);



        Image.setOnClickListener(FullSearch.this);
        /*Calling the fireBaseSearch method when clicked on search button*/
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = SearchField.getText().toString();
                if(searchText.length()<2)
                    Toast.makeText(FullSearch.this,"Please enter some text",Toast.LENGTH_SHORT).show();
                else
                    firebaseUserSearch(searchText);
            }
        });

    }
    private void firebaseUserSearch(String searchText)
    {
        /*Here orderByChild that is we are searching from the Name which is child of ICT*/
        /*Note \uf8ff is the unicode provided by the firebase and without it, search won't work*/
        Query firebaseSearchQuery = databaseReference.orderByChild("Name").startAt(searchText).endAt(searchText+"\uf8ff");
        /*The firebaseRecyclerAdapter is given by firebase UI and we just need to use it*/
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ICT, ICTviewHolder>(
                ICT.class,
                R.layout.list_layout,
                ICTviewHolder.class,
                firebaseSearchQuery)
        {
            @Override
            protected void populateViewHolder(ICTviewHolder viewHolder, final ICT model, int position) {
                //viewHolder.setDetails(getApplicationContext(), model.getInfo(), model.getimage());
                //It holds the view of this item//
                viewHolder.ICT_name.setText(model.getName());


                //This onclick listner is an anonymous inner class and so we need to give the defination//
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this,model.getName(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FullSearch.this,Information.class);
                        //First argument is the key and secong is the value it holding//
                        intent.putExtra("Info", model.getInfo());
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("Image",model.getimage());
                        startActivity(intent);
                    }
                });


                Glide.with(getApplicationContext()).load(model.getimage()).into(viewHolder.ICT_img);
            }
        };
        //recyclerView.setAdapter(autoComplete);
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    /*The view Holder is the innner class*/
    public static class ICTviewHolder extends RecyclerView.ViewHolder{

        TextView ICT_name;
        ImageView ICT_img;
        View view;


        /*Constructor must be provided from android*/
        public ICTviewHolder(@NonNull View itemView) {
            super(itemView);
            ICT_name = (TextView) itemView.findViewById(R.id.txt_name);
            ICT_img = (ImageView) itemView.findViewById(R.id.imageView);
            view=itemView;//So that we can use onClickListner()//

        }
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_fullsearch,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent =new Intent();
        switch(id)
        {
            case R.id.menu_full_home:
                intent.setClass(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_full_advsearch:
                intent.setClass(this,AdvanceSearch.class);
                startActivity(intent);
                break;
            case R.id.menu_full_info:
                intent.setClass(this,AboutUs.class);
                startActivity(intent);
                break;
            case R.id.menu_full_develeoper:
                intent.setClass(this,developer.class);
                startActivity(intent);
                break;
            case R.id.menu_full_contact:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:7265881581")));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.imageView7)
        {
            SpeechInput();
        }
    }



    public void SpeechInput()
    {
        //Intent to recognize speech//
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        //TO handle the exception if device not support microphone*/
        try {
            //Internal call for conversion//
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException a)
        {
            //To display popup message//
            Toast.makeText(FullSearch.this,"Sorry! Your device does not support speech language!",Toast.LENGTH_LONG).show();
        }



    }
    public void onActivityResult(int request_code,int result_code,Intent I)
    {
        super.onActivityResult(request_code,result_code,I);
        switch(request_code)
        {
            case 100: if(result_code==RESULT_OK && I!=null)
            {
                ArrayList<String> result = I.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                SearchField.setText(result.get(0));
            }
                break;

        }
    }


}