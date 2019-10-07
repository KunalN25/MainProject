package com.example.mainproject.RestaurantOperations.RestaurantMenu;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.menu
 */
public class MenuFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private static final String TAG="kun";
    private ListView menuList;
    private MenuListAdapter menuListAdapter;
    private Button show;
    private List<MenuItem> menuItem,addToCart;
    private List<MenuItemForListView> menuItemForListViews;
    private MenuFragmentMethods menuFragmentMethods;
    private Spinner selectedItems;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_menu, container, false);
        initialize(v);
        show=v.findViewById(R.id.show);
        show.setOnClickListener(this);
        menuList.setOnItemClickListener(this);

        menuList.setAdapter(menuListAdapter);

        return v;
    }
    private void initialize(View v){
        menuList=v.findViewById(R.id.menuList);
        menuItemForListViews=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            menuItemForListViews.add(new MenuItemForListView("Item "+(i+1),"100"));
        }
        menuListAdapter=new MenuListAdapter(getActivity(),menuItemForListViews);
        selectedItems=v.findViewById(R.id.selectedItems);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuFragmentMethods=(MenuFragmentMethods)context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {

        addToCart=new ArrayList<>();
        int cartItems=0;
        try{
            for(int i=0;i<menuItemForListViews.size();i++)
            {
                if(menuItemForListViews.get(i).isSelected())
                {
                    Log.d(TAG, "onClick: "+menuItemForListViews);
                    addToCart.add(new MenuItem(menuItemForListViews.get(i).getName(),menuItemForListViews.get(i).getPrice()));
                    cartItems++;
                }
            }
            if(cartItems==0)
                Message.message(getActivity(),"Please select at least one item");
            else {
                menuFragmentMethods.sendMenuItems(addToCart);
            }



        }catch (NullPointerException e)
        {
            Message.message(getActivity(),e.toString(), 1);
            Log.d(TAG, "onClick: "+e.toString());
        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox checkBox;
        TextView name,price;
        view.setSelected(true);
        checkBox= view.findViewById(R.id.checkItem);
        name=view.findViewById(R.id.menuItemName);
        price=view.findViewById(R.id.itemPrice);
        boolean checked=checkBox.isChecked();
        checkBox.setChecked(!checked);
        if(!checked)
        {
            view.setBackgroundColor(R.color.pink);
            menuItemForListViews.get(i).setSelected(true);
        }
        else
        {
            view.setBackgroundColor(android.R.color.background_light);
            menuItemForListViews.get(i).setSelected(false);
        }


    }



    public interface MenuFragmentMethods{
        void sendMenuItems(List<MenuItem> cart);
    }



    /*ADD:
    * 1.Item Selected Listener to the menuItems
    * 2.Bottom order bar
    * 3.*/
}
