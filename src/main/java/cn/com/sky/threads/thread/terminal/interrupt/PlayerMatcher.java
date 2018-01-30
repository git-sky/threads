package cn.com.sky.threads.thread.terminal.interrupt;

import cn.com.sky.threads.concurrent.tools.countdownlatch.Player;

/**
 * 2、Performing task-specific cleanup before rethrowing InterruptedException
 */
public class PlayerMatcher {

    private PlayerSource players;

    public PlayerMatcher(PlayerSource players) {
        this.players = players;
    }

    public void matchPlayers() throws InterruptedException {
        Player playerOne = null;
        Player playerTwo = null;
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

    private void startNewGame(Player playerOne, Player playerTwo) throws InterruptedException {
        throw new InterruptedException();
    }

    private class PlayerSource {
        Player waitForPlayer() {
            return null;
        }

        void addFirst(Player playerOne) {

        }
    }

}