package com.ht.tulingtest.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.ht.tulingtest.R;
import com.ht.tulingtest.adapter.MyAdapter;
import com.ht.tulingtest.pojo.Messages;
import com.ht.tulingtest.service.MyService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements ServiceConnection {

    private EditText input;
    private List<Messages> list;
    private MyAdapter adapter;
    private Handler handler;
    private ListView listView;
    private MyService.MyBinder myBinder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        input = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case 998:
                        String receivecontent = (String) msg.obj;
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = sDateFormat.format(System.currentTimeMillis());
                        Messages receivemessages = genernateMessages(receivecontent,
                                R.drawable.renma,
                                time,
                                "mm",
                                "receive");
                        list.add(receivemessages);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(adapter.getCount() - 1);
                        break;
                }
            }
        };
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    //点击提交的时候触发的事件。
    public void addOnClick(View view) {
        String inputStr = input.getText().toString();
        if (inputStr.length() > 0) {
            //添加一条发送信息
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sDateFormat.format(System.currentTimeMillis());
            Messages sendmessages = genernateMessages(inputStr,
                    R.drawable.xiaohei,
                    time,
                    "小黑",
                    "send");
            list.add(sendmessages);
            adapter.notifyDataSetChanged();
            input.setText("");
            listView.setSelection(adapter.getCount() - 1);
            myBinder.setHanlder(handler);
            myBinder.getMessenges(inputStr);
        }
    }

    private Messages genernateMessages(String content,
                                       int imageid,
                                       String time,
                                       String username,
                                       String msgtype) {
        Messages messages = new Messages();
        messages.setContent(content);
        messages.setImageid(imageid);
        messages.setTime(time);
        messages.setUsername(username);
        messages.setMsgtype(msgtype);
        return messages;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        myBinder = (MyService.MyBinder) iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
