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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.LoginAndRegistration.InternetConnection;
import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.UtilityClasses.NoInternetString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.menu
 */
public class MenuFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private static final String TAG = "ole";
    private ListView menuList;
    private MenuListAdapter menuListAdapter;
    private Button show;
    private String cuisines;
    private List<MenuItem> menuItems,addToCart;
    private List<MenuItemForListView> menuItemForListViews;
    private MenuFragmentMethods menuFragmentMethods;
    private ProgressBar progressBar;
    private int prevItem = 0;



    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_menu, container, false);
        initialize(v);

        compareCuisines();
        //Log.d(TAG, "onCreateView: Size:" + menuItemForListViews.size());
        if (getContext() == null)
            Log.d(TAG, "onCreateView: getcontezt is null");

        progressBar.bringToFront();
        show=v.findViewById(R.id.show);
        show.setOnClickListener(this);
        menuList.setOnItemClickListener(this);

        return v;
    }



    private void initialize(View v){
        progressBar=v.findViewById(R.id.menuProgressBar);
        menuList=v.findViewById(R.id.menuList);
        //  Log.d(TAG, "initialize: "+menuItems.get(0).getNAME());

        // Log.d(TAG, "initialize: "+menuItemForListViews.get(0).getNAME());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuFragmentMethods=(MenuFragmentMethods)context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        if(InternetConnection.isInternetConnected(getActivity())) {
            addToCart = new ArrayList<>();
            int cartItems = 0;
            try {
                for (int i = 0; i < menuItemForListViews.size(); i++) {
                    if (menuItemForListViews.get(i).isSelected()) {
                        Log.d(TAG, "onClick: " + menuItemForListViews);
                        addToCart.add(new MenuItem(menuItemForListViews.get(i).getName(), menuItemForListViews.get(i).getPrice(), false));
                        cartItems++;
                    }
                }

                if (cartItems == 0)
                    Message.message(getActivity(), "Please select at least one item");
                else {
                    int total = calculateTotalCost();
                    menuFragmentMethods.sendMenuItems(addToCart, total);
                    /*START CONFIRM FRAGMENT*/
                }


            } catch (NullPointerException e) {
                Message.message(getActivity(), e.toString(), 1);
                Log.d(TAG, "onClick: " + e.toString());
            }
        }
        else
            Message.message(getActivity(), NoInternetString.NO_INTERNET_CONNECTION);

    }

    private int calculateTotalCost() {
        int total=0;
        for(int i=0;i<addToCart.size();i++)
        {
            total += Integer.parseInt(addToCart.get(i).getPRICE() + "");
        }
        return total;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        menuList.setFocusable(true);
        int itemPositionInVisibleRegion=i-menuList.getFirstVisiblePosition();
        int firstVisible=menuList.getFirstVisiblePosition();
      //  Log.d(TAG, "onItemClick: visible pos"+menuList.getFirstVisiblePosition());
        CheckBox checkBox;
        TextView name,price;
        view.setSelected(true);
        checkBox= view.findViewById(R.id.checkItem);
        name=view.findViewById(R.id.menuItemName);
        price=view.findViewById(R.id.itemPrice);
        boolean checked=checkBox.isChecked();
        if(name.getText().toString().equals(menuItemForListViews.get(i).getName())) {
            checkBox.setChecked(!checked);
            if (!checked) {
                menuItemForListViews.get(i).setSelected(true);

//                spinnerList.add(menuItemForListViews.get(i).getNAME());

            } else {
                menuItemForListViews.get(i).setSelected(false);
                // spinnerList.remove(menuItemForListViews.get(i).getNAME());
            }
        }
/*
        arrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,spinnerList);
        selectedItems.setAdapter(arrayAdapter);
*/

        menuListAdapter=new MenuListAdapter(getActivity(),menuItemForListViews);
        menuList.setAdapter(menuListAdapter);
         int j=itemPositionInVisibleRegion;
         int k=i;
         if(firstVisible==0) {
             while (j >=menuList.getFirstVisiblePosition()) {
                 menuList.setSelection(k);
                 k--;
                 j--;
             }
         }
         else {
             while (j >menuList.getFirstVisiblePosition()) {
                 menuList.setSelection(k);
                 k--;
                 j--;
             }
         }

    }

    public void setCuisines(String cuisines) {
        this.cuisines=cuisines;
    }

    private void compareCuisines() {
        //  Message.message(getActivity(),cuisines,1);
        if (cuisines.contains("Bengali")) {
            loadFromDatabase("BENGALI");
            Log.d(TAG, "compareCuisines: load from bengali");
        }
        if (cuisines.contains("South Indian")) {
            loadFromDatabase("SOUTH INDIAN");
            Log.d(TAG, "compareCuisines: load from south india");
        }
        if (cuisines.contains("Chinese")) {
            loadFromDatabase("CHINESE");
            Log.d(TAG, "compareCuisines: load from chinese");
        }
        if (cuisines.contains("North Indian")) {
            loadFromDatabase("NORTH INDIAN");
        }
        if (cuisines.contains("Fast Food")) {
            loadFromDatabase("FAST FOODS");
        }
        if (cuisines.contains("Cafe")) {
            loadFromDatabase("cafe");
        }

        if (cuisines.contains("Burger")) {
            loadFromDatabase("JUMBO KING");
            Log.d(TAG, "compareCuisines: load from jumbo king");

        }
       /* else{
              loadFromDatabase("BENGALI");
              Log.d(TAG, "compareCuisines: load from bengali 2");
          }*/

    }

    private void loadFromDatabase(String cuisine) {
        menuItems=new ArrayList<>();
        menuItemForListViews = new ArrayList<>();

        //  Log.d(TAG, "loadFromDatabase: called");


        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection(cuisine)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //  Log.d(TAG, "onComplete: called");
                if(task.isSuccessful())
                {
                    for(DocumentSnapshot snapshot: task.getResult())
                    {
                        MenuItem menuItem=snapshot.toObject(MenuItem.class);
                        Log.d(TAG, "onComplete: \n" + menuItem.getNAME());
                        menuItems.add(menuItem);


                    }
                    if (!menuItems.isEmpty()) {
                        for (int i = prevItem; i < menuItems.size(); i++) {
                            menuItemForListViews.add(new MenuItemForListView(menuItems.get(i).getNAME(),
                                    menuItems.get(i).getPRICE(),
                                    menuItems.get(i).getTYPE()));
                        }
                    }
                    if (getActivity() != null) {
                        menuListAdapter = new MenuListAdapter(getActivity(), menuItemForListViews);
                        menuList.setAdapter(menuListAdapter);

                    }
                    prevItem = menuItems.size();
                    progressBar.setVisibility(View.GONE);
                    show.setVisibility(View.VISIBLE);


                }
                else
                {
                    Log.d(TAG, "onComplete: Could not load");
                    progressBar.setVisibility(View.GONE);
                    menuFragmentMethods.startConnectionErrorFragment();

                }
            }
        });


    }


    public interface MenuFragmentMethods{
        void sendMenuItems(List<MenuItem> cart,int total);

        void startConnectionErrorFragment();
    }



    /*ADD:
    * 1.Item Selected Listener to the menuItems
    * 2.Bottom order bar
    * 3.*/
}
