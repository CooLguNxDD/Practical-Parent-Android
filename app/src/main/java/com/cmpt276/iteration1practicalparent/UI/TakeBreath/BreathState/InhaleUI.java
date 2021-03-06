package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;

import java.util.Locale;

import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.NBreath;
import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.programState;

public class InhaleUI extends StateControlCommend {
    private TextView setBreathText, showBreathText;
    private SeekBar selectNBreath;
    private Button beginBreath,breathingButton;
    private ImageView breathingCircle;
    private Animation animation;
    private MediaPlayer inhaleSound;

    @Override
    public void Run(Context context, View view) {
        super.Run(context, view);
        programState = 2;
        Toast.makeText(context, "breath in inhale ui",
                Toast.LENGTH_SHORT).show(); //debug checking
        initialLayout(context,view);

    }
    @Override

    public void setNextState(Context context, View view,StateControlCommend state){
        //set button to the next event
        super.setNextState(context,view, state);
        state.Run(context, view);//move to next event
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setStateChangeTimer(Context context,View view){
        final boolean[] canProcess = {false};
        CountDownTimer timer_3s = new CountDownTimer(3100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showBreathText.setText(String.format(Locale.CANADA, view.getResources().getString(R.string.remain_time_text)
                        ,millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                showBreathText.setText(R.string.to_release_text);
                canProcess[0] = true; //can process to next activity after 3s
                cancel();
            }
        };
        CountDownTimer timer_10s = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //do something
            }
            @Override
            public void onFinish() {
                setNextState(context,view,new ExhaleUI()); //to exhale UI after 10s
                inhaleSound.stop();
                cancel();
            }
        };
        breathingButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("doing","touching");
                    timer_3s.start();
                    timer_10s.start();
                    breathingCircle.startAnimation(animation);
                    inhaleSound = MediaPlayer.create(context, R.raw.inhale_sound);
                    inhaleSound.start();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("doing","releasing");
                    timer_3s.cancel();
                    timer_10s.cancel();
                    setInhaleUI(context,view);
                    breathingCircle.clearAnimation();
                    if (canProcess[0]){
                        setNextState(context,view,new ExhaleUI());
                    }
                    inhaleSound.stop();
                }
                /*params.width = 300;
                params.height = 300;
                breathingCircle.setLayoutParams(params);*/
                return false;
            }
        });
    }

    @Override
    public void initialLayout(Context context, View view) {
        super.initialLayout(context, view);
        selectNBreath   = (SeekBar) view.findViewById(R.id.select_n_breath_seek_bar);//reset the UI elements of Breath
        setBreathText   = (TextView)view.findViewById(R.id.set_breath_text);
        beginBreath     = (Button)  view.findViewById(R.id.begin_breath_button);
        breathingButton = (Button)  view.findViewById(R.id.breathing_button);
        showBreathText  = (TextView)view.findViewById(R.id.show_breath_text);
        breathingCircle = (ImageView) view.findViewById(R.id.breathing_circle);
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_zoom_in);
        inhaleSound = MediaPlayer.create(context, R.raw.inhale_sound);

       breathingCircle.setImageResource(R.drawable.green_circle);

        beginBreath.setEnabled(false);//disable the button
        beginBreath.setVisibility(View.INVISIBLE);

        breathingButton.setEnabled(true);
        breathingButton.setVisibility(View.VISIBLE);

        if (NBreath == 0){ //no more breath
            beginBreath.setEnabled(true);
            beginBreath.setVisibility(View.VISIBLE);
            breathingButton.setEnabled(false); //disable the button
            breathingButton.setVisibility(View.INVISIBLE);

            setFinishText();
            NBreath = 3;//set back to default
            beginBreath.setOnClickListener((view1)->new BreathUI().Run(context,view)); //set button back to the breathing
        }
        else{
            setInhaleUI(context,view);
            setStateChangeTimer(context,view); //next activity base on timer
        }

    }
    private void setInhaleUI(Context context, View view){
        selectNBreath.setVisibility(View.INVISIBLE);
        setText(view);
    }
    private void setText(View view){
        breathingButton.setText(R.string.button_breath_in);
        setBreathText.setText(String.format(view.getResources().getString(R.string.remain_breath),NBreath));
        showBreathText.setText(R.string.text_breath_in);
        showBreathText.setTextSize(20);

    }
    private void setFinishText(){
        beginBreath.setText(R.string.good_job_button);
        setBreathText.setText("");
        showBreathText.setText(R.string.finish_text);
        showBreathText.setTextSize(20);
    }
}
