package polito.iot.iotmobileapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.core.ServiceActivity;

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
        private TextView status;
        private TextView duration;
        private RecyclerView exercise_rv;
        private ArrayList<Exercise> exercise_list;
        private FloatingActionButton runBtn;
        private FloatingActionButton infoBtn;

        private ViewHolder(View v) {
            super(v);

        }

        public ViewHolder(View itemView, TextView title, TextView subtitle, ImageView logo, TextView status, TextView duration, RecyclerView exercise_rv, FloatingActionButton runBtn, FloatingActionButton infoBtn) {
            super(itemView);
            this.title = title;
            this.subtitle = subtitle;
            this.logo = logo;
            this.runBtn = runBtn;
            this.infoBtn = infoBtn;
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
        vh.infoBtn = ((FloatingActionButton) v.findViewById(R.id.info_action));
        vh.runBtn = ((FloatingActionButton) v.findViewById(R.id.action));
        //vh.subtitle = (TextView) v.findViewById(R.id.subtitle);
        vh.exercise_rv = (RecyclerView) v.findViewById(R.id.exercise_rv);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.title.setText(this.schedules.get(position).getName());
        //holder.subtitle.setText(this.schedules.get(position).getObjective());

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this.mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext,1);
        holder.exercise_rv.setLayoutManager(gridLayoutManager);
        holder.exercise_rv.setAdapter(exerciseAdapter);

        holder.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScheduleBottomSheetFragment fragment = new ScheduleBottomSheetFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name",schedules.get(position).getName());
                bundle.putString("details",schedules.get(position).getDetails());
                bundle.putString("objective",schedules.get(position).getObjective());
                bundle.putString("start_date",schedules.get(position).getStartDate());
                bundle.putString("end_date",schedules.get(position).getEndDate());
                bundle.putInt("days",schedules.get(position).getDays());

                fragment.setArguments(bundle);

                fragment.show(((FragmentActivity)mContext).getSupportFragmentManager(),"info");

            }
        });


        holder.runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, ServiceActivity.class);

                i.putExtra("id_schedule",schedules.get(position).getID());
                mContext.startActivity(i);

            }
        });

        for (Exercise e : (ArrayList<Exercise>)this.schedules.get(position).getExercises())
            exerciseAdapter.exercises.add(new Exercise(e.getName(),e.getMuscularZone(),e.getDescription(),e.getDetails(),e.getRepetitions(),e.getWeight(),e.getUrl()));



    }




    @Override
    public int getItemCount() {
        return schedules.size();
    }
}