package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Declare TextView variables

    private TextView origin;
    private TextView description;
    private TextView ingredients;
    private TextView alsoKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Load images

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Populate TextView boxes

        origin = (TextView) findViewById(R.id.tv_place_of_origin);
        description = (TextView) findViewById(R.id.tv_description);
        ingredients = (TextView) findViewById(R.id.tv_ingredients);
        alsoKnown = (TextView) findViewById(R.id.tv_also_known_as);
        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());

        // Pull data from JSON arrays to populate TextView

        getList(sandwich.getIngredients(), ingredients);
        getList(sandwich.getAlsoKnownAs(), alsoKnown);

    }

    /* Method below sampled from https://github.com/angelynaliem/SandwichApp to sort
    thru ingredients and AKA */

    private void getList(List<String> list, TextView textView) {
        StringBuilder listInfo = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listInfo.append(list.get(i));
            if (i != list.size() - 1)
                listInfo.append("\n");
        }

        // Convert object data to string

        textView.setText(listInfo.toString());

    }


}
