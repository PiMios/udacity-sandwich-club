package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sw = null;

        String mainName;
        String placeOfOrigin;
        String description;
        String image;

        try {
            JSONObject sandwichMenu = new JSONObject(json);
            JSONObject name = sandwichMenu.getJSONObject("name");
            mainName = name.getString("mainName");

            JSONArray akaArray = name.getJSONArray("alsoKnownAs");
            ArrayList<String> listOfNames = new ArrayList<>();
            for (int i = 0; i < akaArray.length(); i++) {
                listOfNames.add(akaArray.getString(i));
            }

            placeOfOrigin = sandwichMenu.getString("placeOfOrigin");
            description = sandwichMenu.getString("description");
            image = sandwichMenu.getString("image");

            JSONArray ingredientsArray = sandwichMenu.getJSONArray("ingredients");
            ArrayList<String> listOfIngredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                listOfIngredients.add(ingredientsArray.getString(i));
            }

            return new Sandwich(mainName, listOfNames, placeOfOrigin, description, image, listOfIngredients);

        } catch (JSONException e) {
            e.printStackTrace();;
        }

        return sw;
    }
}
