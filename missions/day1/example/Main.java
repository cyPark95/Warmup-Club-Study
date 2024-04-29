package example;

public class Main {

    public static void main(String[] args) {
        Member member = new Member("Name", 20);
        System.out.println("Before: " + member);

        AnnotationProcessor.process(member);
        System.out.println("After: " + member);
    }
}
