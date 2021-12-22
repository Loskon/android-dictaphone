package com.loskon.androidprojectdictaphone.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;

/**
 * Звуковые настройки
 */

public class AudioSettings {
    public final int sampleRate = 8000 ; // 44100
    public final int format = AudioFormat.ENCODING_PCM_16BIT;
    public final int channel = AudioFormat.CHANNEL_IN_MONO;
    public final int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channel, format);
}
