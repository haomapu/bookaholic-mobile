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
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductListFragment extends Fragment implements UserDataChangedListener, BooksDataChangedListener{

    private ImageButton buttonGoToCart, buttonFilter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private BookAdapter adapter;
    private ScrollView filterContainer;
    private Button buttonTypeScience, buttonTypeRomantic, buttonTypeMystery,
            buttonTypeHorror, buttonTypeShortStories,
            buttonTypeCook, buttonTypeEssay, buttonTypeHistory;
    private Button filterConfirmButton, filterResetButton;
    private EditText inputMinPrice, inputMaxPrice;
    public static final Integer RecordAudioRequestCode = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    ArrayList<String> selectedType = new ArrayList<>();
    Integer minPrice = null, maxPrice = null;

    private RecyclerView bestSellerRecyclerView;
    private RecyclerView recentlyAddRecyclerView;
    private RecyclerView offerRecyclerView;
    private OfferAdapter offerAdapter;
    private BestSellerAdapter bestSellerAdapter;
    private RecentlyAddAdapter recentlyAddAdapter;
    private NotificationBadge shopping_badge;

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

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        adapter = new BookAdapter(view.getContext(), Book.allBooks);

        bestSellerAdapter = new BestSellerAdapter(view.getContext(), Book.allBooks);
        bestSellerAdapter.filterByBuyer();
        bestSellerRecyclerView = view.findViewById(R.id.bestSellerRecyclerView);
        bestSellerRecyclerView.setAdapter(bestSellerAdapter);

        recentlyAddAdapter = new RecentlyAddAdapter(view.getContext(), Book.allBooks);
        recentlyAddAdapter.filterByDate();
        recentlyAddRecyclerView = view.findViewById(R.id.recentlyAddRecyclerView);
        recentlyAddRecyclerView.setAdapter(recentlyAddAdapter);

        List<Offer> offerList = new ArrayList<>();
        offerList.add(new Offer(R.drawable.sales1, "Up to 20%"));
        offerList.add(new Offer(R.drawable.sales2, "Up to 50%"));
        offerList.add(new Offer(R.drawable.sales3, "Up to 30%"));

        offerAdapter = new OfferAdapter(offerList);
        offerRecyclerView = view.findViewById(R.id.offerRecyclerView);
        offerRecyclerView.setAdapter(offerAdapter);

        shopping_badge = view.findViewById(R.id.shopping_badge);
        shopping_badge.setNumber(Order.currentOrder.orderSize());

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
        buttonTypeHorror = view.findViewById(R.id.button_select_filter_type_horror);
        buttonTypeCook = view.findViewById(R.id.button_select_filter_type_cookbooks);
        buttonTypeEssay = view.findViewById(R.id.button_select_filter_type_essay);
        buttonTypeHistory = view.findViewById(R.id.button_select_filter_type_history);
        buttonTypeShortStories = view.findViewById(R.id.button_select_filter_type_shortstories);
        buttonTypeMystery = view.findViewById(R.id.button_select_filter_type_mystery);
        buttonTypeRomantic = view.findViewById(R.id.button_select_filter_type_romance);

        buttonTypeScience.setOnClickListener(filterSelectListener);
        buttonTypeHorror.setOnClickListener(filterSelectListener);
        buttonTypeCook.setOnClickListener(filterSelectListener);
        buttonTypeEssay.setOnClickListener(filterSelectListener);
        buttonTypeHistory.setOnClickListener(filterSelectListener);
        buttonTypeShortStories.setOnClickListener(filterSelectListener);
        buttonTypeMystery.setOnClickListener(filterSelectListener);
        buttonTypeRomantic.setOnClickListener(filterSelectListener);

        inputMinPrice = view.findViewById(R.id.edittextMinimumPrice);
        inputMaxPrice = view.findViewById(R.id.edittextMaximumPrice);


        buttonFilter = view.findViewById(R.id.button_filter);
        buttonFilter.setOnClickListener(v -> showFilterMenu());


        return view;
    }

    View.OnClickListener filterSelectListener = v -> {
        if (v.getId() == R.id.button_select_filter_type_science) {
            if (selectedType.contains("science")) selectedType.remove("science");
            else selectedType.add("science");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_romance) {
            if (selectedType.contains("romance")) selectedType.remove("romance");
            else selectedType.add("romance");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_mystery) {
            if (selectedType.contains("mystery")) selectedType.remove("mystery");
            else selectedType.add("mystery");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_horror) {
            if (selectedType.contains("horror")) selectedType.remove("horror");
            else selectedType.add("horror");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_shortstories) {
            if (selectedType.contains("shortstories")) selectedType.remove("shortstories");
            else selectedType.add("shortstories");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_history) {
            if (selectedType.contains("history")) selectedType.remove("history");
            else selectedType.add("history");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_essay) {
            if (selectedType.contains("essay")) selectedType.remove("essay");
            else selectedType.add("essay");
            updateTypeFilterButtons();
        } else if (v.getId() == R.id.button_select_filter_type_cookbooks) {
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
        bestSellerAdapter.setBooks(Book.allBooks);
        bestSellerAdapter.filterByBuyer();
        recentlyAddAdapter.setBooks(Book.allBooks);
        recentlyAddAdapter.filterByDate();
        adapter.notifyDataSetChanged();
        bestSellerAdapter.notifyDataSetChanged();
        recentlyAddAdapter.notifyDataSetChanged();
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

        Intent intent = new Intent(ProductListFragment.this.getActivity(), SearchResultsActivity.class);
        intent.putExtra("selectedType",selectedType);
        intent.putExtra("minPrice",minPrice);
        intent.putExtra("maxPrice",maxPrice);
        startActivity(intent);
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
            Intent intent = new Intent(ProductListFragment.this.getActivity(), SearchResultsActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
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
