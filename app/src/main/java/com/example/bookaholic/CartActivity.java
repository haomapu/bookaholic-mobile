package com.example.bookaholic;

import static android.view.View.GONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.cart.Voucher;
import com.example.bookaholic.cart.VoucherActivity;
import com.example.bookaholic.details.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPriceTextView, mShippingFeeTextView, mCartTotalPriceTextView;
    private ArrayList<OrderBook> mBookList;
    private TextView discountPriceTextview;
    private Button confirmButton, voucherButton;
    float cartTotalPrice;
    float newPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        discountPriceTextview = findViewById(R.id.discountPriceTextview);
        initConfirmButton();
        initVoucherButton();
        discountPriceTextview.setPaintFlags(discountPriceTextview.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        ImageButton buttonBack = findViewById(R.id.button_cart_back);
        buttonBack.setOnClickListener(v -> CartActivity.this.finish());

        // Initialize the book list with some sample books
        mBookList = Order.currentOrder.getOrderBooks();

        // Get a reference to the RecyclerView
        mCartRecyclerView = findViewById(R.id.cartRecyclerView);
        mTotalPriceTextView = findViewById(R.id.totalPriceTextView);
        mShippingFeeTextView = findViewById(R.id.shippingFeeTextView);
        mCartTotalPriceTextView = findViewById(R.id.cartTotalPriceTextView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCartRecyclerView.setLayoutManager(layoutManager);

        // Create an instance of the CartAdapter class and pass the book list to its constructor
        mCartAdapter = new CartAdapter(mBookList, this);

        // Set the CartAdapter instance to the RecyclerView
        mCartRecyclerView.setAdapter(mCartAdapter);

        // Calculate the total price
        float totalPrice = 0;
        for (OrderBook orderBook : mBookList) {
            totalPrice += orderBook.getBook().getPrice() * orderBook.getQuantity();
        }
        float shippingFee = 0;
        if (mBookList.size() != 0) {
            shippingFee = 30000;
        }
        cartTotalPrice = totalPrice + shippingFee;

        mShippingFeeTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(shippingFee) + " đ");
        mTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice) + " đ");
        mCartTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(cartTotalPrice) + " đ");
        Order.currentOrder.setTotalPrice((totalPrice));

        mCartAdapter.setOnQuantityChangeListener(new CartAdapter.OnQuantityChangedListener() {
            @Override
            public void onQuantityChanged() {
                // Calculate the total price
                float totalPrice = 0;
                for (OrderBook orderBook : mBookList) {
                    totalPrice += orderBook.getBook().getPrice() * orderBook.getQuantity();
                }
                float shippingFee = 0;
                if (mBookList.size() != 0) {
                    shippingFee = 30000;
                }
                float cartTotalPrice = totalPrice + shippingFee;
                Order.currentOrder.setTotalPrice((totalPrice));
                mShippingFeeTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(shippingFee) + " đ");
                mTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice) + " đ");
                mCartTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(cartTotalPrice) + " đ");
            }
        });

        mCartAdapter.setOnDeleteListener(new CartAdapter.onDeleteListener() {
            public void onDelete() {
                // Calculate the total price
                float totalPrice = 0;
                for (OrderBook orderBook : mBookList) {
                    totalPrice += orderBook.getBook().getPrice() * orderBook.getQuantity();
                }
                float shippingFee = 0;
                if (mBookList.size() != 0) {
                    shippingFee = 30000;
                }
                float cartTotalPrice = totalPrice + shippingFee;
                Order.currentOrder.setTotalPrice((totalPrice));
                mShippingFeeTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(shippingFee) + " đ");
                mTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice) + " đ");
                mCartTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(cartTotalPrice) + " đ");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Voucher.currentVoucher == null){
            discountPriceTextview.setVisibility(GONE);
        } else {

            discountPriceTextview.setVisibility(View.VISIBLE);
            discountPriceTextview.setText(NumberFormat.getNumberInstance(Locale.US).format(cartTotalPrice) + " đ");
            if (Voucher.currentVoucher.getTypeVoucher().contains("Price")){
                newPrice = cartTotalPrice - Voucher.currentVoucher.getDiscountVoucher();
                mCartTotalPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(newPrice) + " đ");

                Order.currentOrder.setTotalPrice((newPrice));
            }
            else {
                mCartTotalPriceTextView.setText(String.valueOf(cartTotalPrice - Voucher.currentVoucher.getDiscountVoucher()*cartTotalPrice));
            }
        }
    }


    public void initVoucherButton(){
        voucherButton = findViewById(R.id.voucherButton);

        voucherButton.setOnClickListener(v ->{
            Intent intent = new Intent(this, VoucherActivity.class);
            startActivity(intent);
        });
    }
    public void initConfirmButton() {
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            for (OrderBook orderBook : Order.currentOrder.getOrderBooks()){
                if (orderBook.getBook().getQuantity() < orderBook.getQuantity()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error");
                    builder.setMessage(orderBook.getBook().getTitle() + " exceeded available stock");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                    return;
                }
            }

            for (OrderBook orderBook : Order.currentOrder.getOrderBooks()){
                DatabaseReference quantityRef = database.getReference("Books").child(orderBook.getBook().getId()).child("quantity");
                quantityRef.setValue(orderBook.getBook().getQuantity() - orderBook.getQuantity());
                DatabaseReference buyerRef = database.getReference("Books")
                        .child(orderBook.getBook().getId()).child("buyer");
                buyerRef.setValue(orderBook.getBook().getBuyer() + 1);
            }

            DatabaseReference myRef = database.getReference("Users")
                    .child(MainActivity.currentSyncedUser.getId()).child("orderHistory");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                        ArrayList<Order> orderHistory = new ArrayList<>();
                        for (DataSnapshot orderHistorySnapshot : dataSnapshot.getChildren()) {
                            Order order = orderHistorySnapshot.getValue(Order.class);
                            orderHistory.add(order);
                        }
                        Order.currentOrder.setOrderOwner(MainActivity.currentSyncedUser.getId());
                        Order.currentOrder.setId(MainActivity.currentSyncedUser.getOrderHistory().size());
                        orderHistory.add(Order.currentOrder);

                        myRef.setValue(orderHistory);
                    } else {
                        ArrayList<Order> orderHistory = new ArrayList<>();
                        Order.currentOrder.setOrderOwner(MainActivity.currentSyncedUser.getId());
                        orderHistory.add(Order.currentOrder);
                        myRef.setValue(orderHistory);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Order Submitted");
            builder.setMessage("Your order has been successfully submitted!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Order.currentOrder = new Order();

                }
            });
            builder.show();
        });


    }
}
