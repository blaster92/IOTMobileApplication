package polito.iot.iotmobileapplication.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import polito.iot.iotmobileapplication.R;

/**
 * Created by user on 09/09/2018.
 */

//status = 1 pause - 0 play

public class ServiceEntry extends Fragment {

    private TextView name,muscolar_zone,description,details,repetitions,weigth;
    private RelativeLayout background;
    private Button video;
    private FloatingActionButton play_pause, restart;
    private int status = 1;
    private TextView timer_view;
    private CircularProgressBar progressBar;
    private Timer timer;
    private SimpleDateFormat format;
    private int time = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.service_entry, container, false);
        name = (TextView) rootView.findViewById(R.id.title);
        description = (TextView) rootView.findViewById(R.id.description_txt);
        muscolar_zone = (TextView) rootView.findViewById(R.id.muscolar_zone);
        details = (TextView) rootView.findViewById(R.id.details);
        repetitions = (TextView) rootView.findViewById(R.id.repetitions);
        weigth = (TextView) rootView.findViewById(R.id.weight);
        video = (Button) rootView.findViewById(R.id.video);
        play_pause = (FloatingActionButton) rootView.findViewById(R.id.play_pause);
        restart = (FloatingActionButton) rootView.findViewById(R.id.restart);
        timer_view = (TextView) rootView.findViewById(R.id.timer);
        progressBar = (CircularProgressBar) rootView.findViewById(R.id.circularProgressBar);

        timer = new Timer();
        format = new SimpleDateFormat("hh:mm:ss");

        background = (RelativeLayout) rootView.findViewById(R.id.back);
        background.setBackgroundColor(getArguments().getInt("color"));

        try {
            final JSONObject exercise = new JSONObject(getArguments().getString("exercise"));
            name.setText(exercise.getString("name"));
            description.setText(exercise.getString("description"));
            details.setText(exercise.getString("details"));
            muscolar_zone.setText(exercise.getString("muscolar_zone"));
            repetitions.setText(String.valueOf(exercise.getInt("repetitions")));
            weigth.setText(String.valueOf(exercise.get("weight")));

            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    time = 0;
                    timer_view.setText("00:00:00");

                }
            });
            play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (status == 1){

                        play_pause.setImageResource(R.drawable.baseline_pause_white_24);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            play_pause.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF6D959")));
                        }
                        if (timer==null)
                            timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {


                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        time++;
                                        int hours = time / 3600;
                                        int minutes = (time % 3600) / 60;
                                        int seconds = time % 60;

                                        timer_view.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                                    }
                                });



                            }
                        },0,1000);

                        status = 0;
                    }else{

                        play_pause.setImageResource(R.drawable.baseline_play_arrow_white_24);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            play_pause.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF3F8793")));
                        }
                        timer.cancel();
                        timer.purge();
                        timer = null;


                        status = 1;
                    }

                }
            });


            video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        WebDialogFragment dialogFragment = WebDialogFragment.newInstance(exercise.getString("url"));
                        dialogFragment.show(getChildFragmentManager(),"video");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }




        return rootView;
    }

}
