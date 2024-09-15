package com.nobel.game.service.impl;

import com.nobel.game.models.Move;
import com.nobel.game.service.AbstractGameService;
import com.nobel.game.service.RPCGameOperations;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MarkovChainBasedGameService extends AbstractGameService implements RPCGameOperations {
    private final Map<Move, Map<Move, Integer>> transitionMatrix = new HashMap<>();
    private Move lastPlayerMove = null;

    public MarkovChainBasedGameService() {
        resetGameState();
    }

    public Move predictPlayerMove() {
        if (lastPlayerMove == null) {
            // No history, default to random
            return Move.values()[new Random().nextInt(Move.values().length)];
        }

        Map<Move, Integer> transitions = transitionMatrix.get(lastPlayerMove);
        return transitions.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    public void resetGameState() {
        for (Move move : Move.values()) {
            transitionMatrix.put(move, new HashMap<>());
            for (Move nextMove : Move.values()) {
                transitionMatrix.get(move).put(nextMove, 0);
            }
        }
        getStatistics().reset();
    }

    @Override
    public void updateGameState(Move playerMove) {
        if (lastPlayerMove != null) {
            // Update transition counts
            Map<Move, Integer> transitions = transitionMatrix.get(lastPlayerMove);
            transitions.put(playerMove, transitions.get(playerMove) + 1);
        }
        lastPlayerMove = playerMove;
    }
}
