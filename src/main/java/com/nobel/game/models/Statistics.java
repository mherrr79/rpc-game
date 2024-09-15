package com.nobel.game.models;

import lombok.Getter;

@Getter
public class Statistics {
    private int wins;
    private int losses;
    private int draws;

    public void reset() {
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public Result addWin() {
        this.wins += 1;
        return Result.WIN;
    }

    public Result addLoss() {
        this.losses += 1;
        return Result.LOSE;
    }

    public Result addDraw() {
        this.draws += 1;
        return Result.DRAW;
    }
}