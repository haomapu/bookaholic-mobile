package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.Locale;

public class ProductListFragment extends Fragment implements UserDataChangedListener, BooksDataChangedListener{

    private TextView usernameView;
    private ImageButton buttonGoToCart, buttonFilter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private BookAdapter adapter;
    private ScrollView filterContainer;
    private Button filterConfirmButton, filterResetButton;
    private EditText inputMinPrice, inputMaxPrice;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ImageView micButton;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private int minPrice, maxPrice;

    public ProductListFragment() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

//        usernameView = view.findViewById(R.id.textview_greeting);
//        updateUsernameView();

        adapter = new BookAdapter(view.getContext(), Book.allBooks);
        recyclerView = view.findViewById(R.id.recyclerview_home);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progressbar_home_fragment);
        updateProgressBar();

        searchView = view.findViewById(R.id.searchview_home);
//        searchView.setOnQueryTextListener(searchQueryTextListener);
//
        buttonGoToCart = view.findViewById(R.id.imagebutton_home_mycart);
//        updateCartButton();
//        buttonGoToCart.setOnClickListener(v -> startMyCartActivity());
//
        filterContainer = view.findViewById(R.id.filters_container);

        filterConfirmButton = view.findViewById(R.id.button_filter_confirm);
        filterResetButton = view.findViewById(R.id.button_filter_reset);
        filterConfirmButton.setOnClickListener(v -> onConfirm());
//        filterResetButton.setOnClickListener(v -> resetFilters());

//        buttonSexMale = view.findViewById(R.id.button_select_filter_sex_male);
//        buttonSexFemale = view.findViewById(R.id.button_select_filter_sex_female);
//        buttonSexMale.setOnClickListener(filterSelectListener);
//        buttonSexFemale.setOnClickListener(filterSelectListener);
//
//        buttonAgeLow = view.findViewById(R.id.button_select_filter_age_low);
//        buttonAgeHigh = view.findViewById(R.id.button_select_filter_age_high);
//        buttonAgeLow.setOnClickListener(filterSelectListener);
//        buttonAgeHigh.setOnClickListener(filterSelectListener);

        inputMinPrice = view.findViewById(R.id.edittextMinimumPrice);
        inputMaxPrice = view.findViewById(R.id.edittextMaximumPrice);
//
//        buttonColorBlack = view.findViewById(R.id.button_select_filter_color_black);
//        buttonColorWhite = view.findViewById(R.id.button_select_filter_color_white);
//        buttonColorGray = view.findViewById(R.id.button_select_filter_color_gray);
//        buttonColorYellow = view.findViewById(R.id.button_select_filter_color_yellow);
//        buttonColorBrown = view.findViewById(R.id.button_select_filter_color_brown);
//        buttonColorBrown.setOnClickListener(filterSelectListener);
//        buttonColorYellow.setOnClickListener(filterSelectListener);
//        buttonColorGray.setOnClickListener(filterSelectListener);
//        buttonColorWhite.setOnClickListener(filterSelectListener);
//        buttonColorBlack.setOnClickListener(filterSelectListener);
//
        buttonFilter = view.findViewById(R.id.imagebutton_filter);
        buttonFilter.setOnClickListener(v -> showFilterMenu());
//
//        micButton = view.findViewById(R.id.img_mic);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyAdapter() {
        adapter.setBooks(Book.allBooks);
        adapter.notifyDataSetChanged();
    }

    public void updateProgressBar() {
        try {
            progressBar.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        } catch (Exception exception) {
            Log.d(TAG, exception.toString());
        }
    }
    @Override
    public void updateUserRelatedViews() {

    }
    private void hideFilterMenu() {
        Log.d(TAG, "hide filter now");
        ObjectAnimator animator = ObjectAnimator.ofFloat(filterContainer, "translationX", 0);
        animator.setDuration(500);
        animator.start();
    }

    private void showFilterMenu() {
        Log.d(TAG, "filter button clicked");
        ObjectAnimator animator = ObjectAnimator.ofFloat(filterContainer, "translationX", -filterContainer.getWidth());
        animator.setDuration(500);
        animator.start();
    }

    private void onConfirm() {
        hideFilterMenu();
        String minPriceString = inputMinPrice.getText().toString();
        String maxPriceString = inputMaxPrice.getText().toString();

        if (!minPriceString.isEmpty())
            minPrice = Integer.parseInt(inputMinPrice.getText().toString());

        if (!maxPriceString.isEmpty())
            maxPrice = Integer.parseInt(inputMaxPrice.getText().toString());

//        adapter.filterByOptions(selectedSex, selectedAge, minPrice, maxPrice, selectedColor);
    }
    @Override
    public void updateBooksRelatedViews() {
        notifyAdapter();
        updateProgressBar();
    }
}
