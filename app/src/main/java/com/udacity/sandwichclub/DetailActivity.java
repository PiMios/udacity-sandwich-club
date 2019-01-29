package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        origin = (TextView) findViewById(R.id.tv_place_of_origin);
        description = (TextView) findViewById(R.id.tv_description);
        ingredients = (TextView) findViewById(R.id.tv_ingredients);
        alsoKnown = (TextView) findViewById(R.id.tv_also_known_as);

        origin.setText(sandwich.getMainName());
        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());

        settingList(sandwich.getIngredients(), ingredients);
        settingList(sandwich.getAlsoKnownAs(), alsoKnown);

    }

    /*I know the UI works with the method below, but need to understand why. This was taken from https://github.com/angelynaliem/SandwichApp*/

    private void settingList(List<String> list, TextView textView) {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            data.append(list.get(i));
            if (i != list.size() - 1)
                data.append(",");
        }

        textView.setText(data.toString().replace(",", "\n"));

    }
}
