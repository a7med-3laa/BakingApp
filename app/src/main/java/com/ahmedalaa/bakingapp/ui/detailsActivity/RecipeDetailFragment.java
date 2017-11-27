package com.ahmedalaa.bakingapp.ui.detailsActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.RecipeStep;
import com.ahmedalaa.bakingapp.model.RecipeStepWrapper;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    BandwidthMeter bandwidthMeter;
    TrackSelector trackSelector;
    MediaSource videoSource;
    ExtractorsFactory extractorsFactory;
    SimpleExoPlayer player;
    TrackSelection.Factory videoTrackSelectionFactory;
    int pos;
    DataSource.Factory dataSourceFactory;

    @BindView(R.id.videoView)
    SimpleExoPlayerView exoPlayerView;
    @Nullable
    @BindView(R.id.recipe_step_detail)
    TextView recipeStepDetail;
    RecipeDetailActivity recipeDetailActivity;
    private RecipeStep mItem;
    private long time = 0;
    private int win = 0;
    private boolean isPlaying = true;

    public RecipeDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailActivity)
            recipeDetailActivity = (RecipeDetailActivity) context;
    }

    @Optional
    @OnClick(R.id.previous_btn)
    public void previousClick(View view) {
        if (pos != 0) {
            pos--;
            mItem = RecipeStepWrapper.getStep(pos);
            bindData();
        }


    }

    @Optional
    @OnClick(R.id.next_btn)
    public void nextClick(View view) {
        if (pos != RecipeStepWrapper.steps.size() - 1) {
            pos++;
            mItem = RecipeStepWrapper.getStep(pos);
            bindData();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.

                pos = getArguments().getInt(ARG_ITEM_ID);
            }
        }
        if (savedInstanceState != null) {
            time = savedInstanceState.getLong("time", 0);
            win = savedInstanceState.getInt("win");
            pos = savedInstanceState.getInt("pos");
            isPlaying = savedInstanceState.getBoolean("isPlaying");
        }
        mItem = RecipeStepWrapper.getStep(pos);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        //  setRetainInstance(true);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);


        // Measures bandwidth during playback. Can be null if not required.
        dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Baking app"));
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        // Produces Extractor instances for parsing the media data.
        extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        exoPlayerView.setPlayer(player);

        bindData();
    }

    private void bindData() {
        videoSource = new ExtractorMediaSource(Uri.parse(mItem.videoURL),
                dataSourceFactory, extractorsFactory, null, new ExtractorMediaSource.EventListener() {
            @Override
            public void onLoadError(IOException error) {

            }

        });
        player.setPlayWhenReady(isPlaying);
        player.seekTo(win, time);

        // Prepare the player with the source.
        player.prepare(videoSource, true, false);
        if (recipeStepDetail != null) {
            recipeStepDetail.setText(mItem.description);
        } else {
            if (recipeDetailActivity != null)
                recipeDetailActivity.hideToolBar();
        }
        player.seekTo(win, time);


    }

    private void releasePlayer() {
        if (player != null) {
            time = player.getCurrentPosition();
            win = player.getCurrentWindowIndex();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("time", player.getCurrentPosition());
        outState.putInt("win", player.getCurrentWindowIndex());
        outState.putInt("pos", pos);
        outState.putBoolean("isPlaying", player.getPlayWhenReady());

        super.onSaveInstanceState(outState);

    }
}
