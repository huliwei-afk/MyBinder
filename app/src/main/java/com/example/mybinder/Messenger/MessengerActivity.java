package com.example.mybinder.Messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.mybinder.R;

public class MessengerActivity extends AppCompatActivity {

    private Messenger mMessenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message mMessage = Message.obtain(null, MessengerService.MSG_FROMCLIENT);
            Bundle mBundle = new Bundle();
            mBundle.putString("message", "这里是客户端，服务端收到了吗");
            mMessage.setData(mBundle);
            mMessage.replyTo = new Messenger(mHandler);
            try{
                mMessenger.send(mMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          switch (msg.what){
              case MessengerService.MSG_FROMCLIENT:
                  Log.i(MessengerService.TAG, "收到服务端信息------" + msg.getData().get("rep"));
                  break;
          }
      }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}