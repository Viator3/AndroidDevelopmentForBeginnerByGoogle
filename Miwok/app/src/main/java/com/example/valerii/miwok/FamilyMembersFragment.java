package com.example.valerii.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyMembersFragment extends Fragment {


    public FamilyMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getString(R.string.family_father), getString(R.string.ru_family_father), R.drawable.family_father, R.raw.family_father));
        words.add(new Word(getString(R.string.family_mother), getString(R.string.ru_family_mother), R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(getString(R.string.family_son), getString(R.string.ru_family_son), R.drawable.family_son, R.raw.family_son));
        words.add(new Word(getString(R.string.family_daughter), getString(R.string.ru_family_daughter), R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(getString(R.string.family_older_brother), getString(R.string.ru_family_older_brother), R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word(getString(R.string.family_younger_brother), getString(R.string.ru_family_younger_brother), R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(getString(R.string.family_older_sister), getString(R.string.ru_family_older_sister), R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(getString(R.string.family_younger_sister), getString(R.string.ru_family_younger_sister), R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(getString(R.string.family_grandmother), getString(R.string.ru_family_grandmother), R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(getString(R.string.family_grandfather), getString(R.string.ru_family_grandfather), R.drawable.family_grandfather, R.raw.family_grandfather));


        WordAdapter adapter =
                new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus
                        (mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
