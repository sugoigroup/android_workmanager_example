package com.sugoijapaneseschool.workmanagerexample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.sugoijapaneseschool.workmanagerexample.MainActivity.FOUND_OUT_KEY;
import static com.sugoijapaneseschool.workmanagerexample.MainActivity.KEY_DETECTOR;

public class LogWorker  extends Worker {

    public LogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String detector = getInputData().getString(KEY_DETECTOR);

        Log.i("worker",   detector + " detector is founding a key!");

        //내보낼 값을 정하자
        Data outputData = new Data.Builder()
            .putString(FOUND_OUT_KEY, "elzzup")
            .build();
        return Result.success(outputData);
    }
}
