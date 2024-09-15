package com.nobel.game.service.impl;

import com.nobel.game.models.Move;
import com.nobel.game.service.AbstractGameService;
import com.nobel.game.service.RPCGameOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PastMoveFrequencyAwareGameService extends AbstractGameService implements RPCGameOperations {
    private final Map<Move, Integer> playerMoveHistory = new HashMap<>();

    public PastMoveFrequencyAwareGameService() {
        resetGameState();
    }

    public Move predictPlayerMove() {
        return playerMoveHistory.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
    }

    @Override
    public void resetGameState() {
        for (Move move : Move.values()) {
            playerMoveHistory.put(move, 0);
        }
        getStatistics().reset();
    }

    @Override
    public void updateGameState(Move playerMove) {
        playerMoveHistory.put(playerMove, playerMoveHistory.get(playerMove) + 1);
    }
}