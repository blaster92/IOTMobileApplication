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
 * Created by user on 11/09/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public ArrayList<Message> messages;
    private Context mContext;

    public MessageAdapter(Context mContext) {

        this.mContext = mContext;
        this.messages = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView title,body,send_date;


        private ViewHolder(View v) {
            super(v);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_entry, parent, false);
        MessageAdapter.ViewHolder vh = new MessageAdapter.ViewHolder(v);

        vh.title = (TextView) v.findViewById(R.id.title);
        vh.body = (TextView) v.findViewById(R.id.body);
        vh.send_date = (TextView) v.findViewById(R.id.send_date);


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
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, final int position) {

        holder.title.setText(messages.get(position).getTitle());
        holder.body.setText(messages.get(position).getBody());
        holder.send_date.setText(messages.get(position).getSend_date());


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
