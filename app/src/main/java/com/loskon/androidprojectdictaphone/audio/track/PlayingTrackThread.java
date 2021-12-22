package com.loskon.androidprojectdictaphone.audio.track;

import android.media.AudioTrack;
import android.os.Process;

import com.loskon.androidprojectdictaphone.ulaw.DecodingInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Воспроизведение записи в потоке
 */

public class PlayingTrackThread extends Thread {

    private final AudioTrack track;
    private File file;
    private boolean hasPlayingTrack = false;

    public PlayingTrackThread(AudioTrack track) {
        this.track = track;
    }

    @Override
    public void run() {
        performRecording();
    }

    private void performRecording() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
            byte[] buffer = new byte[4096];
            int length;

            track.play();

            InputStream inputStream = new FileInputStream(file);
            InputStream decodingInputStream = new DecodingInputStream(inputStream);

            while (((length = decodingInputStream.read(buffer)) != -1) && hasPlayingTrack) {
                track.write(buffer, 0, length);
            }

            inputStream.close();
            decodingInputStream.close();
            if (finishedPlaying != null && hasPlayingTrack) finishedPlaying.onFinishedPlaying();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (failedPlaying != null) failedPlaying.onFailedPlaying();
        }
    }

    public void startRecording(File file) {
        this.file = file;
        hasPlayingTrack = true;
        start();
    }

    public void stopRecording() {
        hasPlayingTrack = false;
        track.stop();
        interrupt();
    }

    private static CallbackFinishedPlaying finishedPlaying;
    private static CallbackFailedPlaying failedPlaying;

    public static void listenerCallback(CallbackFinishedPlaying finishedPlaying) {
        PlayingTrackThread.finishedPlaying = finishedPlaying;
    }

    public static void listenerCallback(CallbackFailedPlaying playingSuccess) {
        PlayingTrackThread.failedPlaying = playingSuccess;
    }
}
