package com.sugoijapaneseschool.workmanagerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    final String CANCEL_ME = "Cancel me";
    static final String FOUND_OUT_KEY = "workerFoundoutKey";
    static final String KEY_DETECTOR = "keyman";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.hello);
        tv.setOnClickListener(e -> {
            startQueue();
        });
        startQueue();
    }

    private void startQueue() {

        // 충전중일때만 worker 가 실행되도록 제한을 건다.
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // 이번 한번만 일하도록 한다.
        final OneTimeWorkRequest  oneTimeWorkRequest =  new OneTimeWorkRequest.Builder(UpladWorker.class)
                .setConstraints(constraints)
                .addTag(CANCEL_ME)
                .build();

        //보낼값을 정하자.
        Data whoIsTheDetector = new Data.Builder().putString(KEY_DETECTOR, "kim").build();

        // 이번 한번만 일하도록 한다.
        final OneTimeWorkRequest  logWorkerRequest =  new OneTimeWorkRequest.Builder(LogWorker.class)
                .setConstraints(constraints)
                .setInputData(whoIsTheDetector)
                .addTag(CANCEL_ME)
                .build();

        // 매니져에게 일할거라고 등록한다.
        // then 으로 순차적인 workRequest를 실행할수 있다.
        WorkManager.getInstance(this)
                .beginUniqueWork("iamUnique", ExistingWorkPolicy.APPEND_OR_REPLACE, oneTimeWorkRequest)
                .then(logWorkerRequest)
                .enqueue();

        // 결과를
        foundKeyByWorkerGuy(logWorkerRequest.getId());
    }

    private void foundKeyByWorkerGuy(UUID workerUUID) {
        LiveData<WorkInfo> lf = WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerUUID);

        lf.observe(this, workInfo -> {
            if (workInfo.getOutputData().getString(FOUND_OUT_KEY) != null) {
                tv.setText("The Key is  " + workInfo.getOutputData().getString(FOUND_OUT_KEY));
            } else {
                tv.setText("Finding the key");
            }
        });
    }
}