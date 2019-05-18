package com.example.divideanddestress;

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

    private ArrayList<String> mItemName;
    private ArrayList<String> mItemDue;
    private OnAssignmentListener mOnAssignmentListener;

    RecyclerViewAdapter(ArrayList<String> itemName, ArrayList<String> itemDue, OnAssignmentListener onAssignmentListener) {
        Log.d(TAG, "RecyclerViewAdapter: is called.");
        this.mItemName = itemName;
        this.mItemDue = itemDue;
        this.mOnAssignmentListener = onAssignmentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mOnAssignmentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, "onBindViewHolder: position value is: " + position);

        holder.itemName.setText(mItemName.get(position));
        holder.itemDue.setText(mItemDue.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Item count = " + mItemName.size());
        return mItemName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemName;
        TextView itemDue;
        RelativeLayout listItem;
        OnAssignmentListener onAssignmentListener;

        ViewHolder(@NonNull View itemView, OnAssignmentListener onAssignmentListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDue = itemView.findViewById(R.id.itemDue);
            listItem = itemView.findViewById(R.id.listItem);
            this.onAssignmentListener = onAssignmentListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssignmentListener.OnAssignmentClick(getAdapterPosition());
        }
    }

    public interface OnAssignmentListener {
        void OnAssignmentClick(int position);
    }
}
