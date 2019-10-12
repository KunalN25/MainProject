package com.example.mainproject.RestaurantOperations.RestaurantMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mainproject.R;

import java.util.List;

public class MenuListAdapter extends ArrayAdapter {
    Context context;
    List<MenuItemForListView> menuItemForListViews;

    private static final String TAG="kun";
     MenuListAdapter(Context context, List<MenuItemForListView> menuItemForListViews) {
        super(context, R.layout.menu_list_item,R.id.menuItemName,menuItemForListViews);
        this.context=context;
       this.menuItemForListViews=menuItemForListViews;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        MenuItemViewHolder holder=null;
        if(row==null) //Initialize the LayoutInflater only when the row is being created for the first time otherwise directly skip
        // to the next step
        {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                row = inflater.inflate(R.layout.menu_list_item, parent, false);
            }
            assert row != null;
            holder=new MenuItemViewHolder(row);   //This does the work of initializing the view objects like textView and imageViews and makes
            // the listView further optimized.
             row.setTag(holder);     //This can store some object inside it.

        }
        else {
            holder= (MenuItemViewHolder) row.getTag();

        }

        if(menuItemForListViews.get(position).isSelected()) {
            holder.listItem.setBackgroundColor(R.color.pink);
            holder.checkBox.setChecked(true);


        } else {
            holder.listItem.setBackgroundColor(android.R.color.background_light);
            holder.checkBox.setChecked(false);

        }

        holder.names.setText(menuItemForListViews.get(position).getName());
        holder.price.setText(menuItemForListViews.get(position).getPrice()+"");

        return row;
    }



}

class MenuItemViewHolder{
    TextView names,price;
    private ImageView icon;
    CheckBox checkBox;
    RelativeLayout listItem;
    MenuItemViewHolder(View v){
        names=v.findViewById(R.id.menuItemName);
        price=v.findViewById(R.id.itemPrice);
        icon=v.findViewById(R.id.icon);
        checkBox=v.findViewById(R.id.checkItem);
        listItem=v.findViewById(R.id.listItem);
    }
}
