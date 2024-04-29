package example;

import java.lang.reflect.Field;

public class AnnotationProcessor {

    public static void process(Object o) {
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isSupported(field)) {
                try {
                    field.setAccessible(true);
                    String name = (String) field.get(o);

                    Prefix annotation = field.getAnnotation(Prefix.class);
                    String prefix = annotation.value();

                    field.set(o, prefix + name);
                } catch (IllegalAccessException e) {
                    System.err.println("Failed to access field: " + e.getMessage());
                }
            }
        }
    }

    private static boolean isSupported(Field field) {
        return field.isAnnotationPresent(Prefix.class) && field.getType() == String.class;
    }
}
