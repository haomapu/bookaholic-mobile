package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bookaholic.details.Book;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ManageBookAdapter extends RecyclerView.Adapter<ManageBookAdapter.ViewHolder> {

    private ArrayList<Book> mBooks;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onUpdateClick(int position);
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ManageBookAdapter(Context context, ArrayList<Book> books) {
        mContext = context;
        mBooks = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book currentBook = mBooks.get(position);

        Glide.with(holder.itemView)
                .load(currentBook.getImages().get(0))
                .into(holder.mImageView);
        holder.mTextViewTitle.setText(currentBook.getTitle());
        holder.mTextViewPrice.setText(String.valueOf(currentBook.getPrice()));
        holder.mTextViewRecentAdd.setText(currentBook.getRecentlyDate());
        holder.mTextViewQuantity.setText(String.valueOf(currentBook.getQuantity()));
        holder.mTextViewSold.setText(String.valueOf(currentBook.getBuyer()));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewTitle, mTextViewPrice, mTextViewRecentAdd, mTextViewQuantity, mTextViewSold;
        public Button mUpdateButton, mRemoveButton;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.bookImageView);
            mTextViewTitle = itemView.findViewById(R.id.bookNameTextView);
            mTextViewPrice = itemView.findViewById(R.id.bookPriceTextView);
            mTextViewRecentAdd = itemView.findViewById(R.id.bookRecentAddTextView);
            mTextViewQuantity = itemView.findViewById(R.id.bookQuantityTextView);
            mTextViewSold = itemView.findViewById(R.id.bookBuyerTextView);
            mUpdateButton = itemView.findViewById(R.id.updateButton);
            mRemoveButton = itemView.findViewById(R.id.removeButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUpdateClick(position);
                        }
                    }
                }
            });

            mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });
        }
    }
}

