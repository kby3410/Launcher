package com.example.launchertest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AppAdd extends AppCompatActivity {
    private ArrayList<Data> List = new ArrayList();               //메모 파일들의 목록
    private AppAddRecyclerAdapter adapter;
    private ArrayList<String> apps = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Drawable> icon = new ArrayList<>();
    private Intent myListIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        RecyclerView recyclerView = findViewById(R.id.Recycle);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,6);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new AppAddRecyclerAdapter(List);
        recyclerView.setAdapter(adapter);

        myListIntent = new Intent(Intent.ACTION_MAIN, null);
        myListIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        java.util.List<ResolveInfo> pack = getPackageManager().queryIntentActivities(myListIntent,0);
        for(int i = 0; i < pack.size(); i++){
            apps.add(pack.get(i).activityInfo.applicationInfo.packageName);
            try {
                icon.add(getPackageManager().getApplicationIcon(pack.get(i).activityInfo.applicationInfo.packageName));
                name.add(getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(pack.get(i).activityInfo.applicationInfo.packageName, PackageManager.GET_UNINSTALLED_PACKAGES)).toString());
            }catch (PackageManager.NameNotFoundException e){}
            Data save_data = new Data(name.get(i),apps.get(i),icon.get(i));
            List.add(i,save_data);
        }
        adapter.notifyDataSetChanged();
    }
}
