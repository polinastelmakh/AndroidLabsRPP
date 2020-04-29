package com.example.lab2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class VKListAdapter extends RecyclerView.Adapter<VKListAdapter.MyViewHolder>{

    private Singleton singleton = Singleton.getInstance();
    private int size;
    private Activity context;

    public VKListAdapter(int size, Activity context) {
        this.size = size;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForItem = R.layout.recycler_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idForItem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VKActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }

        void bind(int number) {
            position = number - 1;
            textView.setBackgroundResource(R.drawable.background_text_view_recycler);
            textView.setText(singleton.name[position]);
        }
    }
}

