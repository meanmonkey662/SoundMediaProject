package ctec.soundmedia.controller;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Color;
import android.widget.RelativeLayout;


public class SoundActivity extends Activity implements Runnable
{
    private Button startButton;
    private Button stopButton;
    private Button nextButton;
    private Button pauseButton;
    private Button videoPlayer;
    private Button videoButton;
    private MediaPlayer soundPlayer;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private Button colorButton;
    private RelativeLayout appbackground;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        startButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.music);
        colorButton = (Button) findViewById(R.id.colorButton);
        appbackground = (RelativeLayout) findViewById(R.id.background);

        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }

    private void changeColors()
    {
        int redColor;
        int greenColor;
        int blueColor;

        redColor = (int) (Math.random() * 256);
        greenColor = (int) (Math.random() * 256);
        blueColor = (int) (Math.random() *256);

        appbackground.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));

        redColor = (int) (Math.random() * 256);
        greenColor = (int) (Math.random() * 256);
        blueColor = (int) (Math.random() *256);

        colorButton.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
    }

    private void setupListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.start();
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View buttonView)
            {
                changeColors();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                soundPlayer.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.music);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.stop();
                appbackground.setBackgroundResource(R.drawable.nyan);
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.cat);
            }

        });

        videoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser)
                {
                    soundPlayer.seekTo(progress);
                }
            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal = soundPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);

        while (soundPlayer != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();
            }
            catch(InterruptedException soundException)
            {
                return;
            }
            catch(Exception otherExeption)
            {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }

}
