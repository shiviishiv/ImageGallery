package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.imagegallery.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hendrawd.storageutil.library.StorageUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    List<String>myImageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            String[] externalStoragePaths = StorageUtil.getStorageDirectories(this);
            for(String temp:externalStoragePaths)
                getDataFunction(new File(temp));
            for (String t:myImageList)
                Log.d(TAG, "onCreate: "+t.toString());
            activityMainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,3));
            activityMainBinding.recyclerView.setAdapter(new MyAdapter(this,myImageList));

        }else{
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
    }

    private void getDataFunction(File file) {
        if(file.isDirectory()){
            final String[] list = file.list();
            for(String temp:list)
                getDataFunction(new File(file,temp));

        }
        String temp = file.getAbsolutePath();
        if(temp.endsWith("jpg") || temp.endsWith("png"))
            myImageList.add(temp);
    }

}