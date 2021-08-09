package com.example.mybinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AIDLService extends Service {
    private CopyOnWriteArrayList<Game> mGameList = new CopyOnWriteArrayList<>();
    private Binder binder = new IGameManager.Stub(){

        @Override
        public List<Game> getGameList() throws RemoteException {
            return mGameList;
        }

        @Override
        public void addGame(Game game) throws RemoteException {
            mGameList.add(game);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mGameList.add(new Game("九阴真经","最好玩的武侠网游"));
        mGameList.add(new Game("大航海时代","最好玩的航海手游"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
