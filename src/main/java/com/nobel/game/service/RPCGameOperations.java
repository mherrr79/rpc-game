package com.nobel.game.service;

import com.nobel.game.models.Move;
import com.nobel.game.models.Result;
import com.nobel.game.models.Statistics;

import java.util.function.BiPredicate;

public interface RPCGameOperations {
    Result playOneRound(Move playerMove);

    void updateGameState(Move playerMove);

    void resetGameState();

    Move predictPlayerMove();

    Statistics getStatistics();

    BiPredicate<Move, Move> PLAYER_WINS_BIPREDICATE = (player, computer) -> (player == Move.ROCK && computer == Move.SCISSORS) || (player == Move.PAPER && computer == Move.ROCK) || (player == Move.SCISSORS && computer == Move.PAPER);

    default Move counterMove(Move move) {
        return switch (move) {
            case ROCK -> Move.PAPER;
            case PAPER -> Move.SCISSORS;
            case SCISSORS -> Move.ROCK;
        };
    }
}
