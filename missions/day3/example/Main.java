import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        List<Fruit> fruits = List.of(
                new Fruit("사과", 900),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 500),
                new Fruit("수박", 5_000)
        );

        filterFruit(fruits, Main::condition);
    }

    private static boolean condition(Fruit fruit) {
        return fruit.getPrice() >= 1_000;
    }

    private static List<Fruit> filterFruit(List<Fruit> fruits, Predicate<Fruit> condition) {
        return fruits.stream()
                .filter(condition)
                .toList();
    }
}
