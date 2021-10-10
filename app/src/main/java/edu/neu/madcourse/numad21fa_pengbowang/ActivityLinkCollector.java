package edu.neu.madcourse.numad21fa_pengbowang;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ActivityLinkCollector extends AppCompatActivity {
    private final ArrayList<ItemCard> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    private TextView urlName;
    private TextView urlLink;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkcollector);
        init(savedInstanceState);


        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            int pos = 0;
            openDialog(pos);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(findViewById(R.id.recycler_view), "Delete link", Snackbar.LENGTH_LONG).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openDialog(int pos) {
        Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog);
        myDialog.setCancelable(false);

        Button btnAdd = (Button) myDialog.findViewById(R.id.btn_Add);

        urlName = myDialog.findViewById(R.id.inputName);
        urlLink = myDialog.findViewById(R.id.inputUrl);

        myDialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlLink.getText().toString().trim();
                if (!url.isEmpty() && Patterns.WEB_URL.matcher(url).matches()) {
                    int pos = 0;
                    addItem(pos, urlName.getText().toString(), urlLink.getText().toString());
                    Snackbar.make(findViewById(R.id.fabAdd), "Add link", Snackbar.LENGTH_LONG).show();
                    myDialog.cancel();
                } else {
                    Toast toast = Toast.makeText(ActivityLinkCollector.this, "Link format is wrong!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });

        Button btnCancel = (Button) myDialog.findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
    }

    public void openURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Uri link = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        startActivity(intent);
    }

    private void addItem(int position, String urlName, String urlLink) {
        itemList.add(position, new ItemCard(urlName, urlLink, getApplicationContext()));
        Snackbar.make(findViewById(R.id.fabAdd), "Successfully add link", Snackbar.LENGTH_LONG).show();
        recyclerAdapter.notifyItemInserted(position);

    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        rLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {

            @Override
            public void onItemClick(int position) {
                String url = String.valueOf(itemList.get(position).getLinkURL());
                openURL(url);
                recyclerAdapter.notifyItemChanged(position);
            }
        };
        recyclerAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for (int i = 0; i < size; i++) {
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String itemDescription = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    ItemCard itemCard = new ItemCard(itemName, itemDescription, getApplicationContext());
                    itemList.add(itemCard);
                }
            }
        }
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", itemList.get(i).getLinkName());
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getLinkURL());
        }
        super.onSaveInstanceState(outState);
    }
}