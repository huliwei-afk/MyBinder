package com.example.mybinder.Messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;


public class MessengerService extends Service {
    public static final String TAG = "TAG";
    public static final int MSG_FROMCLIENT = 1000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case MSG_FROMCLIENT:
                    Log.i(TAG,"收到客户端信息-----" + message.getData().get("message"));
                    Messenger messenger = message.replyTo;
                    Message mMessage = Message.obtain(null, MessengerService.MSG_FROMCLIENT);
                    Bundle bundle = new Bundle();
                    bundle.putString("rep", "这里是服务端，我们收到信息了");
                    mMessage.setData(bundle);
                    try {
                        messenger.send(mMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(mHandler).getBinder();
    }
}
