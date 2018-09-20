package polito.iot.iotmobileapplication.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import polito.iot.iotmobileapplication.R;

/**
 * Created by user on 09/09/2018.
 */

public class ExerciseDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.exercise_info, container, false);

        ((TextView)rootView.findViewById(R.id.title)).setText(getArguments().getString("name"));
        ((TextView)rootView.findViewById(R.id.details)).setText(getArguments().getString("details"));
        ((TextView)rootView.findViewById(R.id.description_txt)).setText(getArguments().getString("description"));
        ((TextView)rootView.findViewById(R.id.repetitions)).setText(String.valueOf(getArguments().getInt("repetitions")));
        ((TextView)rootView.findViewById(R.id.weight)).setText(String.valueOf(getArguments().getFloat("weight")));
        ((TextView)rootView.findViewById(R.id.muscular_zone)).setText(getArguments().getString("muscular_zone"));
        WebView web = ((WebView) rootView.findViewById(R.id.video));

        if (!getArguments().getString("url").equals("")) {
            web.setWebViewClient(new Callback());
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl(getArguments().getString("url"));
        } else {

            Toast.makeText(getContext(),"No video for this exercise is available",Toast.LENGTH_LONG).show();

        }


        return rootView;
    }


    private class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

    }

}
