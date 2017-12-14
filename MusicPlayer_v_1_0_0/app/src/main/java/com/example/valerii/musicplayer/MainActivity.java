package com.example.valerii.musicplayer;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

//    private static final String KEY_COUNT = "COUNT";
//    private static final String KEY_COUNT2 = "COUNT2";

    public static int oneTimeOnly = 0;

    private Button play, pause, stop;
    private TextView endTime, counterTime, songName;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private SeekBar seekbarSong, seekbarValue;
    private Handler myHandler = new Handler();
    private Resources myResources;

    private double finalTime = 0;
    private double startTime = 0;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(MainActivity.this, "I'm Finished", Toast.LENGTH_SHORT).show();
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.overture_the_children_of_captain_grant);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        seekbarSong = (SeekBar)findViewById(R.id.seekbar_song);
        seekbarValue = (SeekBar)findViewById(R.id.seekbar_value);
        play = (Button) findViewById(R.id.button_play);
        pause = (Button) findViewById(R.id.button_pause);
        stop = (Button) findViewById(R.id.button_stop);
        endTime = (TextView) findViewById(R.id.text_time_end);
        counterTime = (TextView) findViewById(R.id.text_time_counter);
        songName = (TextView) findViewById(R.id.text_song_name);

        seekbarValue.setProgress(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        seekbarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVolume;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVol, boolean b) {
                progressVolume = progressVol;
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progressVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekedPprogess;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekedPprogess = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.overture_the_children_of_captain_grant);
                }
                mMediaPlayer.seekTo(seekedPprogess);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.overture_the_children_of_captain_grant);
                }
                int resultAudioManager = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (resultAudioManager == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer.start();
                }
                mMediaPlayer.setOnCompletionListener(mCompletionListener);

                myResources = getResources();

                songName.setText(myResources.getResourceEntryName(R.raw.overture_the_children_of_captain_grant));

                finalTime = mMediaPlayer.getDuration();
                startTime = mMediaPlayer.getCurrentPosition();

                endTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                counterTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                if (oneTimeOnly == 0) {
                    seekbarSong.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                seekbarSong.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);

                seekbarValue.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                seekbarValue.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mMediaPlayer.isPlaying()){
                        mMediaPlayer.pause();
                    }
                } catch (NullPointerException e) {}
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            try {
                startTime = mMediaPlayer.getCurrentPosition();
                counterTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
                );
            } catch (NullPointerException e) {

            }

            seekbarSong.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
//            Toast.makeText(MainActivity.this, "OnDestroy", Toast.LENGTH_SHORT).show();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putCharSequence(KEY_COUNT, endTime.getText());
//        outState.putCharSequence(KEY_COUNT2, counterTime.getText());
//    }
//
//
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        endTime.setText(savedInstanceState.getString(KEY_COUNT));
//        counterTime.setText(savedInstanceState.getString(KEY_COUNT2));
//    }
}
