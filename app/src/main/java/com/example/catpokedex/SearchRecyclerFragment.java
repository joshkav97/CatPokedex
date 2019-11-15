package com.example.catpokedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SearchRecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView searchBox;
    private Button searchButton;
    ArrayList<Cat> searchResults = new ArrayList<>();
    public static ArrayList<Cat> catResults;
    final CatAdapter catAdapter = new CatAdapter();
    public SearchRecyclerFragment() {
    }

    public void performSearch() {
        searchResults.clear();
        String searchText = searchBox.getText().toString();
        for (Cat cat : catResults) {
            if (cat.getName().toLowerCase().matches(searchText.toLowerCase().concat(".*"))) {
                searchResults.add(cat);
                catAdapter.setData(searchResults);
                recyclerView.setAdapter(catAdapter);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        searchBox = view.findViewById(R.id.searchBox);
        searchButton = view.findViewById(R.id.searchButton);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://api.thecatapi.com/v1/breeds?api_key=" + getString(R.string.api_key);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Cat>>() {
                }.getType();
                catResults = gson.fromJson(response, listType);

                catAdapter.setData(catResults);
                recyclerView.setAdapter(catAdapter);
                requestQueue.stop();

                searchBox.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            performSearch();
                            return true;
                        }
                        return false;
                    }
                });

                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performSearch();
                    }
                });

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);
        return view;
    }
}
