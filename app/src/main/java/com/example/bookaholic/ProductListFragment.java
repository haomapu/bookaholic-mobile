package com.example.bookaholic;

import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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


    public ProductListFragment() {

    }
    @Override
    public void updateUserRelatedViews() {

    }

    @Override
    public void updateCatsRelatedViews() {

    }
}
