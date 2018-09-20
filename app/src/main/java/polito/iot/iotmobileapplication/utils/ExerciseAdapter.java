package polito.iot.iotmobileapplication.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import polito.iot.iotmobileapplication.R;

/**
 * Created by user on 07/08/2018.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    public ArrayList<Exercise> exercises;
    private Context mContext;

    public ExerciseAdapter(Context mContext) {

        this.mContext = mContext;
        this.exercises = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView title;
        private ImageButton info;


        private ViewHolder(View v) {
            super(v);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_entry, parent, false);
        ExerciseAdapter.ViewHolder vh = new ExerciseAdapter.ViewHolder(v);

        vh.title = (TextView) v.findViewById(R.id.title);
        vh.info = (ImageButton) v.findViewById(R.id.info_btn);



        return vh;
    }



    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ViewHolder holder, final int position) {

        holder.title.setText(exercises.get(position).getName());

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExerciseDialogFragment fragment = new ExerciseDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name",exercises.get(position).getName());
                bundle.putString("description",exercises.get(position).getDescription());
                bundle.putString("details",exercises.get(position).getDetails());
                bundle.putInt("repetitions",exercises.get(position).getRepetitions());
                bundle.putFloat("weight",exercises.get(position).getWeight());
                bundle.putString("muscular_zone",exercises.get(position).getMuscularZone());
                bundle.putString("url",exercises.get(position).getUrl());

                fragment.setArguments(bundle);

                fragment.show(((FragmentActivity)mContext).getSupportFragmentManager(),"exercise_info");


            }
        });

        switch (position){

            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                break;
            case 4:
                break;


        }



    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
