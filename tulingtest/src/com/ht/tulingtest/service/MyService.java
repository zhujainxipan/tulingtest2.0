package com.ht.tulingtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import com.ht.tulingtest.thread.MyTask;

/**
 * Created by annuo on 2015/5/16.
 */
public class MyService extends Service {

    private Handler handler;
    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void getMessenges(String inputStr) {
            Thread thread = new Thread(new MyTask(inputStr, handler));
            thread.start();
        }

        public void setHanlder(Handler hanlder) {
            MyService.this.handler = hanlder;
        }
    }
}
