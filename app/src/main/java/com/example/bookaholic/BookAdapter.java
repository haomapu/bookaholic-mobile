package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import static com.example.bookaholic.FirebaseHelper.downloadFile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;
import com.example.bookaholic.details.Detail;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> books;
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.setBooks(books);
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = new ArrayList<>();
        for (Book book : books) {
            this.books.add(book.deepCopy());
        }
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_book_itemview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        try {
            Book book = books.get(position);
            Log.d(TAG, book.toString());
            holder.imageView.setImageResource(book.getImageURL());
            holder.nameView.setText(book.getTitle());
            holder.priceView.setText(book.getDisplayablePrice());
            holder.typesView.setText(book.getCategory());
            holder.authorView.setText(book.getAuthor());
            holder.layout.setOnClickListener(v -> startBookDetailsActivity(book));

            OnSuccessListener<byte[]> onDownloadImageSuccessListener = bytes -> {
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(imageBitmap);
            };

//            downloadFile(book.getImageURL(), onDownloadImageSuccessListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBookDetailsActivity(Book book) {
        try {
            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("bookName", book.getTitle());
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView, priceView, typesView, authorView;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_book_home_itemview);
            nameView = itemView.findViewById(R.id.textview_bookname_home_itemview);
            priceView = itemView.findViewById(R.id.textview_bookprice_home_itemview);
            typesView = itemView.findViewById(R.id.textview_booktype_home_itemview);
            authorView = itemView.findViewById(R.id.textview_bookauthor_home_itemview);
            layout = itemView.findViewById(R.id.layout_book_home_itemview);
        }
    }
}
