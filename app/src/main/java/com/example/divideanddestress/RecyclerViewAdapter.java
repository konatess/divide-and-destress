package com.example.divideanddestress;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mItemName = new ArrayList<>();
    private ArrayList<String> mItemDue = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> itemName, ArrayList<String> itemDue, Context context) {
        Log.d(TAG, "RecyclerViewAdapter: is called.");
        this.mItemName = itemName;
        this.mItemDue = itemDue;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, "onBindViewHolder: position value is: " + position);

        holder.itemName.setText(mItemName.get(position));
        holder.itemDue.setText(mItemDue.get(position));

        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mItemName.get(position));

                // Insert navigation here
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Item count = " + mItemName.size());
        return mItemName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemDue;
        RelativeLayout listItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDue = itemView.findViewById(R.id.itemDue);
            listItem = itemView.findViewById(R.id.listItem);
        }
    }
}
