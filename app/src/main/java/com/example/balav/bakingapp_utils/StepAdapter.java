package com.example.balav.bakingapp_utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.balav.bakingapp_utils.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {
    private static final String TAG = StepAdapter.class.getSimpleName();
    private int mNumberItems;
    private Context mContext;
    private List<Step> mStep;



    public  StepAdapter(List<Step> stepList){
        Log.v (TAG,"Size of Steps -->"+stepList.size ());
        mNumberItems = stepList.size ();
        mStep =stepList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        int layoutIdForListItem = R.layout.step_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        StepAdapter.StepViewHolder viewHolder = new StepAdapter.StepViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        ((StepAdapter.StepViewHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {        return mNumberItems;    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepText;
        public StepViewHolder(View itemView) {
            super (itemView);
            stepText = (TextView)itemView.findViewById (R.id.tv_step);
            stepText.setOnClickListener (StepAdapter.StepViewHolder.this);
        }
        void bind(int listIndex) {
            Log.v (TAG, "Step-->"+mStep.get (listIndex));
            Log.v (TAG, "Step Desc-->"+mStep.get (listIndex).getShortDescription ());
            stepText.setText (mStep.get (listIndex).getShortDescription ());
        }


        @Override
        public void onClick(View v) {
            Log.v ("onClick-->","----|"+getAdapterPosition ()+"|");
            launchDetailActivity (getAdapterPosition ());
        }
        private void launchDetailActivity(int id) {
           /* Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra (DetailActivity.BAKING_ID,id);

            intent.putExtra(DetailActivity.BAKING_KEY,mBaking.get(id));*/
            Log.v (TAG,"Recipe sending Object.."+mStep.get (id));
            Log.v (TAG,"Recipe sending ..."+mStep.get (id).getDescription ());
            //mContext.startActivity(intent);
        }
    }
}
