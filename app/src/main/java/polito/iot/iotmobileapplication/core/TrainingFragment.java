package polito.iot.iotmobileapplication.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.utils.Schedule;
import polito.iot.iotmobileapplication.utils.ScheduleAdapter;

/**
 * Created by user on 06/08/2018.
 */

public class TrainingFragment extends Fragment {

    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_training,null);
        scheduleRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_rv);
        scheduleRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        scheduleRecyclerView.setLayoutManager(mLayoutManager);

        scheduleAdapter = new ScheduleAdapter(getContext());
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        scheduleAdapter.schedules.add(new Schedule("Exercise 1","Shoulders"));
        scheduleAdapter.schedules.add(new Schedule("Exercise 2","Arms"));
        scheduleAdapter.schedules.add(new Schedule("Exercise 3","Chest"));

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





}
