package com.example.mainproject.RestaurantOperations.RestaurantMenu;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;


/**
 * A simple {@link Fragment} subclass.menu
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private static final String TAG="kun";
    ListView menuList;
    ArrayAdapter<String> arrayAdapter;
    MenuListAdapter menuListAdapter;
    Button show;
    MenuItem[] menuItem;
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
        menuList.setAdapter(menuListAdapter);
        Log.d(TAG, "onCreateView: Menu List created");
        //menuList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        return v;
    }
    private void initialize(View v){
        menuList=v.findViewById(R.id.menuList);
        menuItem=new MenuItem[10];
        for(int i=0;i<10;i++)
        {
            menuItem[i]=new MenuItem("Item "+i,"100");
        }
        //arrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_multiple_choice,test);
        menuListAdapter=new MenuListAdapter(getActivity(),menuItem);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        for(int i=0;i<5;i++)
        {
            TextView textView=menuList.getChildAt(i).findViewById(R.id.name);
            CheckBox checkBox=menuList.getChildAt(i).findViewById(R.id.checkItem);
            if(checkBox.isChecked()) {

                Log.d(TAG, "onClick: \nName:" + menuItem[i].getName() + " \n"+"Price :"+menuItem[i].getPrice());
            }
        }


    }


    /*ADD:
    * 1.Item Selected Listener to the menuItems
    * 2.Bottom order bar
    * 3.*/
}
