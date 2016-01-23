package in.kuari.trackall.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import in.kuari.trackall.R;
import in.kuari.trackall.adapter.CourierListAdapter;

public class ListAllCourier extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText courierName;
    private CourierListAdapter adp;
    private String trackID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_courier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        if(intent!=null){
           trackID= intent.getStringExtra("trackID");
        }

       recyclerView= (RecyclerView) findViewById(R.id.rc_all_courier);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adp=new CourierListAdapter(this,trackID);

        recyclerView.setAdapter(adp);

        search();


    }

    void search(){

        courierName= (EditText) findViewById(R.id.input_courier_name);
        courierName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                adp.filter(courierName.getText().toString().toLowerCase());

            }
        });
    }


}
