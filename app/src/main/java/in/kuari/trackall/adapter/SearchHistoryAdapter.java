package in.kuari.trackall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.kuari.trackall.R;
import in.kuari.trackall.activities.ShowResultActivity;
import in.kuari.trackall.bean.CourierBean;
import in.kuari.trackall.bean.SearchHistory;
import in.kuari.trackall.utils.Colors;

/**
 * Created by root on 1/31/16.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchViewHolder>{

    private List<SearchHistory> searchHistories;
    private List<SearchHistory> filterdeSearchHistories;

    private Context context;

    public SearchHistoryAdapter(Context context, List<SearchHistory> searchHistories) {
        this.context=context;
        this.searchHistories=searchHistories;
        filterdeSearchHistories=new ArrayList<>();
        filterdeSearchHistories.addAll(searchHistories);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.home_search_row,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
       final SearchHistory searchHistory= filterdeSearchHistories.get(position);
        holder.name.setText(searchHistory.getName());
        holder.trackId.setText(searchHistory.getTrackId());
        holder.view.setBackgroundColor(Colors.getRandomColor());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHistoryitem(searchHistory);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterdeSearchHistories.size();
    }

     static class SearchViewHolder extends RecyclerView.ViewHolder{

         TextView trackId;
         TextView name;
         View view;
         public SearchViewHolder(View itemView) {
             super(itemView);
             view=itemView;
             trackId= (TextView) itemView.findViewById(R.id.hist_id);
             name= (TextView) itemView.findViewById(R.id.hist_name);

         }
     }
   void OnClickHistoryitem(SearchHistory searchHistory)
   {
       String trackID=searchHistory.getCourierID();
        long courierID=Long.parseLong(searchHistory.getCourierID());
       Toast.makeText(context,trackID+"hh"+courierID,Toast.LENGTH_LONG).show();

          Intent intent=new Intent(context, ShowResultActivity.class);
           intent.putExtra("trackId",trackID);
           intent.putExtra("comingFrom",0);
           intent.putExtra("courierID",courierID);

           context.startActivity(intent);//, ActivityOptions.makeSceneTransitionAnimation((Activity)context).toBundle());


    }
public void filter(String input){
    filterdeSearchHistories.clear();
    int i=0;

    if(input.length()==0)
        filterdeSearchHistories.addAll(searchHistories);
    else
    {
        for(SearchHistory s:searchHistories){

            if((s.getTrackId().toLowerCase().contains(input)||s.getName().toLowerCase().contains(input))){
                filterdeSearchHistories.add(s);

            }
        }
    }

    notifyDataSetChanged();
}
}