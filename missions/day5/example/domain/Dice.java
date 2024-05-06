package example.domain;

import example.domain.strategy.DiceRollStrategy;

public class Dice {

    private final int minNumber;
    private final int maxNumber;
    private final DiceRollStrategy rollStrategy;

    public Dice(int minNumber, int maxNumber, DiceRollStrategy rollStrategy) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.rollStrategy = rollStrategy;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int roll() {
        return rollStrategy.roll(minNumber, maxNumber);
    }
}
