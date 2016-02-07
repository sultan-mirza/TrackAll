package in.kuari.trackall.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.kuari.trackall.R;
import in.kuari.trackall.adapter.SearchHistoryAdapter;
import in.kuari.trackall.bean.SearchHistory;
import in.kuari.trackall.databases.SQLiteDBHandler;

/**
 * Created by sultan_mirza on 1/18/16.
 */
public class HomeFragment extends Fragment{

    private EditText trackID;
    private RecyclerView  recyclerView;
    private SearchHistoryAdapter adp;
    TextView noHist;
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.home_layout,container,false);
        Activity activity=getActivity();
        trackID = (EditText) rootView.findViewById(R.id.input_trackID_or_name);

       recyclerView= (RecyclerView) rootView.findViewById(R.id.list_search_history);
        noHist= (TextView)rootView.findViewById(R.id.no_hist_img);

        RecyclerView.LayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        SQLiteDBHandler handler=new SQLiteDBHandler(activity);
        List<SearchHistory> searchHistories=handler.getAllSearches();
        /*for(SearchHistory s:searchHistories){
            Toast.makeText(activity,"g"+s.getTrackId()+s.getName(),Toast.LENGTH_SHORT);
        }*/

        if(searchHistories.size()==0)
            noHist.setVisibility(View.VISIBLE);
        adp=new SearchHistoryAdapter(activity,searchHistories);
        recyclerView.setAdapter(adp);

        /*Log.d("Bind1",searchHistories.size()+"");
        img.setVisibility(View.VISIBLE);
        if(searchHistories.size()==0){
            Log.d("Bind",searchHistories.size()+"");
            Toast.makeText(activity,searchHistories.size()+"gg",Toast.LENGTH_SHORT).show();


        }*/



        search(activity);
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


}
