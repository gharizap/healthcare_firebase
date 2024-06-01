package com.example.healthcare;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOD;
    private TextView totalCostTextView;
    private OrderDetailsAdapter adapter;
    private ArrayList<OrderDetail> orderList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Initialize UI components
        recyclerViewOD = findViewById(R.id.recyclerViewOD);
        totalCostTextView = findViewById(R.id.textViewTotalCost);

        // Set up RecyclerView
        recyclerViewOD.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase reference
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders").child("jefta1708");

        // Initialize order list
        orderList = new ArrayList<>();

        // Fetch orders from Firebase
        fetchOrdersFromFirebase();

        // Retrieve the OrderDetail object from the intent
        OrderDetail orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        if (orderDetail != null) {
            orderList.add(orderDetail);
            displayOrderDetails(orderDetail.getPrice());
        } else {
            Log.d("OrderDetailsActivity", "No order detail found in intent");
        }
    }

    private void fetchOrdersFromFirebase() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                float totalCost = 0;
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    if (orderSnapshot.exists()) {
                        try {
                            String name = orderSnapshot.child("name").getValue(String.class);
                            String contact = orderSnapshot.child("contact").getValue(String.class);
                            String address = orderSnapshot.child("address").getValue(String.class);
                            String date = orderSnapshot.child("date").getValue(String.class);
                            String time = orderSnapshot.child("time").getValue(String.class);
                            String pincode = orderSnapshot.child("pincode").getValue(String.class);
                            Float price = orderSnapshot.child("price").getValue(Float.class);
                            String type = orderSnapshot.child("type").getValue(String.class); // Get the type field

                            if (name != null && contact != null && address != null &&
                                    date != null && time != null && pincode != null && price != null && type != null) {
                                OrderDetail orderDetail = new OrderDetail(name, address, contact, pincode, date, time, price, type);
                                orderList.add(orderDetail);
                                totalCost += price;

                                // Log each order item details
                                Log.d("OrderDetailsActivity", "Fetched order item: " + orderDetail);
                            } else {
                                Log.d("OrderDetailsActivity", "Order item has null fields, skipping: " + orderSnapshot.getKey());
                            }
                        } catch (Exception e) {
                            Log.e("OrderDetailsActivity", "Error fetching order data: " + e.getMessage());
                        }
                    } else {
                        Log.d("OrderDetailsActivity", "No order data found in snapshot");
                    }
                }
                displayOrderDetails(totalCost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderDetailsActivity.this, "Failed to load orders: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OrderDetailsActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void displayOrderDetails(float totalCost) {
        if (orderList != null && !orderList.isEmpty()) {
            Log.d("OrderDetailsActivity", "Displaying order details with size: " + orderList.size());
            adapter = new OrderDetailsAdapter(orderList);
            recyclerViewOD.setAdapter(adapter);
            totalCostTextView.setText("Total Cost: " + totalCost);
        } else {
            Log.d("OrderDetailsActivity", "Order list is empty");
        }
    }
}
