package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnBack, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details);

        tvPackageName = findViewById(R.id.textViewBMCartTitle);
        edDetails = findViewById(R.id.editTextBMDTextMultiLine);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        btnBack = findViewById(R.id.buttonBMDBack);
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost: " + intent.getStringExtra("text3") + "/-");

        btnBack.setOnClickListener(v -> startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class)));

        btnAddToCart.setOnClickListener(v -> {
            SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedpreferences.getString("username", "");
            String encodedUsername = Utils.encodeUsername(username);  // Encode the username

            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(intent.getStringExtra("text3"));

            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts").child(encodedUsername).push();
            CartItem cartItem = new CartItem(product, String.valueOf(price));

            cartRef.setValue(cartItem)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to add product to cart: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
