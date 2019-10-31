package com.example.mainproject.RestaurantOperations.Reviews;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private String TAG="kun";
    private List<ReviewData> reviewDataList;
    private String reviewsString;
    TextView noReviewsText;


    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_review, container, false);
        initRecyclerView(v);

        return v;
    }
    private void initRecyclerView(View v){
        noReviewsText=v.findViewById(R.id.noReviewsText);
        recyclerView=v.findViewById(R.id.reviewList);
        Log.d(TAG, "Review fragment initRecyclerView: Recycler View is initialized");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showView();


    }
    private  void showView()
    {
        reviewDataList=new ArrayList<>();      //We re-initialize the arraylist everytime the search button is clicked
        //as it should reload the list when new item is searched.
        ReviewListAdapter adapter=new ReviewListAdapter(getActivity(),reviewDataList);



        loadReviewsFromJSON();
          adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void loadReviewsFromJSON(){


        try {
            JSONArray reviews=new JSONArray(reviewsString);
            for(int i=0;i<reviews.length();i++)
            {
                JSONObject singleReview=reviews.getJSONObject(i).getJSONObject("review");
                reviewDataList.add(new ReviewData(singleReview.getJSONObject("user").getString("name"),
                                singleReview.getJSONObject("user").getString("profile_image"),
                                singleReview.getString("review_text"),
                                singleReview.getDouble("rating")));

            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(reviewDataList.isEmpty())
        {
            noReviewsText.setVisibility(View.VISIBLE);
            Log.d(TAG, "loadReviewsFromJSON: No reviews in this restaurant");
            noReviewsText.setText("No Reviews for this Restaurant");
            noReviewsText.bringToFront();
            //noReviewsText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }



    public void getJsonData(String reviews) {
        this.reviewsString=reviews;
    }
}
