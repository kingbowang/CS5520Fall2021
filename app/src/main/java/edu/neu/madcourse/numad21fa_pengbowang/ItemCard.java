package edu.neu.madcourse.numad21fa_pengbowang;

import android.content.Context;

public class ItemCard implements ItemClickListener {
    private final String linkName;
    private final String linkURL;

    public ItemCard(String linkName, String linkURL, Context context) {
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    @Override
    public void onItemClick(int position) {
    }
}
