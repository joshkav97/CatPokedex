package com.example.catpokedex;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FavouriteCat {
    public static int favId = 1;

    public static ArrayList<Cat> getAllFavouriteCats() {
        return new ArrayList<Cat>((List) Arrays.asList(favouriteCats.values().toArray()));
    }

    public static Integer getSize() {
        return favouriteCats.size();
    }

    public static final HashMap<Integer, Cat> favouriteCats = new HashMap<>();

    public static void addFavouriteCat(Cat cat) {
        favouriteCats.put(favId, cat);
        favId += 1;
    }
}