package com.example.bookaholic.orderHistory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.bookaholic.MainActivity;
import com.example.bookaholic.R;

public class OrderHistory extends AppCompatActivity {
    RecyclerView orderRV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        if (MainActivity.currentSyncedUser.getOrderHistory() == null)
            Log.e("Error", "Sai r");
        else {
            initOrderHistory();
        }
        ImageView returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void initOrderHistory(){
        orderRV = findViewById(R.id.orderRV);
        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(this, MainActivity.currentSyncedUser.getOrderHistory());
        orderRV.setAdapter(orderHistoryAdapter);
    }
}
