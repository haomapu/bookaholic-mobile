package com.example.bookaholic.orderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.bookaholic.MainActivity.currentSyncedUser;

import com.example.bookaholic.OrderBook;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class OrderHistoryDetail extends AppCompatActivity {
    String position;
    RecyclerView orderDetailRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);
        loadData();
        ArrayList<OrderBook> orderBooks= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getOrderBooks();
        String orderStatus= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getOrderStatus();
        Double orderTotal= currentSyncedUser.getOrderHistory().get(Integer.valueOf(position)).getTotalPrice();
        orderDetailRV = findViewById(R.id.orderDetailRV);
        ImageView returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        OrderHistoryDetailAdapter orderDetailAdapter = new OrderHistoryDetailAdapter(this, orderBooks, orderStatus, orderTotal);
        orderDetailRV.setAdapter(orderDetailAdapter);
    }

    private void loadData() {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        position = bundle.getString("position");
    }

}