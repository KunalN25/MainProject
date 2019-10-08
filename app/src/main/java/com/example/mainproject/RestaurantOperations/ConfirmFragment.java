package com.example.mainproject.RestaurantOperations;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment implements View.OnClickListener {

    private List<MenuItem> cart;
    private TextView totalPriceValue;
    private ListView cartItemsListView;
    private int totalPrice;
    private Button confirmButton;
    ConfirmFragmentMethods confirmFragmentMethods;
    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_confirm, container, false);
        initialize(v);
        cartItemsListView.setAdapter(new ConfirmFragmentListAdapter(getActivity(),cart));
        View header=getLayoutInflater().inflate(R.layout.confirm_your_order,null);
        View footer=getLayoutInflater().inflate(R.layout.total_price,null);

        cartItemsListView.addHeaderView(header);
        totalPriceValue=footer.findViewById(R.id.totalValue);
        totalPriceValue.setText(totalPrice+"");
        cartItemsListView.addFooterView(footer);
        confirmButton=footer.findViewById(R.id.confirmYourOrderButton);
        confirmButton.setOnClickListener(this);
        return v;
    }

    private void initialize(View v) {

        cartItemsListView=v.findViewById(R.id.carItemsList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        confirmFragmentMethods=(ConfirmFragmentMethods)context;

    }

    void getMenuItems(List<com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem> cart, int totalPrice) {
        this.cart=cart;
        this.totalPrice=totalPrice;

    }

    @Override
    public void onClick(View view) {
        confirmFragmentMethods.startPaymentActivity(totalPrice);
    }
    interface ConfirmFragmentMethods{
        void startPaymentActivity(int total);
    }
}
