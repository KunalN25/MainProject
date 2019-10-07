package com.example.mainproject.RestaurantOperations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;

import java.util.List;

public class ConfirmFragmentListAdapter extends ArrayAdapter {
    private Context context;
    private List<MenuItem> menuItems;
    private static final String TAG="kun";
     ConfirmFragmentListAdapter(Context context, List<MenuItem> menuItems) {
        super(context, R.layout.confirm_fragment_list_item,R.id.confirmName,menuItems);
        this.context=context;
        this.menuItems=menuItems;
    }

    @NonNull
    @Override
    public View getView( int position,  View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        ConfirmItemViewHolder holder=null;
        if(row==null) //Initialize the LayoutInflater only when the row is being created for the first time otherwise directly skip
        // to the next step
        {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                row = inflater.inflate(R.layout.confirm_fragment_list_item, parent, false);
            }
            assert row != null;
            holder=new ConfirmItemViewHolder(row);   //This does the work of initializing the view objects like textView and imageViews and makes
            // the listView further optimized.
            row.setTag(holder);     //This can store some object inside it.


        }
        else {
            holder= (ConfirmItemViewHolder) row.getTag();

        }


        holder.names.setText(menuItems.get(position).getName());
        holder.price.setText(menuItems.get(position).getPrice());

        return row;
    }



}

class ConfirmItemViewHolder{
    TextView names,price;

    ConfirmItemViewHolder(View v){
        names=v.findViewById(R.id.confirmName);
        price=v.findViewById(R.id.confirmPrice);

    }
}
