package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private ImageButton buttonGoToCart, buttonFilter;
    private ImageView returnBtnSearch;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ScrollView filterContainer;
    private Button buttonTypeScience, buttonTypeRomantic, buttonTypeMystery,
            buttonTypeHorror, buttonTypeShortStories,
            buttonTypeCook, buttonTypeEssay, buttonTypeHistory;
    private Button filterConfirmButton, filterResetButton;
    private EditText inputMinPrice, inputMaxPrice;
    ArrayList<String> selectedType = new ArrayList<>();
    Integer minPrice = null, maxPrice = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view_activity);
        adapter = new BookAdapter(this, Book.allBooks);
        Intent intent = this.getIntent();
        adapter.filterByOptions(intent.getStringArrayListExtra("selectedType"), intent.getIntExtra("minPrice", Integer.MIN_VALUE), intent.getIntExtra("maxPrice", Integer.MAX_VALUE));
        if (this.getIntent().getStringExtra("query") != null) {
            adapter.filterByName(this.getIntent().getStringExtra("query"));
        }
        recyclerView = findViewById(R.id.recyclerview_search);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchview_searching);
        searchView.setOnQueryTextListener(searchQueryTextListener);

        buttonGoToCart = findViewById(R.id.imagebutton_search_mycart);
        buttonGoToCart.setOnClickListener(v -> startCartActivity());
        returnBtnSearch = findViewById(R.id.returnBtnSearch);
        returnBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        filterContainer = findViewById(R.id.filters_container_search);
        filterConfirmButton = findViewById(R.id.button_filter_confirm_search);
        filterResetButton = findViewById(R.id.button_filter_reset_search);
        filterConfirmButton.setOnClickListener(v -> onConfirm());
        filterResetButton.setOnClickListener(v -> resetFilters());

        buttonTypeScience = findViewById(R.id.button_select_filter_type_science_search);
        buttonTypeHorror = findViewById(R.id.button_select_filter_type_horror_search);
        buttonTypeCook = findViewById(R.id.button_select_filter_type_cookbooks_search);
        buttonTypeEssay = findViewById(R.id.button_select_filter_type_essay_search);
        buttonTypeHistory = findViewById(R.id.button_select_filter_type_history_search);
        buttonTypeShortStories = findViewById(R.id.button_select_filter_type_shortstories_search);
        buttonTypeMystery = findViewById(R.id.button_select_filter_type_mystery_search);
        buttonTypeRomantic = findViewById(R.id.button_select_filter_type_romance_search);

        buttonTypeScience.setOnClickListener(filterSelectListener);
        buttonTypeHorror.setOnClickListener(filterSelectListener);
        buttonTypeCook.setOnClickListener(filterSelectListener);
        buttonTypeEssay.setOnClickListener(filterSelectListener);
        buttonTypeHistory.setOnClickListener(filterSelectListener);
        buttonTypeShortStories.setOnClickListener(filterSelectListener);
        buttonTypeMystery.setOnClickListener(filterSelectListener);
        buttonTypeRomantic.setOnClickListener(filterSelectListener);

        inputMinPrice = findViewById(R.id.edittextMinimumPrice_search);
        inputMaxPrice = findViewById(R.id.edittextMaximumPrice_search);

        buttonFilter = findViewById(R.id.button_filter_search);
        buttonFilter.setOnClickListener(v -> showFilterMenu());

    }

    View.OnClickListener filterSelectListener = v -> {
        if (v.getId() == R.id.button_select_filter_type_science_search) {
            if (selectedType.contains("science")) selectedType.remove("science");
            else selectedType.add("science");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_romance_search) {
            if (selectedType.contains("romance")) selectedType.remove("romance");
            else selectedType.add("romance");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_mystery_search) {
            if (selectedType.contains("mystery")) selectedType.remove("mystery");
            else selectedType.add("mystery");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_horror_search) {
            if (selectedType.contains("horror")) selectedType.remove("horror");
            else selectedType.add("horror");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_shortstories_search) {
            if (selectedType.contains("shortstories")) selectedType.remove("shortstories");
            else selectedType.add("shortstories");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_history_search) {
            if (selectedType.contains("history")) selectedType.remove("history");
            else selectedType.add("history");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_essay_search) {
            if (selectedType.contains("essay")) selectedType.remove("essay");
            else selectedType.add("essay");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_cookbooks_search) {
            if (selectedType.contains("cookbooks")) selectedType.remove("cookbooks");
            else selectedType.add("cookbooks");
            updateTypeFilterButtons();
        }
    };

    private void resetFilters() {
        selectedType.clear();
        minPrice = null;
        maxPrice = null;
        updateTypeFilterButtons();
        inputMinPrice.setText("");
        inputMaxPrice.setText("");
    }

    void updateTypeFilterButtons() {
        if (selectedType.contains("romance")) {
            buttonTypeRomantic.setBackgroundColor(Color.BLACK);
            buttonTypeRomantic.setTextColor(Color.WHITE);
        } else {
            buttonTypeRomantic.setBackgroundColor(Color.WHITE);
            buttonTypeRomantic.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("mystery")) {
            buttonTypeMystery.setBackgroundColor(Color.BLACK);
            buttonTypeMystery.setTextColor(Color.WHITE);
        } else {
            buttonTypeMystery.setBackgroundColor(Color.WHITE);
            buttonTypeMystery.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("science")) {
            buttonTypeScience.setBackgroundColor(Color.BLACK);
            buttonTypeScience.setTextColor(Color.WHITE);
        } else {
            buttonTypeScience.setBackgroundColor(Color.WHITE);
            buttonTypeScience.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("horror")) {
            buttonTypeHorror.setBackgroundColor(Color.BLACK);
            buttonTypeHorror.setTextColor(Color.WHITE);
        } else {
            buttonTypeHorror.setBackgroundColor(Color.WHITE);
            buttonTypeHorror.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("shortstories")) {
            buttonTypeShortStories.setBackgroundColor(Color.BLACK);
            buttonTypeShortStories.setTextColor(Color.WHITE);
        } else {
            buttonTypeShortStories.setBackgroundColor(Color.WHITE);
            buttonTypeShortStories.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("history")) {
            buttonTypeHistory.setBackgroundColor(Color.BLACK);
            buttonTypeHistory.setTextColor(Color.WHITE);
        } else {
            buttonTypeHistory.setBackgroundColor(Color.WHITE);
            buttonTypeHistory.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("essay")) {
            buttonTypeEssay.setBackgroundColor(Color.BLACK);
            buttonTypeEssay.setTextColor(Color.WHITE);
        } else {
            buttonTypeEssay.setBackgroundColor(Color.WHITE);
            buttonTypeEssay.setTextColor(Color.BLACK);
        }
        if (selectedType.contains("cookbooks")) {
            buttonTypeCook.setBackgroundColor(Color.BLACK);
            buttonTypeCook.setTextColor(Color.WHITE);
        } else {
            buttonTypeCook.setBackgroundColor(Color.WHITE);
            buttonTypeCook.setTextColor(Color.BLACK);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void notifyAdapter() {
        adapter.setBooks(Book.allBooks);
        adapter.notifyDataSetChanged();
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

        Intent intent = new Intent(SearchResultsActivity.this, SearchResultsActivity.class);
        intent.putExtra("selectedType",selectedType);
        intent.putExtra("minPrice",minPrice);
        intent.putExtra("maxPrice",maxPrice);
        startActivity(intent);
    }

    private void startCartActivity() {
        startActivity(new Intent(SearchResultsActivity.this, CartActivity.class));
    }

    private final SearchView.OnQueryTextListener searchQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Intent intent = new Intent(SearchResultsActivity.this, SearchResultsActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

}

