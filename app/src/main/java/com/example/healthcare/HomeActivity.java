package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private TextView usernameView, userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menghubungkan TextView dengan ID yang sesuai di layout
        usernameView = findViewById(R.id.usernameView);
        userNameTextView = findViewById(R.id.userNameTextView);

        // Mengambil username dari Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Mengambil SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);

        // Mengambil username dari SharedPreferences jika tidak ada di Intent
        if (username == null || username.isEmpty()) {
            username = sharedPreferences.getString("username", "User");
        } else {
            // Simpan username ke SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.apply();
        }

        // Menampilkan username di TextView
        usernameView.setText("Hello");
        userNameTextView.setText(username);

        // Menampilkan pesan sambutan
        Toast.makeText(getApplicationContext(), "Selamat datang " + username, Toast.LENGTH_SHORT).show();

        // Menambahkan listener untuk CardView Logout
        final String finalUsername = username; // Make the variable effectively final
        findViewById(R.id.cardExit).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        // Menambahkan listener untuk CardView Find Doctor
        findViewById(R.id.cardFindDoctor).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        });

        // Menambahkan listener untuk CardView Edit Profile
        findViewById(R.id.cardEditProfile).setOnClickListener(v -> {
            Intent editIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
            editIntent.putExtra("username", finalUsername);
            startActivity(editIntent);
        });

        // Menambahkan listener untuk CardView Lab Test
        findViewById(R.id.cardLabTest).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
        });

        // Menambahkan listener untuk CardView Order Details
        findViewById(R.id.cardOrderDetails).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, OrderDetailsActivity.class));
        });

        // Menambahkan listener untuk CardView Buy Medicine
        CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        buyMedicine.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BuyMedicineActivity.class));
        });

        // Implementasikan listener untuk CardView lainnya secara serupa
    }
}
