package com.example.bookaholic.orderHistory;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaholic.OrderBook;
import com.example.bookaholic.R;
import com.example.bookaholic.details.Book;
import com.example.bookaholic.details.Detail;
import com.example.bookaholic.details.Review;

import java.util.ArrayList;

        public class OrderHistoryDetailAdapter extends RecyclerView.Adapter<OrderHistoryDetailAdapter.ViewHolder> {
            private ArrayList<OrderBook> mDataList;
            private Context context;

            public static class ViewHolder extends RecyclerView.ViewHolder {
                public ImageView bookImageView;
        public TextView titleTxt, quantityTxt, priceTxt;

        public Button reviewBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            reviewBtn = itemView.findViewById(R.id.reviewBtn);
        }
    }

    public OrderHistoryDetailAdapter(Context context, ArrayList<OrderBook> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_detail_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderBook orderBook = mDataList.get(position);
        Book book = orderBook.getBook();
        // set image using Glide library
        Glide.with(holder.bookImageView.getContext())
                .load(book.getImages().get(0))
                .into(holder.bookImageView);

        holder.reviewBtn.setOnClickListener(v -> {
            Review rateDialog = new Review(v.getContext(), book);
            rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            rateDialog.setCancelable(false);
            rateDialog.show();
        });
        holder.titleTxt.setText(book.getTitle());
        holder.quantityTxt.setText(String.valueOf(orderBook.getQuantity()));
        holder.priceTxt.setText(String.valueOf(book.getPrice() * orderBook.getQuantity()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, Detail.class);
            Bundle bundle = new Bundle();
            bundle.putString("bookName", book.getTitle());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class OrderDetail {
        private String bookImageUrl, bookTitle;
        private int quantity;
        private double price;

        public OrderDetail(String bookImageUrl, String bookTitle, int quantity, double price) {
            this.bookImageUrl = bookImageUrl;
            this.bookTitle = bookTitle;
            this.quantity = quantity;
            this.price = price;
        }

        public String getBookImageUrl() {
            return bookImageUrl;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }
}