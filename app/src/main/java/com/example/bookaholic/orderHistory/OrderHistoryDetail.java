package com.example.bookaholic.orderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.bookaholic.MainActivity.currentSyncedUser;

import com.example.bookaholic.OrderBook;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class OrderHistoryDetail extends AppCompatActivity {
    String position;
    RecyclerView orderDetailRV;
    public TextView discountPriceTextview, cartTotalPriceTextView, shippingFeeTextView, totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);
        loadData();
        ArrayList<OrderBook> orderBooks= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getOrderBooks();
        String orderStatus= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getOrderStatus();
        Double orderTotal= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getTotalPrice();
        Double discountPrice= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getDiscountPrice();
        orderDetailRV = findViewById(R.id.orderDetailRV);
        ImageView returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        System.out.println(orderTotal);
        OrderHistoryDetailAdapter orderDetailAdapter = new OrderHistoryDetailAdapter(this, orderBooks, orderStatus, orderTotal, discountPrice);
        orderDetailRV.setAdapter(orderDetailAdapter);
        discountPriceTextview = findViewById(R.id.discountPriceTextview);
        cartTotalPriceTextView = findViewById(R.id.cartTotalPriceTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        shippingFeeTextView = findViewById(R.id.shippingFeeTextView);
        discountPriceTextview.setText(String.valueOf(discountPrice + orderTotal));
        cartTotalPriceTextView.setText(orderTotal.toString());
        totalPriceTextView.setText(String.valueOf(discountPrice + orderTotal -  ((double) 30000)));
        shippingFeeTextView.setText("30000");
        discountPriceTextview.setPaintFlags(discountPriceTextview.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void loadData() {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        position = bundle.getString("position");
    }

}