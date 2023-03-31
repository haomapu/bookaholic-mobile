package com.example.bookaholic.details;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "BottomSheetFragment";

    private CardView mCardView;
    FragmentTransaction fragmentManager;
    RecyclerView commentListView;
    ArrayList<Comment> comments;
    public BottomSheetFragment(ArrayList<Comment> comments) {
        this.comments = comments;
        // Required empty public constructor
    }


    public static BottomSheetFragment newInstance(ArrayList<Comment> comments) {
        return new BottomSheetFragment(comments);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
//        initComment(view);
        mCardView = view.findViewById(R.id.cardView);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing to prevent dismissing the fragment when clicking on the card
            }
        });
        fragmentManager = getChildFragmentManager().beginTransaction();
        ReviewFragment reviewFragment = ReviewFragment.newInstance(comments);

        fragmentManager.replace(R.id.fragmentContainer, reviewFragment);
        fragmentManager.commit();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Set the height of the dialog to wrap content
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
