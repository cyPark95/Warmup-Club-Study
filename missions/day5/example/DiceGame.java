package example;

import example.domain.Dice;
import example.domain.DiceNumberCounter;
import example.domain.strategy.DiceRollStrategy;
import example.view.InputView;
import example.view.OutputView;

public class DiceGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final Dice dice;
    private final DiceNumberCounter diceNumberCounter;

    public DiceGame(int diceMinNumber, int diceMaxNumber, DiceRollStrategy strategy) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.dice = new Dice(diceMinNumber, diceMaxNumber, strategy);
        this.diceNumberCounter = new DiceNumberCounter();
    }

    public void start() {
        int diceRollCount = inputView.getDiceRollCount();
        rollDice(diceRollCount);
        printAllDiceNumberCount();
    }

    private void rollDice(int diceRollCount) {
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = dice.roll();
            diceNumberCounter.increaseCount(diceNumber);
        }
    }

    private void printAllDiceNumberCount() {
        for (int i = dice.getMinNumber(); i <= dice.getMaxNumber(); i++) {
            outputView.printDiceNumberCount(i, diceNumberCounter.getCount(i));
        }
    }
}
