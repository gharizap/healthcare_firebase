package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode, edtype;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContactNumber);
        edpincode = findViewById(R.id.editTextBMBpincode);
        edtype = findViewById(R.id.editTextBMBType);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        Intent intent = getIntent();
        String[] priceParts = intent.getStringExtra("price").split(":");
        String priceStr = priceParts[1].replaceAll("[^\\d.]", ""); // Extracting only the numerical value
        float price = Float.parseFloat(priceStr);
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");

            if (username.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
                return;
            }

            Database db = new Database();
            OrderDetail order = new OrderDetail(
                    edname.getText().toString(),
                    edaddress.getText().toString(),
                    edcontact.getText().toString(),
                    edpincode.getText().toString(),
                    date,
                    time,
                    price,
                    edtype.getText().toString()
            );

            db.addOrder(username, order, new Database.DatabaseCallback() {
                @Override
                public void onSuccess() {
                    db.clearCart(username, new Database.DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();
                            Intent orderIntent = new Intent(LabTestBookActivity.this, OrderDetailsActivity.class);
                            orderIntent.putExtra("orderDetail", order);
                            startActivity(orderIntent);
                            finish(); // Finish current activity after successful checkout
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getApplicationContext(), "Gagal menghapus keranjang", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), "Pemesanan gagal", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
