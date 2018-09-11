package polito.iot.iotmobileapplication.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import polito.iot.iotmobileapplication.R;

/**
 * Created by user on 09/09/2018.
 */

public class ScheduleBottomSheetFragment extends BottomSheetDialogFragment {


    private String state="";

    public ScheduleBottomSheetFragment() {
        super();
    }



    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.schedule_info, null);
        dialog.setContentView(view);

        ((TextView)view.findViewById(R.id.title)).setText(getArguments().getString("name"));
        ((TextView)view.findViewById(R.id.details)).setText(getArguments().getString("details"));
        ((TextView)view.findViewById(R.id.duration)).setText(getArguments().getString("start_date")+"-"+getArguments().getString("end_date"));
        ((TextView)view.findViewById(R.id.objective)).setText(getArguments().getString("objective"));
        ((TextView)view.findViewById(R.id.days)).setText(String.valueOf(getArguments().getInt("days")));


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {


                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                    //Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }


            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //dismiss();

    }

    public String getState(){

        return state;

    }



}