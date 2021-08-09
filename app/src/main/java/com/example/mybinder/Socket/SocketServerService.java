package com.example.mybinder.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerService extends Service {
    private boolean isServiceDestroyed = false;

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    private class TcpServer implements Runnable{
        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                return;
            }
            while (!isServiceDestroyed){
                try {
                    final Socket client = serverSocket.accept();
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException{
        //接收客户消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("您好，我是服务端");
        while(!isServiceDestroyed){
            String str = in.readLine();
            Log.i("TAG", "收到客户端的信息" + str);
            if(TextUtils.isEmpty(str)){
                Log.i("TAG","客户端断开链接");
                break;
            }
            String message = "收到了客户端的信息为：" + str;
            // 从客户端收到的消息加工再发送给客户端
            out.println(message);
        }
        out.close();
        in.close();
        client.close();
    }

    @Override
    public void onDestroy() {
        isServiceDestroyed = true;
        super.onDestroy();
    }
}
