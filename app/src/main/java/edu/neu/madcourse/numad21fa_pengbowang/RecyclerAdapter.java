package edu.neu.madcourse.numad21fa_pengbowang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private final ArrayList<ItemCard> itemList;
    private ItemClickListener listener;

    public RecyclerAdapter(ArrayList<ItemCard> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new RecyclerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);

        holder.itemName.setText((CharSequence) currentItem.getLinkName());
        holder.itemDescription.setText((CharSequence) currentItem.getLinkURL());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}