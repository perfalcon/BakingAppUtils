package com.example.balav.bakingapp_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.balav.bakingapp_utils.model.Baking;
import com.example.balav.bakingapp_utils.model.Ingredient;
import com.example.balav.bakingapp_utils.model.Step;

import java.util.List;
import java.util.Random;

public class RecipeFragment extends Fragment {
    private static final String TAG = RecipeFragment.class.getSimpleName();
    private List<Step> mSteps;
    private Baking mBaking;
    public RecipeFragment(){

    }
@SuppressLint("ValidFragment")
public RecipeFragment(Baking baking){
        mBaking = baking;
}
OnStepClickListener onStepClickListener;
    public interface OnStepClickListener{
    void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        try {
            onStepClickListener = (OnStepClickListener) context;
        }catch (ClassCastException e){
            throw  new ClassCastException (context.toString ()
                    +"must  implement OnStepClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        final TextView step = (TextView)rootView.findViewById (R.id.tv_fg_step);
        step.setText(mBaking.getIngredients ().get (0).getIngredient ());
        step.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Random x = new Random ();
                step.setText (mBaking.getIngredients ().get(x.nextInt (4)).getIngredient ());
            }
        });

        final RecyclerView rvStep = (RecyclerView)rootView.findViewById (R.id.rv_step);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (getContext ());
        rvStep.setLayoutManager (linearLayoutManager);
        rvStep.setHasFixedSize (true);
        final StepAdapter stepAdapter = new StepAdapter (mBaking.getSteps ());
        rvStep.setAdapter (stepAdapter);
        rvStep.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"helloooo ");


            }
        });
        return rootView;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState (outState);
        // to code later
    }



    //Step Adapter



}
