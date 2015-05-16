package com.ht.tulingtest.thread;

import android.os.Handler;
import android.os.Message;
import com.ht.tulingtest.Util.HttpUtils;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * Project: com.ht.tulingtest.tasks
 * Author: 安诺爱成长
 * Email: 1399487511@qq.com
 * Date: 2015/5/14
 */
public class MyTask implements Runnable {
    private String inputStr;
    private Handler handler;

    public MyTask(String inputStr, Handler handler) {
        this.inputStr = inputStr;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            String receivecontent = HttpUtils.getResponse(inputStr);
            Message message = handler.obtainMessage(998, receivecontent);
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
