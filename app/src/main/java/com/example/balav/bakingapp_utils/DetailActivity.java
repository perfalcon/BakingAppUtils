package com.example.balav.bakingapp_utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balav.bakingapp_utils.model.Baking;
import com.example.balav.bakingapp_utils.model.Ingredient;

import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String RECIPE_KEY= "recipe";
    public static final String BUNDLE_KEY="bundle";
    public static final String BAKING_KEY="baking";
    public static final String BAKING_ID="baking_id";
    private  Baking baking;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState (outState);
        Log.v (TAG, "Saving the State");
        outState.putParcelable (BAKING_KEY,baking);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe_steps);

        if(savedInstanceState != null) {
            Log.v (TAG, "Restoring State");
            if (savedInstanceState.containsKey (BAKING_KEY)) {
                baking = savedInstanceState.getParcelable (BAKING_KEY);
            }
        }else{
            Intent intent = getIntent ();
            if (intent == null) {
                closeOnError ();
            }

            baking = intent.getParcelableExtra (BAKING_KEY);
            Log.v(TAG,"baking--->"+baking);
            Log.v (TAG,"Recipe Name -->"+baking.getName ());
            int baking_id = intent.getIntExtra (BAKING_ID,0);
            Log.v (TAG, "RECIPE CLICKED-->" + baking_id);
        }

        populateUI(baking);
        // Create a new recipeFragment
        RecipeFragment recipeFragment = new RecipeFragment (baking);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_container, recipeFragment)
                .commit();


    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    private void populateUI(Baking mBaking){
        TextView mRecipeName = (TextView) findViewById(R.id.tv_recipeName);
        mRecipeName.setText(mBaking.getName ());
        /*TextView mIngredients = (TextView) findViewById(R.id.tv_ingredients);

        mIngredients.setText(prettyIngredients (mBaking.getIngredients ()));*/

    }
    private String prettyIngredients(List<Ingredient> ingredientList){
        ListIterator it =ingredientList.listIterator ();
        StringBuilder sb = new StringBuilder () ;

        while(it.hasNext ()){
            Ingredient ingredient =(Ingredient) it.next ();
            Log.v(TAG,"ingredients-->"+ingredient.getIngredient ()+"|"+ingredient.getQuantity ()+ingredient.getMeasure ());
            sb.append (ingredient.getIngredient ()+"|"+ingredient.getQuantity ()+ingredient.getMeasure ());
        }
        return sb.toString ();
    }

    @Override
    public void onStepSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
    }
}
