package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        String placeOfOrigin;
        String description;
        String image;

        try {
            JSONObject sammichDetails = new JSONObject(json);
            JSONObject name = sammichDetails.getJSONObject("name");
            mainName = name.getString("manName");

            JSONArray alsoKnwonAsArray = name.getJSONArray("alsoKnownAs");
            ArrayList<String> sammichNames = new ArrayList<>();
            for (int i = 0; i < alsoKnwonAsArray.length(); i++) {
                sammichNames.add(alsoKnwonAsArray.getString(i));
            }

            placeOfOrigin = sammichDetails.getString("placeOfOrigin");
            description = sammichDetails.getString("description");

            image = sammichDetails.getString("image");

            JSONArray ingredientsArray = sammichDetails.getJSONArray("ingredients");
            ArrayList<String> sammichIngredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                sammichIngredients.add(ingredientsArray.getString(i));
            }

            return new Sandwich(mainName, sammichNames, placeOfOrigin, description, image, sammichIngredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
