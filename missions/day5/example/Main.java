package example;

public class Main {

    private static final int DICE_MIN_NUMBER = 1;
    private static final int DICE_MAX_NUMBER = 6;

    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame(
                DICE_MIN_NUMBER,
                DICE_MAX_NUMBER,
                Main::getNumberInRange
        );
        diceGame.start();
    }

    private static int getNumberInRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
