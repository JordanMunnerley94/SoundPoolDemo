package it.jcu.edu.au.soundpooldemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


public class SoundSystem {

    private SoundPool pool;
    public int THUNDER;
    public int HORN;
    public int SONAR;
    public int SCREAM;
    private boolean ready;
    private int count;
    private static final int MAX_COUNT = 4;

    private Looper looper;
    private Handler handler;
    private class LooperThread extends Thread {
        @Override
        public void run() {
            try {
                Looper.prepare();
                looper = Looper.myLooper();
                handler = new Handler(looper);
                Looper.loop(); // thread blocks here
            } catch (Throwable t) {
                Log.w("SoundSystem", "looper terminated...");
            }
        }
    }


    public SoundSystem(Context context) {
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);

        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleID, int status) {

                if (status != 0) {
                    Log.e("SoundSystem", "error loading samples");
                    return;
                }
                ++count;
                if (count == MAX_COUNT) {
                    ready = true;
                    Log.i("SoundSystem", "All loads completed!");
                }
            }
        });

        THUNDER = pool.load(context, R.raw.thunder,1);
        SCREAM = pool.load(context, R.raw.scream,1);
        HORN = pool.load(context, R.raw.horn,1);
        SONAR = pool.load(context, R.raw.sonar,1);
    }

    public void start() {
        LooperThread thread = new LooperThread();
        thread.start();
    }

    public void stop() {
        looper.quit();
    }

    public void play(final int sampleID) {
        if (!ready) return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                pool.play(sampleID, 1, 1, 1, 0, 1);
            }
        });
    }

}
