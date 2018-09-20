package polito.iot.iotmobileapplication.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.utils.Message;
import polito.iot.iotmobileapplication.utils.MessageAdapter;

/**
 * Created by user on 06/08/2018.
 */

public class NotificationFragment extends Fragment {

    public interface NotificationInterface{

        void findMessages(Intent intent);

    }


    private RecyclerView message_view;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_notification,null);

        message_view = (RecyclerView) view.findViewById(R.id.message_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        message_view.setLayoutManager(linearLayoutManager);

        adapter = new MessageAdapter(getContext());

        message_view.setAdapter(adapter);





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

    public void addMessages(ArrayList<Message> list){

        adapter.messages.clear();

        adapter.messages.addAll(list);

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showLastMessage(){

        if (adapter.messages.size()>0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(adapter.messages.get(0).getTitle());
            builder.setMessage(adapter.messages.get(0).getBody());
            builder.setCancelable(true);

            builder.create().show();

        }

    }
}
