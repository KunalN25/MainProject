package com.example.mainproject.RestaurantOperations.RestaurantsList;

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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantActivity;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;

import java.util.List;

import static androidx.core.content.ContextCompat.getDrawable;

public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.ViewHolder> {
    private Context context;
    private List <RestaurantJSONItems> restaurantsList;
    private final String TAG="kun";
     JSONAdapter(Context context, List<RestaurantJSONItems> list) {
        this.context = context;
        this.restaurantsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        RestaurantJSONItems restaurantJSONItems=restaurantsList.get(position);
        holder.restaurantName.setText(restaurantJSONItems.getName());
        holder.cuisines.setText(restaurantJSONItems.getCuisines());
        holder.rating.setText(restaurantJSONItems.getRatings());
        Log.d(TAG, "onBindViewHolder: "+restaurantJSONItems.getRatings());
          if(!restaurantJSONItems.getPhotos_url().isEmpty()) {
            Glide.with(context)
                    .asBitmap()
                    .fitCenter()
                    .centerCrop()
                    .load(restaurantJSONItems.getPhotos_url())
                    .into(holder.restaurantPhoto);        //Load the imageURL into the imageView
        }
        else
            holder.restaurantPhoto.setImageResource(R.drawable.restaurant_default);
        double rating=Double.parseDouble(holder.rating.getText().toString());
        if(rating>=3.5){
            Log.d(TAG, "onBindViewHolder: Ratings are greater than 4");
            holder.rating.setBackground(getDrawable(context, R.drawable.border_edit_for_rating_green));
            //Green background
        }
        else if(rating>=2 && rating<3.5){
            Log.d(TAG, "onBindViewHolder: Ratings between 2 and 3.5");
            holder.rating.setBackground(getDrawable(context, R.drawable.border_edit_for_rating_yellow));

            //Yellow background
        }
        else{
            Log.d(TAG, "onBindViewHolder: ratings less than 2");
            //Red Background
            holder.rating.setBackground(getDrawable(context, R.drawable.border_edit_for_rating_red));
        }
        final RestaurantJSONItems res=restaurantJSONItems;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.recyclerViewOnClick(restaurantsList.get(position));

                Intent intent=new Intent(context, RestaurantActivity.class);
                intent.putExtra("RestaurantData",res);
                context.startActivity(intent);
                Log.d(TAG, "onClick: View clicked");

            }
        });


    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView restaurantName,cuisines,rating;
        private ImageView restaurantPhoto;
        LinearLayout linearLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantPhoto=itemView.findViewById(R.id.photo);
            restaurantName=itemView.findViewById(R.id.name);
            rating=itemView.findViewById(R.id.ratingOnRecyclerView);
            cuisines=itemView.findViewById(R.id.cuisines);
            linearLayout=itemView.findViewById(R.id.restaurantCardViewLayout);
        }
    }
}
