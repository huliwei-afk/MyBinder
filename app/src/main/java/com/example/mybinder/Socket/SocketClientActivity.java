package com.example.mybinder.Socket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mybinder.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_receive;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        initView();
        Intent service = new Intent(this, SocketServerService.class);
        startService(service);
        //防止报android.os.NetworkOnMainThreadException异常
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new Thread() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }.start();
    }

    private void initView() {
        et_receive= (EditText) findViewById(R.id.et_receive);
        bt_send= (Button) findViewById(R.id.bt_send);
        tv_message= (TextView) this.findViewById(R.id.tv_message);
        bt_send.setOnClickListener(v -> {
            final String msg = et_receive.getText().toString();
            //向服务器发送信息
            if(!TextUtils.isEmpty(msg)&&null!=mPrintWriter) {
                mPrintWriter.println(msg);
                tv_message.setText(tv_message.getText() + "\n" + "客户端：" + msg);
                et_receive.setText("");
            }
        });
    }

    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                //选择和服务器相同的端口8688
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                SystemClock.sleep(1000);
            }
        }
        try {
            // 接收服务器端的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()) {
                final String msg = br.readLine();
                if (msg != null) {
                    runOnUiThread(() -> tv_message.setText(tv_message.getText() + "\n" + "服务端：" + msg)
                    );
                }
            }
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}