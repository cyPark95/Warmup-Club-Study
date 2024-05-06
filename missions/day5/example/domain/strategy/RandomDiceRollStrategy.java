package example.domain.strategy;

public class RandomDiceRollStrategy implements DiceRollStrategy {

    @Override
    public int roll(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
