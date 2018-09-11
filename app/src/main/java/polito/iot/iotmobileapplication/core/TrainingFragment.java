package polito.iot.iotmobileapplication.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.utils.LinePagerIndicatorDecoration;
import polito.iot.iotmobileapplication.utils.Schedule;
import polito.iot.iotmobileapplication.utils.ScheduleAdapter;

/**
 * Created by user on 06/08/2018.
 */

public class TrainingFragment extends Fragment {


    public interface TrainingInterface{

        void findSchedules(Intent intent);


    }


    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton infoBtn;
    private FloatingActionButton runBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_training,null);

        infoBtn = (FloatingActionButton) view.findViewById(R.id.info_action);
        runBtn = (FloatingActionButton) view.findViewById(R.id.action);

        scheduleRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_rv);
        scheduleRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        scheduleRecyclerView.setLayoutManager(mLayoutManager);

        scheduleAdapter = new ScheduleAdapter(getContext());
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        scheduleRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());






        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();






    }




    public void addScheduleList(ArrayList<Schedule> list){

        scheduleAdapter.schedules.clear();

        for (Schedule s : list)
            scheduleAdapter.schedules.add(new Schedule(s.getID(),s.getName(), s.getObjective(), s.getDetails(),s.getStartDate(),s.getEndDate(),s.getDays(),s.getExercises()));

        scheduleAdapter.notifyDataSetChanged();


    }
}
