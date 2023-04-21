package com.example.bookaholic.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.bookaholic.Order;
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
        if (Order.currentOrder == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("This cart is not qualified");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();

                    finish();
                }
            });
            builder.show();
            return;
        }
        if (Order.currentOrder.getTotalPrice() < voucher.getMinimumVoucher()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("This cart is not qualified");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();

                    finish();
                }
            });
            builder.show();
            return;
        }
        if (Order.currentOrder.orderSize() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("This cart is not qualified");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();

                    finish();
                }
            });
            builder.show();
            return;
        }
        if (voucher.getQuantityVoucher() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("This voucher is exceeded");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                    finish();
                }
            });
            builder.show();
            return;
        }

        Voucher.currentVoucher = voucher;
        onBackPressed();
    }
}