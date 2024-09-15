package com.nobel.game.views.game;

import com.nobel.game.models.GameMode;
import com.nobel.game.models.Move;
import com.nobel.game.models.Result;
import com.nobel.game.models.Statistics;
import com.nobel.game.service.RPCGameOperations;
import com.nobel.game.service.impl.MarkovChainBasedGameService;
import com.nobel.game.service.impl.PastMoveFrequencyAwareGameService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("rock-paper-scissors")
public class GameView extends VerticalLayout {
    private RPCGameOperations gameService;
    private final PastMoveFrequencyAwareGameService pastMoveFrequencyAwareGameService;
    private final MarkovChainBasedGameService markovChainBasedGameService;

    private final Span resultSpan = new Span();
    private final Span statsSpan = new Span();

    @Autowired
    public GameView(PastMoveFrequencyAwareGameService pastMoveFrequencyAwareGameService,
                    MarkovChainBasedGameService markovChainBasedGameService) {

        this.pastMoveFrequencyAwareGameService = pastMoveFrequencyAwareGameService;
        this.markovChainBasedGameService = markovChainBasedGameService;

        // Default to Easy Mode
        this.gameService = pastMoveFrequencyAwareGameService;

        ComboBox<String> modeSelector = createGameModeSelector();

        Button rockButton = new Button("Rock", e -> playMove(Move.ROCK));
        Button paperButton = new Button("Paper", e -> playMove(Move.PAPER));
        Button scissorsButton = new Button("Scissors", e -> playMove(Move.SCISSORS));
        Button resetButton = new Button("Reset Game", e -> resetGame());

        add(modeSelector, rockButton, paperButton, scissorsButton, resultSpan, statsSpan, resetButton);
        updateStatisticsView();
    }

    private ComboBox<String> createGameModeSelector() {
        ComboBox<String> modeSelector = new ComboBox<>("Select Game Mode");
        modeSelector.setItems(GameMode.EASY.name(), GameMode.DIFFICULT.name());
        modeSelector.setValue(GameMode.EASY.name());

        modeSelector.addValueChangeListener(event -> {
            this.gameService = GameMode.EASY.name().equals(event.getValue())
                    ? this.pastMoveFrequencyAwareGameService
                    : this.markovChainBasedGameService;
            gameService.resetGameState();
            resultSpan.setText("Game mode changed to " + event.getValue() + ". Game reset.");
            updateStatisticsView();
        });
        return modeSelector;
    }

    private void playMove(Move playerMove) {
        Result result = gameService.playOneRound(playerMove);
        resultSpan.setText("You played " + playerMove + ". Result: " + result);
        updateStatisticsView();
    }

    private void resetGame() {
        gameService.resetGameState();
        resultSpan.setText("Game reset.");
        updateStatisticsView();
    }

    private void updateStatisticsView() {
        Statistics stats = gameService.getStatistics();
        statsSpan.setText("Wins: " + stats.getWins() + ", Losses: " + stats.getLosses() + ", Draws: " + stats.getDraws());
    }
}