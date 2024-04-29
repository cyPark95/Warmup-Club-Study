package example;

public class Member {

    @Prefix("prefix_")
    private final String name;

    private final int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "example.Member{" + "name='" + name  + ", age=" + age + '}';
    }
}
