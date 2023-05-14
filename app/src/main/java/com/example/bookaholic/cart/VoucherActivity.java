package com.example.bookaholic.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.bookaholic.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VoucherActivity extends AppCompatActivity implements OnApplyClickListener {
    RecyclerView voucherRV;
    ArrayList<Voucher> vouchers = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        initOrderHistory();
        context = (Context) getIntent().getSerializableExtra("context");

    }
    public void initOrderHistory(){
        vouchers = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Vouchers");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                    for (DataSnapshot voucherSnapshot : dataSnapshot.getChildren()) {
                        Voucher voucher = voucherSnapshot.getValue(Voucher.class);
                        vouchers.add(voucher);
                    }
                    voucherRV = findViewById(R.id.voucherRV);
                    VoucherAdapter voucherAdapter = new VoucherAdapter(VoucherActivity.this, vouchers);
                    voucherAdapter.setOnApplyClickListener(VoucherActivity.this);

                    voucherRV.setAdapter(voucherAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
    @Override
    public void onApplyClicked(Voucher voucher) {
        Voucher.currentVoucher = voucher;
        onBackPressed();
    }
}