package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookaholic.databinding.ActivityAddBookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBook extends AppCompatActivity {
    private ActivityAddBookBinding binding;
    private DatabaseReference database;
    private Uri imageUri;

    private final int pickImage = 100;
    private ImageButton imageBtn;
    private String image, name, author,
            category, desciption, price, publicationDate,
            publisher, size, numberOfPages, typeOfCover;

    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance().getReference("Books");

        // category
        Spinner spinner = binding.categoryBook;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = parent.getItemAtPosition(0).toString();
            }
        });
        tvDate = binding.publicationDateBook;
        imageBtn = binding.bGallery;

        binding.bGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, pickImage);
            }
        });

        // Show DatePicker when TextView is clicked
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog và thiết lập sự kiện chọn ngày
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBook.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // Thiết lập ngày được chọn vào TextView
                        publicationDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear+1, year);
                        tvDate.setText(publicationDate);
                    }
                }, year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.nameBook.getText().toString();
                author = binding.nameAuthor.getText().toString();
                desciption = binding.descriptionBook.getText().toString();
                price = binding.priceBook.getText().toString();
                publisher = binding.publisherBook.getText().toString();
                size = binding.sizeBook.getText().toString();
                numberOfPages = binding.numberOfPagesBook.getText().toString();
                typeOfCover = binding.typeOfCoverBook.getText().toString();
                Log.e(TAG,name);
                Log.e(TAG,author);
                Log.e(TAG,category);
                Log.e(TAG,desciption);
                Log.e(TAG,price);
                Log.e(TAG,publicationDate);
                Log.e(TAG,publisher);
                Log.e(TAG,size);
                Log.e(TAG,numberOfPages);
                Log.e(TAG,typeOfCover);

                uploadData();
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddBook.this, ProfileAcitivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data.getData();
            imageBtn.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        Date now = new Date();
        String fileName = formatter.format(now);
        StorageReference storage = FirebaseStorage.getInstance().getReference("image/" + fileName);

        storage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageBtn.setImageURI(null);
                Toast.makeText(getApplicationContext(), "Successful Uploaded", Toast.LENGTH_SHORT).show();

                File localFile;
                try {
                    localFile = File.createTempFile(fileName, "");
                    storage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageBtn.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        image = uri.toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void uploadData() {
        Book book = new Book(image, name, author, category, desciption, price
                , publicationDate, publisher, size, numberOfPages, typeOfCover);

        database.child(name.toString()).setValue(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Successful Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddBook.this, AddBook.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddBook.this, AddBook.class));
                    }
                });
    }
}