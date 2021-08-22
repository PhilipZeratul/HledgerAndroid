package com.phillip.hledgerandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    public static class Data {
        public String date;
        public String description;
        public String account;
        public String number;
        public String account2;
        public String number2;
    }
    private Data[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView description;
        private final TextView account;
        private final TextView number;
        private final TextView account2;
        private final TextView number2;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            date = (TextView) view.findViewById(R.id.recycler_textView_date);
            description = (TextView) view.findViewById(R.id.recycler_textView_description);
            account = (TextView) view.findViewById(R.id.recycler_textView_account);
            number = (TextView) view.findViewById(R.id.recycler_textView_number);
            account2 = (TextView) view.findViewById(R.id.recycler_textView_account2);
            number2 = (TextView) view.findViewById(R.id.recycler_textView_number2);
        }

        public TextView getDate() {
            return date;
        }
        public TextView getDescription() {
            return description;
        }
        public TextView getAccount() {
            return account;
        }
        public TextView getNumber() {
            return number;
        }
        public TextView getAccount2() {
            return account2;
        }
        public TextView getNumber2() {
            return number2;
        }
    }

    public CustomRecyclerAdapter(Data[] dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Data data = localDataSet[position];
        holder.getDate().setText(data.date);
        holder.getDescription().setText(data.description);
        holder.getAccount().setText(data.account);
        holder.getNumber().setText(data.number);
        holder.getAccount2().setText(data.account2);
        holder.getNumber2().setText(data.number);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
