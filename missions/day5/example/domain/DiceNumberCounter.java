package example.domain;

import java.util.HashMap;
import java.util.Map;

public class DiceNumberCounter {

    private final Map<Integer, Integer> counts = new HashMap<>();

    public void increaseCount(int count) {
        counts.put(count, counts.getOrDefault(count, 0) + 1);
    }

    public int getCount(int count) {
        return counts.getOrDefault(count, 0);
    }
}
