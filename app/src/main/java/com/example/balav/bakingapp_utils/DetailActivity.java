package com.example.balav.bakingapp_utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.balav.bakingapp_utils.model.Baking;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String RECIPE_KEY= "recipe";
    public static final String BUNDLE_KEY="bundle";
    public static final String BAKING_KEY="baking";
    public static final String BAKING_ID="baking_id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.recipe_detail);
        Intent intent = getIntent ();
        if (intent == null) {
            closeOnError ();
        }
      /*  Bundle BundleBaking = intent.getBundleExtra (BUNDLE_KEY);
        Baking baking = BundleBaking.getParcelable (BAKING_KEY);
        if(baking == null){
            closeOnError ();
        }
        Log.v (TAG,"Recipe Name -->"+baking.getName ());*/
        int baking_id = intent.getIntExtra (BAKING_ID,0);


        Log.v (TAG, "RECIPE CLICKED-->" + baking_id);
        //bFav = intent.getStringExtra (OPTION_SELECTED).equals ("favorites");

    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
