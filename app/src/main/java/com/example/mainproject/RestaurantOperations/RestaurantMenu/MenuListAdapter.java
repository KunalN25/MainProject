package com.example.mainproject.RestaurantOperations.RestaurantMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mainproject.R;

public class MenuListAdapter extends ArrayAdapter{
    Context context;
    MenuItem[] menuItems;
    private static final String TAG="kun";
    public MenuListAdapter(Context context, MenuItem[] menuItems) {
        super(context, R.layout.menu_list_item,R.id.menuItemName,menuItems);
        this.context=context;
       this.menuItems=menuItems;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row=convertView;
        MenuItemViewHolder holder=null;
        if(row==null) //Initialize the LayoutInflater only when the row is being created for the first time otherwise directly skip
        // to the next step
        {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.menu_list_item, parent, false);
            holder=new MenuItemViewHolder(row);   //This does the work of initializing the view objects like textView and imageViews and makes
            // the listView further optimized.
             row.setTag(holder);     //This can store some object inside it.


        }
        else {
            holder= (MenuItemViewHolder) row.getTag();

        }
        final MenuItemViewHolder finalHolder = holder;

        row.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                boolean checked= finalHolder.checkBox.isChecked();
                finalHolder.checkBox.setChecked(!checked);
                if(!checked)
                {
                    finalHolder.listItem.setBackgroundColor(context.getResources().getColor(R.color.pink));
                }
                else {
                    finalHolder.listItem.setBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
                }
                Log.d(TAG, "onClick: "+finalHolder.names.getText().toString()+" is clicked");
            }
        });

        holder.names.setText(menuItems[position].getName());
        holder.price.setText(menuItems[position].getPrice());
        holder.checkBox.setTag("CheckBox");
        holder.names.setTag("Item");
        return row;
    }


}

class MenuItemViewHolder{
    TextView names,description,price;
    ImageView icon;
    CheckBox checkBox;
    RelativeLayout listItem;
    MenuItemViewHolder(View v){
        names=v.findViewById(R.id.menuItemName);
        description=v.findViewById(R.id.itemDesription);
        price=v.findViewById(R.id.itemPrice);
        icon=v.findViewById(R.id.icon);
        checkBox=v.findViewById(R.id.checkItem);
        listItem=v.findViewById(R.id.listItem);
    }
}
