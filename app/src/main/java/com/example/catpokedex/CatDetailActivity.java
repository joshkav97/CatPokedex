package com.example.catpokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.example.catpokedex.SearchRecyclerFragment.catResults;

public class CatDetailActivity extends AppCompatActivity {

    private Button addToFavourites;
    public TextView nameTextView;
    public TextView descriptionTextView;
    public TextView weightTextView;
    public TextView temperamentTextView;
    public TextView originTextView;
    public TextView life_spanTextView;
    public TextView wikipedia_urlTextView;
    public TextView dog_friendlyTextView;
    public ImageView imageView;
    public Cat catDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);

        addToFavourites = findViewById(R.id.addToFavourites);
        nameTextView = findViewById(R.id.name);
        descriptionTextView = findViewById(R.id.description);
        weightTextView = findViewById(R.id.weight);
        temperamentTextView = findViewById(R.id.temperament);
        originTextView = findViewById(R.id.origin);
        life_spanTextView = findViewById(R.id.life_span);
        wikipedia_urlTextView = findViewById(R.id.wikipedia_url);
        dog_friendlyTextView = findViewById(R.id.dog_friendly);
        imageView = findViewById(R.id.imageView);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        for (int i = 0; i < catResults.size(); i++) {
            String nameAtPosition = catResults.get(i).getName();
            if (nameAtPosition.equals(name))
                catDetail = catResults.get(i);
        }
        nameTextView.setText(catDetail.getName());
        descriptionTextView.setText((catDetail.getDescription()));
        weightTextView.setText(catDetail.getWeight() + getString(R.string.kg));
        temperamentTextView.setText(catDetail.getTemperament());
        originTextView.setText(catDetail.getOrigin());
        life_spanTextView.setText(catDetail.getLife_span() + getString(R.string.years));
        wikipedia_urlTextView.setText(catDetail.getWikipedia_url());
        dog_friendlyTextView.setText(catDetail.getDog_friendly());

        String url = "https://api.thecatapi.com/v1/images/search?breed_id=" + catDetail.getId();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Cat>>() {
                }.getType();
                List<Cat> catBreed = gson.fromJson(response, listType);
                String imageUrl = catBreed.get(0).getUrl();
                Glide.with(getApplicationContext()).load(catBreed.get(0).getUrl()).into(imageView);
                requestQueue.stop();

                addToFavourites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FavouriteCat.addFavouriteCat(catDetail);
                    }
                });
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(null, "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);

        wikipedia_urlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikipedia_urlTextView.getText().toString()));
                startActivity(intent);
            }
        });

    }
}
