package it.jcu.edu.au.soundpooldemo;

import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.security.PublicKey;


public class MainActivity extends ActionBarActivity {

    private SoundSystem soundSystem;

    SoundPool pool;
    int thunderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundSystem = new SoundSystem(this);
    }

    @Override
    protected void onPause() {
        soundSystem.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundSystem.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int maxX = size.x;
        int maxY = size.y;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (touchX < (maxX/2)) {
                    if (touchY < (maxY/2)) {
                        // top left
                        soundSystem.play(soundSystem.SONAR);
                    } else {
                        // top right
                        soundSystem.play(soundSystem.THUNDER);
                    }
                } else {
                    if (touchY < (maxY/2)) {
                        // bottom left
                        soundSystem.play(soundSystem.HORN);
                    } else {
                        // bottom right
                        soundSystem.play(soundSystem.SCREAM);
                    }
                }
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
