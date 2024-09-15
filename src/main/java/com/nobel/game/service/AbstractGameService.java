package com.nobel.game.service;

import com.nobel.game.models.Move;
import com.nobel.game.models.Result;
import com.nobel.game.models.Statistics;

public abstract class AbstractGameService implements RPCGameOperations {
    private final Statistics statistics = new Statistics();

    @Override
    public Result playOneRound(Move playerMove) {
        Move computerMove = counterMove(predictPlayerMove());

        updateGameState(playerMove);

        if (playerMove == computerMove) {
            return getStatistics().addDraw();
        }

        boolean playerWins = PLAYER_WINS_BIPREDICATE.test(playerMove, computerMove);

        return playerWins ? getStatistics().addWin() : getStatistics().addLoss();
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }
}
