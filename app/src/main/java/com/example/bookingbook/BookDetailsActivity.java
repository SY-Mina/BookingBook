package com.example.bookingbook;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookDetailsActivity extends AppCompatActivity {

    ImageView img_book;
    Button btn_rental, btn_return;
    TextView txt_title, txt_author, txt_translator, txt_publisher, txt_summary, txt_ISBN;
    String title, author, publisher, ISBN, imageUrl;
    BitmapDrawable drawable;
    Bitmap bitmap = drawable.getBitmap();


    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);


        img_book = findViewById(R.id.searched_detail_img);
        drawable = (BitmapDrawable) img_book.getDrawable();
        bitmap = drawable.getBitmap();

        btn_rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title = txt_title.getText().toString();
                author = txt_author.getText().toString();
                publisher = txt_publisher.getText().toString();
                ISBN = txt_ISBN.getText().toString();

                Book book = new Book(title, author, publisher, ISBN, imageUrl);

                sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
                String name = sharedPreferences.getString("login","");
                userReference.child("users").child(ISBN).child(title).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Successfully rented.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "rental Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title = txt_title.getText().toString();
                author = txt_author.getText().toString();
                publisher = txt_publisher.getText().toString();
                ISBN = txt_ISBN.getText().toString();

                sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
                String name = sharedPreferences.getString("login","");

                userDatabase.getReference().child("users").child(name).child(ISBN).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Successfully returned", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "return Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
