package edu.neu.madcourse.numad21fa_pengbowang;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AtYourService extends AppCompatActivity {

    private final Handler textHandler = new Handler();
    private TextView searchTitle;
    private ScrollView movieDetailsView;
    private TextView loading;
    private TextView title;
    private TextView year;
    private TextView released;
    private TextView runtime;
    private TextView genre;
    private TextView director;
    private TextView writer;
    private TextView language;
    private TextView country;
    private TextView plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        released = findViewById(R.id.released);
        runtime = findViewById(R.id.runtime);
        genre = findViewById(R.id.genre);
        director = findViewById(R.id.director);
        writer = findViewById(R.id.writer);
        language = findViewById(R.id.language);
        country = findViewById(R.id.country);
        loading = findViewById(R.id.loading);
        searchTitle = findViewById(R.id.searchTitle);
        movieDetailsView = findViewById(R.id.scrollView);
        plot = findViewById(R.id.plot);
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        if (!title.getText().toString().isEmpty()) {
            savedInstanceState.putString("title", title.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("year", year.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("released", released.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("runtime", runtime.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("genre", genre.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("director", director.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("writer", writer.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("language", language.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("country", country.getText().toString().split(":", 2)[1]);
            savedInstanceState.putString("plot", plot.getText().toString().split(":", 2)[1]);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        if (savedInstanceState.get("title") != null) {
            title.setText(Html.fromHtml("<b>Title: </b>" + savedInstanceState.get("title").toString()));
            year.setText(Html.fromHtml("<b>Year: </b>" + savedInstanceState.get("year").toString()));
            released.setText(Html.fromHtml("<b>Released: </b>" + savedInstanceState.get("released").toString()));
            runtime.setText(Html.fromHtml("<b>Runtime: </b>" + savedInstanceState.get("runtime").toString()));
            genre.setText(Html.fromHtml("<b>Genre: </b>" + savedInstanceState.get("genre").toString()));
            director.setText(Html.fromHtml("<b>Director: </b>" + savedInstanceState.get("director").toString()));
            writer.setText(Html.fromHtml("<b>Writer: </b>" + savedInstanceState.get("writer").toString()));
            language.setText(Html.fromHtml("<b>Language: </b>" + savedInstanceState.get("language").toString()));
            country.setText(Html.fromHtml("<b>Country: </b>" + savedInstanceState.get("country").toString()));
            plot.setText(Html.fromHtml("<b>Plot: </b>" + savedInstanceState.get("plot").toString()));
            movieDetailsView.setVisibility(View.VISIBLE);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onMovieSearchClick(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        loading.setText("Loading...");
        loading.setVisibility(View.INVISIBLE);
        movieDetailsView.setVisibility(View.INVISIBLE);
        if (searchTitle.getText().toString().isEmpty()) {
            resetData();
            Snackbar snack = Snackbar.make(view, "Please enter the title of the movie!", Snackbar.LENGTH_LONG).setAction("Action", null);
            View snackView = snack.getView();
            TextView mTextView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snack.show();
            return;
        }
        loading.setVisibility(View.VISIBLE);
        callWebService();
    }

    private void callWebService() {
        WebServiceThread webServiceThread = new WebServiceThread();
        new Thread(webServiceThread).start();
    }

    private void resetData() {
        title.setText("");
        year.setText("");
        released.setText("");
        runtime.setText("");
        genre.setText("");
        director.setText("");
        writer.setText("");
        language.setText("");
        country.setText("");
        plot.setText("");
    }

    class WebServiceThread implements Runnable {
        @Override
        public void run() {
            try {
                URL url = new URL("http://www.omdbapi.com/?apikey=e661839a&t=" + searchTitle.getText());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                String response = convertStreamToString(inputStream);
                JSONObject movieDetails = new JSONObject(response);
                System.out.println(movieDetails);
                textHandler.post(() -> {
                    try {
                        if (movieDetails.has("Error")) {
                            resetData();
                            loading.setText("Movie not found!");
                        } else {
                            title.setText(Html.fromHtml("<b>Title: </b>" + movieDetails.get("Title").toString()));
                            year.setText(Html.fromHtml("<b>Year: </b>" + movieDetails.get("Year").toString()));
                            released.setText(Html.fromHtml("<b>Released: </b>" + movieDetails.get("Released").toString()));
                            runtime.setText(Html.fromHtml("<b>Runtime: </b>" + movieDetails.get("Runtime").toString()));
                            genre.setText(Html.fromHtml("<b>Genre: </b>" + movieDetails.get("Genre").toString().replace('\n', ' ')));
                            director.setText(Html.fromHtml("<b>Director: </b>" + movieDetails.get("Director").toString()));
                            writer.setText(Html.fromHtml("<b>Writer: </b>" + movieDetails.get("Writer").toString()));
                            language.setText(Html.fromHtml("<b>Language: </b>" + movieDetails.get("Language").toString()));
                            country.setText(Html.fromHtml("<b>Country: </b>" + movieDetails.get("Country").toString()));
                            plot.setText(Html.fromHtml("<b>Plot: </b>" + movieDetails.get("Plot").toString()));
                            loading.setVisibility(View.INVISIBLE);
                            movieDetailsView.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        private String convertStreamToString(InputStream inputStream) {
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next().replace(",", ",\n") : "";
        }
    }
}
