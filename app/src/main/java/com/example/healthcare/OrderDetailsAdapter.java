package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private final ArrayList<OrderDetail> orderList;

    public OrderDetailsAdapter(ArrayList<OrderDetail> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail orderDetail = orderList.get(position);
        holder.nameTextView.setText(orderDetail.getName());
        holder.contactTextView.setText(orderDetail.getContact());
        holder.addressTextView.setText(orderDetail.getAddress());
        holder.dateTextView.setText(orderDetail.getDate());
        holder.timeTextView.setText(orderDetail.getTime());
        holder.pincodeTextView.setText(orderDetail.getPincode());
        holder.priceTextView.setText(String.valueOf(orderDetail.getPrice()));
        holder.typeTextView.setText(orderDetail.getType());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, contactTextView, addressTextView, dateTextView, timeTextView, pincodeTextView, priceTextView, typeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewOrderName);
            contactTextView = itemView.findViewById(R.id.textViewOrderContact);
            addressTextView = itemView.findViewById(R.id.textViewOrderAddress);
            dateTextView = itemView.findViewById(R.id.textViewOrderDate);
            timeTextView = itemView.findViewById(R.id.textViewOrderTime);
            pincodeTextView = itemView.findViewById(R.id.textViewOrderPincode);
            priceTextView = itemView.findViewById(R.id.textViewOrderPrice);
            typeTextView = itemView.findViewById(R.id.textViewOrderType);
        }
    }
}
