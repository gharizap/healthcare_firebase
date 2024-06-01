package com.example.healthcare;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartBuyMedicineActivity extends AppCompatActivity {
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, btnCheckout, btnBack;
    private String[][] packages = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_medicine);

        dateButton = findViewById(R.id.buttonBMCartDate);
        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMCartBack);
        tvTotal = findViewById(R.id.textViewBMCartTotalCost);
        lst = findViewById(R.id.listViewBMCart);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");
        String encodedUsername = Utils.encodeUsername(username); // Encode the username

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts").child(encodedUsername);

        final float[] totalAmount = {0};
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem item = snapshot.getValue(CartItem.class);
                    if (item != null) {
                        cartItems.add(item);
                    }
                }

                packages = new String[cartItems.size()][5];
                for (int i = 0; i < cartItems.size(); i++) {
                    CartItem cartItem = cartItems.get(i);
                    packages[i][0] = cartItem.getProduct();
                    packages[i][4] = "Cost: " + cartItem.getPrice() + "/-";
                    totalAmount[0] += Float.parseFloat(cartItem.getPrice());
                }

                tvTotal.setText("Total Cost: " + totalAmount[0]);

                list = new ArrayList<>();
                for (int i = 0; i < packages.length; i++) {
                    item = new HashMap<>();
                    item.put("line1", packages[i][0]);
                    item.put("line5", packages[i][4]);
                    list.add(item);
                }

                sa = new SimpleAdapter(CartBuyMedicineActivity.this, list,
                        R.layout.multi_lines,
                        new String[]{"line1", "line5"},
                        new int[]{R.id.line_a, R.id.line_e});
                lst.setAdapter(sa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to load cart data: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnBack.setOnClickListener(view -> startActivity(new Intent(CartBuyMedicineActivity.this, BuyMedicineActivity.class)));

        btnCheckout.setOnClickListener(view -> {
            Intent it = new Intent(CartBuyMedicineActivity.this, BuyMedicineBookActivity.class);
            it.putExtra("price", tvTotal.getText().toString());
            it.putExtra("date", dateButton.getText().toString());
            startActivity(it);
        });

        initDatePicker();
        dateButton.setOnClickListener(view -> datePickerDialog.show());
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            dateButton.setText(day + "/" + month + "/" + year);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000); // Set minimum date to next day
    }
}
