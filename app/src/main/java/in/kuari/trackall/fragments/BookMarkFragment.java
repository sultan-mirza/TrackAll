package in.kuari.trackall.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import in.kuari.trackall.R;
import in.kuari.trackall.adapter.BookMarkAdapter;
import in.kuari.trackall.bean.BookMark;
import in.kuari.trackall.databases.SQLiteDBHandler;
import in.kuari.trackall.utils.NotificationHandler;

/**
 * Created by sultan_mirza on 1/18/16.
 */
public class BookMarkFragment extends Fragment {

    private static final String TAG = "BookMarkFragment";
    private EditText trackID;
    private RecyclerView  recyclerView;
    private BookMarkAdapter adp;
    private LinearLayout noHist;
    private Activity activity;

    public BookMarkFragment() {
    }

    public static BookMarkFragment newInstance(int position) {
        BookMarkFragment fragment = new BookMarkFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

   /* @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(actionBar.getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });

    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home_layout, container, false);
       activity=getActivity();
        trackID = (EditText) rootView.findViewById(R.id.input_trackID_or_name);

       recyclerView= (RecyclerView) rootView.findViewById(R.id.list_search_history);
        noHist= (LinearLayout)rootView.findViewById(R.id.no_bm_layout);

        RecyclerView.LayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        SQLiteDBHandler handler=new SQLiteDBHandler(activity);
        List<BookMark> searchHistories=handler.getAllBookMarks();
        /*for(BookMark s:searchHistories){
            Toast.makeText(activity,"g"+s.getRating()+s.getName(), Toast.LENGTH_SHORT).show();
        }*/

        if(searchHistories.size()==0) {
            noHist.setVisibility(View.VISIBLE);
            trackID.setVisibility(View.GONE);
        }
        adp=new BookMarkAdapter(activity,searchHistories);
        recyclerView.setAdapter(adp);

        /*Log.d("Bind1",searchHistories.size()+"");
        img.setVisibility(View.VISIBLE);
        if(searchHistories.size()==0){
            Log.d("Bind",searchHistories.size()+"");
            Toast.makeText(activity,searchHistories.size()+"gg",Toast.LENGTH_SHORT).show();


        }*/



        search(activity);
        alarmSet();
        return rootView;
    }

    void search(Activity activity){
        trackID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.filter(trackID.getText().toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
    }

    private void alarmSet(){
    Intent myIntent = new Intent(activity , NotificationHandler.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);

    AlarmManager alarmManager=(AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);


    Calendar firingCal = Calendar.getInstance();
    Calendar currentCal = Calendar.getInstance();

    firingCal.set(Calendar.HOUR_OF_DAY, new Random().nextInt(24)); //24-hour format
    firingCal.set(Calendar.MINUTE, new Random().nextInt(60));
    firingCal.set(Calendar.SECOND, new Random().nextInt(60));

    long intendedTime = firingCal.getTimeInMillis();
    long currentTime = currentCal.getTimeInMillis();
    if(intendedTime >= currentTime)
    {
        //this will set the alarm for current day if time is below 11 am
        alarmManager.set(AlarmManager.RTC_WAKEUP, intendedTime, pendingIntent);
    }
    else {
        //this will set the alarm for the next day
        firingCal.add(Calendar.DAY_OF_MONTH, 1);
        intendedTime = firingCal.getTimeInMillis();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime ,
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
}
