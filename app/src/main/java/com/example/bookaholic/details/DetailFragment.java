package com.example.bookaholic.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    private String mAuthorContent, mCategoryContent, mDateContent, mCoverTypeContent, mSizeContent, mNumberPageContent;

    TextView authorContent, categoryContent, dateContent, coverTypeContent, sizeContent, numberPageContent;
    public DetailFragment() {

    }
    public static DetailFragment newInstance(String authorContent,String categoryContent,String dateContent,String coverTypeContent,String sizeContent,String numberPageContent) {
        DetailFragment fragment = new DetailFragment();
        fragment.mAuthorContent = authorContent;
        fragment.mCategoryContent = categoryContent;
        fragment.mDateContent = dateContent;
        fragment.mCoverTypeContent = coverTypeContent;
        fragment.mSizeContent = sizeContent;
        fragment.mNumberPageContent = numberPageContent;
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.book_detail_fragment, null);

        initDetail(view);

        return view;
    }
    public void initDetail(View view){
        authorContent = view.findViewById(R.id.authorContent);
        categoryContent = view.findViewById(R.id.categoryContent);
        dateContent = view.findViewById(R.id.dateContent);
        coverTypeContent = view.findViewById(R.id.coverTypeContent);
        sizeContent = view.findViewById(R.id.sizeContent);
        numberPageContent = view.findViewById(R.id.numberPageContent);

        authorContent.setText(mAuthorContent);
        categoryContent.setText(mCategoryContent);
        dateContent.setText(mDateContent);
        coverTypeContent.setText(mCoverTypeContent);
        sizeContent.setText(mSizeContent);
        numberPageContent.setText(mNumberPageContent);
    }
}