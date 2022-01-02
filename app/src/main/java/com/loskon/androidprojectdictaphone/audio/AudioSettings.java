package com.loskon.androidprojectdictaphone.audio;

import android.media.AudioFormat;

/**
 * Общие звуковые настройки для AudioRecord и AudioTrack
 */

public class AudioSettings {
    public static final int SAMPLE_RATE = 8000 ; // 44100
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
}
