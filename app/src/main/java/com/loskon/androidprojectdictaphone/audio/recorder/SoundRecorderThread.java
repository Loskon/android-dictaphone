package com.loskon.androidprojectdictaphone.audio.recorder;

import android.media.AudioRecord;
import android.os.Process;

import com.loskon.androidprojectdictaphone.files.FileManager;
import com.loskon.androidprojectdictaphone.ulaw.EncodingOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Запись звука в потоке
 */

public class SoundRecorderThread extends Thread {

    private final AudioRecord recorder;

    private final Date dateStartRecording = new Date();
    private boolean hasRecordingAudio = false;

    public SoundRecorderThread(AudioRecord recorder) {
        this.recorder = recorder;
    }

    @Override
    public void run() {
        performRecording();
    }

    private void performRecording() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
            byte[] buffer = new byte[4096];
            byte[] outBuffer = new byte[4096];

            recorder.startRecording();

            File file = FileManager.createAudioFile(dateStartRecording);
            FileOutputStream outputStream = new FileOutputStream(file);
            EncodingOutputStream encodingStream = new EncodingOutputStream(outputStream);

            int length;

            while (hasRecordingAudio) {
                length = recorder.read(buffer, 0, buffer.length);
                encodingStream.write(buffer, length, outBuffer);
            }

            outputStream.close();
            encodingStream.close();

            if (callback != null && !hasRecordingAudio) callback.onFinishedRecording(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            if (callback != null) callback.onFinishedRecording(false);
        }
    }

    public void startRecording() {
        hasRecordingAudio = true;
        start();
    }

    public void stopRecording() {
        hasRecordingAudio = false;
        recorder.stop();
        interrupt();
    }

    private static CallbackRecordingSuccess callback;

    public static void listenerCallback(CallbackRecordingSuccess callback) {
        SoundRecorderThread.callback = callback;
    }
}
