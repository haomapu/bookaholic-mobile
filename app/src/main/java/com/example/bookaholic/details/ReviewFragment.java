package com.example.bookaholic.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {
    ArrayList<Comment> comments;
    RecyclerView commentListView;
    public ReviewFragment(ArrayList<Comment> comments) {
        this.comments = comments;
    }
    public static ReviewFragment newInstance(ArrayList<Comment> comments) {
        ReviewFragment fragment = new ReviewFragment(comments);

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.review_fragment, null);

        initComment(view);

        return view;
    }
    public void initComment(View view){
        ReviewAdapter adapter = new ReviewAdapter(comments);
        commentListView = view.findViewById(R.id.commentListView);
        commentListView.setAdapter(adapter);
    }


}
