package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.Locale;

public class ProductListFragment extends Fragment implements UserDataChangedListener, BooksDataChangedListener{

    private TextView usernameView;
    private ImageButton buttonGoToCart, buttonFilter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private BookAdapter adapter;
    private ScrollView filterContainer;
    private Button buttonTypeScience, buttonTypeLifeStyle;
    private Button buttonAuthorBlack, buttonAuthorWhite, buttonAuthorGray, buttonAuthorYellow;
    private Button filterConfirmButton, filterResetButton;
    private EditText inputMinPrice, inputMaxPrice;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ImageView micButton;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    ArrayList<String> selectedType = new ArrayList<>();
    ArrayList<String> selectedAuthor = new ArrayList<>();
    Integer minPrice = null, maxPrice = null;

    public ProductListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Reload arguments from savedInstanceState
        }
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this.requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this.requireContext(), "Permission Denied. Feature is unavailable! ", Toast.LENGTH_SHORT).show();
                    }
                });

        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
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
        searchView.setOnQueryTextListener(searchQueryTextListener);

        buttonGoToCart = view.findViewById(R.id.imagebutton_home_mycart);
//        updateCartButton();
        buttonGoToCart.setOnClickListener(v -> startCartActivity());

        filterContainer = view.findViewById(R.id.filters_container);

        filterConfirmButton = view.findViewById(R.id.button_filter_confirm);
        filterResetButton = view.findViewById(R.id.button_filter_reset);
        filterConfirmButton.setOnClickListener(v -> onConfirm());
        filterResetButton.setOnClickListener(v -> resetFilters());

        buttonTypeScience = view.findViewById(R.id.button_select_filter_type_science);
        buttonTypeLifeStyle = view.findViewById(R.id.button_select_filter_type_lifestyle);
        buttonTypeScience.setOnClickListener(filterSelectListener);
        buttonTypeLifeStyle.setOnClickListener(filterSelectListener);

        inputMinPrice = view.findViewById(R.id.edittextMinimumPrice);
        inputMaxPrice = view.findViewById(R.id.edittextMaximumPrice);

        buttonAuthorBlack = view.findViewById(R.id.button_select_filter_color_black);
        buttonAuthorWhite = view.findViewById(R.id.button_select_filter_color_white);
        buttonAuthorGray = view.findViewById(R.id.button_select_filter_color_gray);
        buttonAuthorYellow = view.findViewById(R.id.button_select_filter_color_yellow);
        buttonAuthorYellow.setOnClickListener(filterSelectListener);
        buttonAuthorGray.setOnClickListener(filterSelectListener);
        buttonAuthorWhite.setOnClickListener(filterSelectListener);
        buttonAuthorBlack.setOnClickListener(filterSelectListener);

        buttonFilter = view.findViewById(R.id.button_filter);
        buttonFilter.setOnClickListener(v -> showFilterMenu());

        micButton = view.findViewById(R.id.img_mic);

        return view;
    }

    View.OnClickListener filterSelectListener = v -> {
        if (v.getId() == R.id.button_select_filter_type_science) {
            if (selectedType.contains("science")) selectedType.remove("science");
            else selectedType.add("science");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_lifestyle) {
            if (selectedType.contains("lifestyle")) selectedType.remove("lifestyle");
            else selectedType.add("lifestyle");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_color_black) {
            if (selectedAuthor.contains("đen")) selectedAuthor.remove("đen");
            else selectedAuthor.add("đen");
            updateAuthorFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_color_white) {
            if (selectedAuthor.contains("trắng")) selectedAuthor.remove("trắng");
            else selectedAuthor.add("trắng");
            updateAuthorFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_color_gray) {
            if (selectedAuthor.contains("xám")) selectedAuthor.remove("xám");
            else selectedAuthor.add("xám");
            updateAuthorFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_color_yellow) {
            if (selectedAuthor.contains("vàng")) selectedAuthor.remove("vàng");
            else selectedAuthor.add("vàng");
            updateAuthorFilterButtons();
        }
    };

    private void resetFilters() {
        selectedAuthor.clear();
        selectedType.clear();
        minPrice = null;
        maxPrice = null;
        updateAuthorFilterButtons();
        updateTypeFilterButtons();
        inputMinPrice.setText("");
        inputMaxPrice.setText("");
    }

    private void updateAuthorFilterButtons() {
        if (selectedAuthor.contains("đen")) {
            buttonAuthorBlack.setBackgroundColor(Color.BLACK);
            buttonAuthorBlack.setTextColor(Color.WHITE);
        } else {
            buttonAuthorBlack.setBackgroundColor(Color.WHITE);
            buttonAuthorBlack.setTextColor(Color.GRAY);
        }
        if (selectedAuthor.contains("trắng")) {
            buttonAuthorWhite.setBackgroundColor(Color.BLACK);
            buttonAuthorWhite.setTextColor(Color.WHITE);
        } else {
            buttonAuthorWhite.setBackgroundColor(Color.WHITE);
            buttonAuthorWhite.setTextColor(Color.GRAY);
        }
        if (selectedAuthor.contains("xám")) {
            buttonAuthorGray.setBackgroundColor(Color.BLACK);
            buttonAuthorGray.setTextColor(Color.WHITE);
        } else {
            buttonAuthorGray.setBackgroundColor(Color.WHITE);
            buttonAuthorGray.setTextColor(Color.GRAY);
        }
        if (selectedAuthor.contains("vàng")) {
            buttonAuthorYellow.setBackgroundColor(Color.BLACK);
            buttonAuthorYellow.setTextColor(Color.WHITE);
        } else {
            buttonAuthorYellow.setBackgroundColor(Color.WHITE);
            buttonAuthorYellow.setTextColor(Color.GRAY);
        }
    }

    void updateTypeFilterButtons() {
        if (selectedType.contains("science")) {
            buttonTypeScience.setBackgroundColor(Color.BLACK);
            buttonTypeScience.setTextColor(Color.WHITE);
        } else {
            buttonTypeScience.setBackgroundColor(Color.WHITE);
            buttonTypeScience.setTextColor(Color.GRAY);
        }
        if (selectedType.contains("lifestyle")) {
            buttonTypeLifeStyle.setBackgroundColor(Color.BLACK);
            buttonTypeLifeStyle.setTextColor(Color.WHITE);
        } else {
            buttonTypeLifeStyle.setBackgroundColor(Color.WHITE);
            buttonTypeLifeStyle.setTextColor(Color.GRAY);
        }
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

        adapter.filterByOptions(selectedType, minPrice, maxPrice, selectedAuthor);
    }
    @Override
    public void updateBooksRelatedViews() {
        notifyAdapter();
        updateProgressBar();
    }

    private void startCartActivity() {
        startActivity(new Intent(ProductListFragment.this.getActivity(), CartActivity.class));
    }

    private final SearchView.OnQueryTextListener searchQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.filterByName(newText);
            return false;
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }
}
