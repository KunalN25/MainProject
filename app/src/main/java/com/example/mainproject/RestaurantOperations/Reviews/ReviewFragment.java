package com.example.mainproject.RestaurantOperations.Reviews;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantsList.GetZomatoData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference reference;
    private List<ReviewData> reviewDataList;
    ReviewData reviewData;
    private String TAG = "review";
    TextView noReviewsText;
    ImageView noReviewsPic;


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

    private String resId, resName;

    private void showView() {
        reviewDataList = new ArrayList<>();      //We re-initialize the arraylist everytime the search button is clicked
        //as it should reload the list when new item is searched.
        ReviewListAdapter adapter = new ReviewListAdapter(getActivity(), reviewDataList);


        loadReviewsFromJSON();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void initRecyclerView(View v){
        noReviewsText=v.findViewById(R.id.noReviewsText);
        noReviewsPic=v.findViewById(R.id.noReviewsEmoji);
        recyclerView=v.findViewById(R.id.reviewList);
        reference = FirebaseDatabase.getInstance().getReference("Reviews");
        Log.d(TAG, "Review fragment initRecyclerView: Recycler View is initialized");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showView();


    }

    private void loadReviewsFromJSON(){

        String reviewsURL = "https://developers.zomato.com/api/v2.1/reviews?res_id=" + resId;
        GetZomatoData getZomatoData = new GetZomatoData(getActivity());
        getZomatoData.execute(reviewsURL);

        try {
            JSONObject reviewObj = new JSONObject((getZomatoData.get()));
            JSONArray reviews = reviewObj.getJSONArray("user_reviews");
            for(int i=0;i<reviews.length();i++)
            {
                JSONObject singleReview=reviews.getJSONObject(i).getJSONObject("review");
                reviewDataList.add(new ReviewData(singleReview.getJSONObject("user").getString("name"),
                                singleReview.getJSONObject("user").getString("profile_image"),
                                singleReview.getString("review_text"),
                                singleReview.getDouble("rating")));

            }
            Log.d(TAG, "loadReviewsFromJSON: " + reviewDataList);


            loadReviewsFromDatabase();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(reviewDataList.isEmpty())
        {
            noReviewsPic.setVisibility(View.VISIBLE);
            noReviewsText.setVisibility(View.VISIBLE);
            Log.d(TAG, "loadReviewsFromJSON: No reviews in this restaurant");
            noReviewsText.setText("No Reviews for this Restaurant");
            noReviewsText.bringToFront();
            noReviewsPic.bringToFront();
            //noReviewsText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void loadReviewsFromDatabase() {
        reference.orderByChild(resName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    reviewData = d.getValue(ReviewData.class);
                    reviewDataList.add(reviewData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError);
            }
        });
        for (ReviewData r : reviewDataList) {

            Log.d(TAG, "\nloadReviewsFromDatabase: " + r.getCustomerName());
        }
    }


    public void getJsonData(String resId, String resName) {
        this.resId = resId;
        this.resName = resName;
    }
}
