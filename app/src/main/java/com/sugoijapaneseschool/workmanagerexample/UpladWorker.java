package com.sugoijapaneseschool.workmanagerexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpladWorker extends Worker {
    private int count = 0;

    public UpladWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        while (count < 10) {
            if (isStopped()) {
                continue;
            }
            count++;
            try {
               // Log.i("worker", "now background" + count);
                showNotification("worker", "now background" + count);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Result.success();

    }

    private void showNotification(String task, String desc) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(
                Context.NOTIFICATION_SERVICE);
        String channelId = "my_channel";
        String channelName = "my_name";
        // Oreo 부터는 노티에 알림하려면 채널이 있어야 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setContentTitle(task)
                        .setContentText(desc)
                        .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }
}
