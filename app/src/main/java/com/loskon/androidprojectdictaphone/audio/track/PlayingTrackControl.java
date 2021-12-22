package com.loskon.androidprojectdictaphone.audio.track;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;

import com.loskon.androidprojectdictaphone.audio.AudioSettings;

import java.io.File;

/**
 * Контроль воспроизведения записи
 */

public class PlayingTrackControl extends AudioSettings {

    private AudioTrack track;
    private PlayingTrackThread thread;

    public PlayingTrackControl() {
        initAudioTrack();
    }

    private void initAudioTrack() {
        int streamType = AudioManager.STREAM_MUSIC;
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channel, format);
        int recordBufSize = minBufSize * 10;
        int mode = AudioTrack.MODE_STREAM;

        track = new AudioTrack(streamType, sampleRate, channelConfig, format, recordBufSize, mode);
    }


    public void startRecording(File file) {
        stopRecording();
        initTrackingThread();
        thread.startRecording(file);
    }

    private void initTrackingThread() {
        thread = new PlayingTrackThread(track);
    }

    public void stopRecording() {
        if (thread != null) thread.stopRecording();
    }
}
