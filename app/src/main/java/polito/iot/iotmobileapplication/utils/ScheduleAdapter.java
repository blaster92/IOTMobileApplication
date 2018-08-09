package polito.iot.iotmobileapplication.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import polito.iot.iotmobileapplication.R;

/**
 * Created by user on 06/08/2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    public ArrayList<Schedule> schedules;

    private Context mContext;

    public ScheduleAdapter(Context mContext) {
        this.mContext = mContext;
        this.schedules = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView title;
        private TextView subtitle;
        private ImageView logo;
        private Button action;
        private TextView status;
        private TextView duration;
        private RecyclerView exercise_rv;
        private ArrayList<Exercise> exercise_list;

        private ViewHolder(View v) {
            super(v);

        }

        public ViewHolder(View itemView, TextView title, TextView subtitle, ImageView logo, Button action, TextView status, TextView duration, RecyclerView exercise_rv) {
            super(itemView);
            this.title = title;
            this.subtitle = subtitle;
            this.logo = logo;
            this.action = action;
            this.status = status;
            this.duration = duration;
            this.exercise_rv = exercise_rv;
        }




    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_element, parent, false);
        ViewHolder vh = new ViewHolder(v);

        vh.title = ((TextView)v.findViewById(R.id.title));
        vh.subtitle = (TextView) v.findViewById(R.id.subtitle);
        vh.exercise_rv = (RecyclerView) v.findViewById(R.id.exercise_rv);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(this.schedules.get(position).getName());
        holder.subtitle.setText(this.schedules.get(position).getObjective());

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this.mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext,3);
        holder.exercise_rv.setLayoutManager(gridLayoutManager);
        holder.exercise_rv.setAdapter(exerciseAdapter);

        for (Exercise e : (ArrayList<Exercise>)this.schedules.get(position).getExercises())

        exerciseAdapter.exercises.add(new Exercise("AAA","20","bello",20,20));
        exerciseAdapter.exercises.add(new Exercise("BB","20","bello1",4,11));
        exerciseAdapter.exercises.add(new Exercise("CC","20","bello2",2,55));
        exerciseAdapter.exercises.add(new Exercise("DD","20","bello3",10,55));
        exerciseAdapter.exercises.add(new Exercise("EE","20","bello4",2,55));


    }


    @Override
    public int getItemCount() {
        return schedules.size();
    }
}