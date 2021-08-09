// IGameManager.aidl
package com.example.mybinder;
import com.example.mybinder.Game;
// Declare any non-default types here with import statements

interface IGameManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     List<Game> getGameList();
     void addGame(in Game game);
}