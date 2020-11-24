package com.example.launchertest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppAddRecyclerAdapter extends RecyclerView.Adapter<AppAddRecyclerAdapter.ItemViewHolder>{

    private DBHandler handler;
    private ArrayList<Data> listData;

    public AppAddRecyclerAdapter(ArrayList<Data> listData) {
        this.listData = listData;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView TitleText;
        private TextView PackText;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            TitleText = itemView.findViewById(R.id.title);
            PackText = itemView.findViewById(R.id.pack);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {       //해당 아이템 클릭 리스너
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Context context = v.getContext();
                        handler = DBHandler.open(context);
                        handler.insert(listData.get(pos).getPack());
                        Intent list = new Intent(context.getApplicationContext(), MainActivity.class);
                        context.startActivity(list.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }

        void onBind(Data data) {
            TitleText.setText(data.getTitle());
            PackText.setText(data.getPack());
            imageView.setImageDrawable(data.getIcon());
        }
    }
}
