package in.kuari.trackall.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import in.kuari.trackall.R;
import in.kuari.trackall.adapter.CourierHomeAdapter;
import in.kuari.trackall.controller.CourierController;
import in.kuari.trackall.fragments.CourierFragment;
import in.kuari.trackall.utils.ConstantValues;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowResultActivity extends AppCompatActivity {


    private WebView webView;
    private String trackID;
    private String courierWebURL;
    private long courierID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.webviewresult);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        if(intent!=null) {
            int flag=  intent.getIntExtra("comingFrom",0);
            if(flag==0) {
                trackID = intent.getStringExtra("trackId");
                courierID = intent.getLongExtra("courierID", 2);
                onCourierTrack();
            }else {
                courierWebURL= intent.getStringExtra("courierWeb");
                Log.d("url",courierWebURL+"");

                onCourierDetail();

            }

        }
        Toast.makeText(this,trackID+"",Toast.LENGTH_LONG).show();
            // setContentView(R.layout.webviewresult);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void initilizeWebview(){
        webView = (WebView) findViewById(R.id.resultwebview);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().supportZoom();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


    }
    public void onCourierTrack(){
        initilizeWebview();
        ConstantValues.TRACKID=trackID;
        Log.d("TrackID",trackID);
        CourierController controller=new CourierController(this,webView);
        controller.populateView(courierID);
    }
    public void onCourierDetail(){
        initilizeWebview();
        CourierHomeAdapter adp=new CourierHomeAdapter(this,webView);
        adp.loadWebsite(courierWebURL);


    }

}
