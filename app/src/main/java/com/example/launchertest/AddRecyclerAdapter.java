package com.example.launchertest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddRecyclerAdapter extends RecyclerView.Adapter<AddRecyclerAdapter.ItemViewHolder> {

    private ArrayList<Data> listData;
    private DBHandler handler;
    public AddRecyclerAdapter(ArrayList<Data> listData) {
        this.listData = listData;
    }

    public AddRecyclerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        AddRecyclerAdapter.ItemViewHolder viewHolder = new AddRecyclerAdapter.ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
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
                        Intent launchIntent = new Intent(context.getPackageManager().getLaunchIntentForPackage(listData.get(pos).getPack()));
                        context.startActivity(launchIntent);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        final Context context = v.getContext();
                        handler = DBHandler.open(context);
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);    //다이얼로그
                        alert.setTitle("앱목록 삭제");
                        alert.setMessage("앱목록을 삭제하시겠습니까?");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                handler.delete(listData.get(pos).getPack());
                                remove(pos);                            //클릭된 뷰 삭제 및 데이터 삭제
                                dialog.dismiss();
                            }
                        });
                        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.show();
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
    public void remove(int position) {                   //해당포지션 삭제
        try {
            listData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listData.size());
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
