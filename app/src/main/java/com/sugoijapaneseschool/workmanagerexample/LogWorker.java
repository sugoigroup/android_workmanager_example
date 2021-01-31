package com.sugoijapaneseschool.workmanagerexample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LogWorker  extends Worker {

    public LogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
         Log.i("worker", "Wow All Done!");
        return Result.success();
    }
}
