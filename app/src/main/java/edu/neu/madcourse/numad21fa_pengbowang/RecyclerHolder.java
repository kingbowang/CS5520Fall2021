package edu.neu.madcourse.numad21fa_pengbowang;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemDescription;

    public RecyclerHolder(View itemView, final ItemClickListener listener) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name);
        itemDescription = itemView.findViewById(R.id.item_description);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });

    }

}
