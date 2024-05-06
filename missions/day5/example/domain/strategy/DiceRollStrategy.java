package example.domain.strategy;

@FunctionalInterface
public interface DiceRollStrategy {

    int roll(int min, int max);
}
