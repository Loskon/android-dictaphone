package com.loskon.androidprojectdictaphone.audio.recorder;

import android.annotation.SuppressLint;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.loskon.androidprojectdictaphone.audio.AudioSettings;

/**
 * Контроль записи звука
 */

public class SoundRecorderControl extends AudioSettings {

    private AudioRecord recorder;
    private SoundRecorderThread thread;

    public SoundRecorderControl() {
        initAudioRecorder();
        initRecordingThread();
    }

    @SuppressLint("MissingPermission")
    private void initAudioRecorder() {
        int audioSource = MediaRecorder.AudioSource.MIC;
        int recordBufSize = minBufSize * 10;

        recorder = new AudioRecord(audioSource, sampleRate, channel, format, recordBufSize);
    }

    private void initRecordingThread() {
        thread = new SoundRecorderThread(recorder);
    }

    public void startRecording() {
        thread.startRecording();
    }

    public void stopRecording() {
        thread.stopRecording();
    }
}
