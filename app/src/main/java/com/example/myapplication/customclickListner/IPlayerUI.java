package com.example.myapplication.customclickListner;
import android.app.Activity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
public interface IPlayerUI extends ExoPlayer.EventListener, SimpleExoPlayer.VideoListener {
        Activity getContext();

        void showToast(int errorStringId);

        void updateButtonVisibilities();

        void showToast(String myString);

        void onCreatePlayer();
        }