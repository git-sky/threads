package cn.com.sky.thread_classic.terminal.interrupt;

import cn.com.sky.thread_juc.concurrent.tools.countdownlatch.CountDownLatchPlayer;

/**
 * 2、Performing task-specific cleanup before rethrowing InterruptedException
 */
public class PlayerMatcher {

    private PlayerSource players;

    public PlayerMatcher(PlayerSource players) {
        this.players = players;
    }

    public void matchPlayers() throws InterruptedException {
        CountDownLatchPlayer.Player playerOne = null;
        CountDownLatchPlayer.Player playerTwo = null;
        try {
            while (true) {
                playerOne = playerTwo = null;
                // Wait for two players to arrive and start a new game
                playerOne = players.waitForPlayer(); // could throw IE
                playerTwo = players.waitForPlayer(); // could throw IE
                startNewGame(playerOne, playerTwo);
            }
        } catch (InterruptedException e) {
            // If we got one player and were interrupted, put that player back
            if (playerOne != null)
                players.addFirst(playerOne);
            // Then propagate the exception
            throw e;
        }
    }

    private void startNewGame(CountDownLatchPlayer.Player playerOne, CountDownLatchPlayer.Player playerTwo) throws InterruptedException {
        throw new InterruptedException();
    }

    private class PlayerSource {
        CountDownLatchPlayer.Player waitForPlayer() {
            return null;
        }

        void addFirst(CountDownLatchPlayer.Player playerOne) {

        }
    }

}