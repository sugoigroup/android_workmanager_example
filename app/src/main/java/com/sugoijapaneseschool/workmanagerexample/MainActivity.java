package com.sugoijapaneseschool.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 충전중일때만 worker 가 실행되도록 제한을 건다.
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // 이번 한번만 일하도록 한다.
        OneTimeWorkRequest oneTimeWorkRequest =  new OneTimeWorkRequest.Builder(UpladWorker.class)
                .setConstraints(constraints)
                .build();

        // 매니져에게 일할거라고 등록한다.
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }
}