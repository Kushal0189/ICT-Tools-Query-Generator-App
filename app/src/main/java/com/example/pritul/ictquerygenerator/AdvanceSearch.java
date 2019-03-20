package com.example.pritul.ictquerygenerator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdvanceSearch extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<ICT, ICTviewHolder> firebaseRecyclerAdapter; //from Firebase UI//




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Advance Search");

        recyclerView = (RecyclerView) findViewById(R.id.result_list);


        recyclerView.setLayoutManager(new LinearLayoutManager(AdvanceSearch.this));
        databaseReference = FirebaseDatabase.getInstance().getReference("ICT");
        databaseReference.keepSynced(true);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ICT, ICTviewHolder>(
                ICT.class,
                R.layout.list_layout,
                ICTviewHolder.class,
                databaseReference) {
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
                        Intent intent = new Intent(AdvanceSearch.this,Information.class);
                        //First argument is the key and secong is the value it holding//

                        intent.putExtra("Info",model.getInfo());
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("Image",model.getimage());

                        startActivity(intent);
                    }
                });


                Glide.with(getApplicationContext()).load(model.getimage()).into(viewHolder.ICT_img);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);



    }
    public boolean onCreateOptionsMenu(final Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_advancesearch,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent() ;
        switch(id)
        {
            case R.id.menu_adv_home:
                intent.setClass(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_adv_Full:
                intent.setClass(this,FullSearch.class);
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

}


