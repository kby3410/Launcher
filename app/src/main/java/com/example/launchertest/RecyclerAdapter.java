package com.example.launchertest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
    private ArrayList<Data> listData;

    public RecyclerAdapter(ArrayList<Data> listData) {
        this.listData = listData;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
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
                                                    //카메라 버튼 다이얼로그
                        Intent launchIntent = new Intent(context.getPackageManager().getLaunchIntentForPackage(listData.get(pos).getPack()));
                        context.startActivity(launchIntent);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Context context = v.getContext();
                        Uri uri = Uri.fromParts("package", listData.get(pos).getPack(), null);
                        Intent uninstallIntent  = new Intent(Intent.ACTION_DELETE,uri);
                        context.startActivity(uninstallIntent);
                    }
                    return true;
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
