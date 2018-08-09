package polito.iot.iotmobileapplication.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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


        private ViewHolder(View v) {
            super(v);

        }


        public ViewHolder(View itemView, TextView title){
            super(itemView);
            this.title = title;


        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_entry, parent, false);
        ExerciseAdapter.ViewHolder vh = new ExerciseAdapter.ViewHolder(v);

        vh.title = (TextView) v.findViewById(R.id.name);


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
    public void onBindViewHolder(ExerciseAdapter.ViewHolder holder, int position) {

        holder.title.setText(exercises.get(position).getName());


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
