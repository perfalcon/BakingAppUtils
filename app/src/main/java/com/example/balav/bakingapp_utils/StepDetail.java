package com.example.balav.bakingapp_utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.balav.bakingapp_utils.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class StepDetail extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = StepDetail.class.getSimpleName ();
    public static final String STEP_KEY="step";
    public static final String STEP_ID="step_id";
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private int[] mButtonIDs = {R.id.btn_prev_step, R.id.btn_next_step};
    private Button[] mButtons;
    private  int current_position;
    private  List <Step> listSteps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.v(TAG,"in Step Detail");
        setContentView (R.layout.step_detail);
        Intent intent = getIntent ();
        if (intent == null) {
            //closeOnError ();
        }

        int step_id = intent.getIntExtra (STEP_ID,0);
        Log.v (TAG, "Step CLICKED-->" + step_id);
        current_position =step_id;
        listSteps = intent.getParcelableArrayListExtra (STEP_KEY);
        Log.v(TAG,"step--->"+listSteps.get (step_id));
        Log.v (TAG,"Step Name -->"+listSteps.get (step_id).getShortDescription ());
        populateUI(listSteps.get (step_id));
    }

    private void populateUI(Step step) {
        Log.v(TAG,"Calling Populate UI -->"+step.toString ());
        TextView mRecipeName = (TextView) findViewById(R.id.tv_step_description);
        mRecipeName.setText(step.getDescription ());
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
        Log.v (TAG,"The Video-->"+step.getVideoURL ());
        // Initialize the player.
        if(step.getVideoURL ()!=null && !step.getVideoURL ().isEmpty ()){
            Log.v (TAG,"Updating the Video-->"+step.getVideoURL ());
            initializePlayer(Uri.parse(step.getVideoURL ()));
        }
        // Initialize the buttons with the composers names.
        mButtons = initializeButtons();

    }
    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(this, "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource (mediaUri, new DefaultDataSourceFactory (
                this, userAgent), new DefaultExtractorsFactory (), null, null);
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector ();
            LoadControl loadControl = new DefaultLoadControl ();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }else{
            //Setting env to play the next passed video
            mExoPlayer.stop();
            mExoPlayer.seekTo(0L);
            }
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Initializes the button to the correct views, and sets the text to the composers names,
     * and set's the OnClick listener to the buttons.
     *
     *
     * @return The Array of initialized buttons.
     */
    private Button[] initializeButtons() {
        Button[] buttons = new Button[mButtonIDs.length];
        for (int i = 0; i < buttons.length; i++) {
            Button currentButton = (Button) findViewById(mButtonIDs[i]);
            buttons[i] = currentButton;
            currentButton.setOnClickListener (this);
        }
        return buttons;
    }

    @Override
    public void onClick(View v) {
        // Get the button that was pressed.
        Button pressedButton = (Button) v;
       Log.v(TAG, "Pressed Button-->"+pressedButton.toString ());
       Log.v(TAG,"Button ID -->"+pressedButton.getId ());
       Log.v(TAG,"Current Postion-->"+current_position);
       if(pressedButton.getId() == mButtonIDs[0]){ // prev
           if(current_position<=0){
              current_position=0;
           }else{
               current_position--;
           }
        }else if(pressedButton.getId () == mButtonIDs[1]){//next
           if(current_position>=listSteps.size ()-1){
               current_position=listSteps.size ()-1;
           }else{
               current_position++;
           }
       }
        Log.v(TAG,"Updaed Current Postion-->"+current_position);
       // mExoPlayer=null;
        populateUI(listSteps.get (current_position));
    }
}
