package com.example.mainproject.RestaurantOperations.Reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantActivity;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private Context context;
    private List <ReviewData> reviewDataList;
    private final String TAG="Main";
     ReviewListAdapter(Context context, List<ReviewData> list) {
        this.context = context;
        this.reviewDataList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);


        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        ReviewData reviewData=reviewDataList.get(position);
        holder.name.setText(reviewData.getCustomerName());
        holder.text.setText(reviewData.getReviewText());
        holder.rating.setText(reviewData.getRating()+"");

        if(!reviewData.getPhoto_url().isEmpty()) {
            Glide.with(context)
                    .asBitmap()
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL)
                    .load(reviewData.getPhoto_url())
                    .into(holder.customerPhoto);        //Load the imageURL into the imageView
        }


    }

    @Override
    public int getItemCount() {
        return reviewDataList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,rating;
        private ReadMoreTextView text;
        private ImageView customerPhoto;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.reviewUserName);
            rating=itemView.findViewById(R.id.ratingText);
            text=itemView.findViewById(R.id.reviewTextExpand);
            customerPhoto=itemView.findViewById(R.id.reviewProfilePhoto);
        }
    }
}
