package com.example.override.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.override.utils.HelperUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

public class StatisticService extends Service {

    private AtomicBoolean status = new AtomicBoolean(false);

    private static final Logger log = LoggerFactory.getLogger(StatisticService.class);
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HelperUtils.init(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!status.get()){
            status.set(true);
            new Thread(() -> {
                over:
                while (true) {
                    try {
                        for (int i = 0; i < 60; i++) {
                            TimeUnit.SECONDS.sleep(1);
                            if (!status.get()) {
                                break over;
                            }
                        }
                        if (status.get()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                HelperUtils.generateEcFile(true);
                            }
                        } else {
                            break;
                        }
                    } catch (InterruptedException e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
