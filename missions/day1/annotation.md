# 어노테이션(Annotation)

강의에서는 어노테이션은 "다양한 역할을 하지만, 마법같은 일을 자동으로 해준다."고 언급했다.<br>
`@SpringBootApplication` 어노테이션의 경우 스프링을 실행시키기 위한 설정들을 자동적으로 해주는 것 또한 마법같은 일이다.

## 어노테이션이란?

> [By Wiki](https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_%EC%95%A0%EB%84%88%ED%85%8C%EC%9D%B4%EC%85%98)
>
> 자바 애너테이션(Java Annotation)은 자바 소스 코드에 추가하여 사용할 수 있는 메타데이터의 일종이다.
> 보통 @ 기호를 앞에 붙여서 사용한다.
> JDK 1.5 버전 이상에서 사용 가능하다.
> 자바 애너테이션은 클래스 파일에 임베디드되어 컴파일러에 의해 생성된 후 자바 가상머신에 포함되어 작동한다.

쉽게말해 주석과 같이 코드에 대한 정보(데이터)를 제공한다.
하지만 주석과 달리 어노테이션은 프로그램의 동작에 영향을 줄 수 있다.

## 어노테이션의 종류

### 표준 어노테이션

#### 자바 언어에서 이미 정의된 어노테이션

- `@Override`
    - 상위 클래스의 메소드를 오버라이드된 메소드임을 나타낸다.
    - 상위 클래스에 해당 메서드가 없다면 에러를 발생시키기 때문에 실수를 줄일 수 있다.


- `@Deprecated`
    - 더 이상 권장되지 않는 대상을 나타낸다.


- `@SuppressWarnings`
    - 컴파일러의 경고를 나타내지 않는다.


- `@SafeVarargs`
    - 가변 인수 메서드에서 타입 안전성 경고를 나타내지 않는다.


- `@FunctionalInterface`
    - 함수형 인터페이스임을 나타낸다.


- `@Native`
    - 네이티브 메서드임을 나타낸다.

### 메타 어노테이션

#### 어노테이션을 정의할 때 사용되는 어노테이션

- `@Retention`
    - 주석이 유지되는 기간을 나타낸다.
    - 정책은 기본적으로 `RetentionPolicy`로 설정
        - `RetentionPolicy.SOURCE`: 컴파일 전까지만 유효 - `RetentionPolicy.CLASS`: 컴파일러가 클래스를 참조할 때까지 유효
        - `RetentionPolicy.RUNTIME`: 컴파일 이후에도 JVM에 의해 실행 시점에 계속 참조


- `@Documented`
    - 주석이 javadoc과 같은 문서화 도구에 포함되어야 함을 나타낸다.


- `@Target`
    - 어노테이션이 적용될 수 있는 대상을 지정한다.
    - 적용될 수 있는 대상의 유형은 `ElementType`로 선언
        - `ElementType.PACKAGE`: 패키지
        - `ElementType.TYPE`: 타입
        - `ElementType.ANNOTATION_TYPE`: 어노테이션 타입
        - `ElementType.CONSTRUCTOR`: 생성자
        - `ElementType.METHOD`: 메서드
        - `ElementType.FIELD`: 기본형 변수
        - `ElementType.TYPE_USE`: 참조형 변수
        - `ElementType.LOCAL_VARIABLE`: 지역 변수
        - `ElementType.PARAMETER`: 기본형 전달인자
        - `ElementType.TYPE_PARAMETER`: 참조형 전달인자


- `@Inherited`
    - 자동으로 상속됨을 나타낸다.


- `@Repeatable`
    - 동일한 요소에 여러 번 사용될 수 있는 어노테이션을 정의한다.

## 어노테이션을 사용하는 이유 (효과) 는 무엇일까?

어노테이션은 코드에 메타데이터를 추가함으로써 컴파일러, 프레임워크, 런타임 시스템 등에게 정보를 제공함으로써 얻을 수 있는 이점은 다음과 같다.

1. 코드의 가독성 향상: 어노테이션은 코드에 메타데이터를 추가하여 코드의 의도를 명확하게 표현할 수 있다.
    - `@Override` 어노테이션은 해당 메서드가 상위 클래스의 메서드를 재정의한다는 것을 명시적으로 나타낸다.
    - `@Deprecated` 어노테이션은 해당 요소가 더 이상 권장되지 않음을 나타낸다.


2. 컴파일 타임 체크: 컴파일 시점에 코드를 검사하고 경고 또는 오류를 발생시킬 수 있다.
    - `@Override` 어노테이션은 메서드가 상위 클래스의 메서드를 오버라이드 하는지 확인하여 오바리이딩 오류를 방지한다.


3. 런타임 처리: 어노테이션은 런타임에 리플렉션을 사용하여 클래스 및 메서드의 정보를 변경할 수 있다.
    - Spring 프레임워크에서는 `@Transactional` 어노테이션을 통해 트랜잭션을 관리한다.
    - Spring 프레임워크에서는 `@Autowired` 어노테이션은 의존성 주입을 자동화하는 데 사용된다.


4. 코드 생성 및 처리: 어노테이션 프로세서(Annotation Processor)를 사용하여 추가적인 코드를 생성하거나 특정 작업을 수행할 수 있다.
    - Lombok 라이브러리는 `@Getter`, `@Setter` 어노테이션을 사용하면 해당 필드의 Getter와 Setter 메서드를 자동으로 생성해 준다.

## 나만의 어노테이션은 어떻게 만들 수 있을까?

어노테이션을 생성하고, 어노테이션이 붙은 문자열 필드의 앞에 어노테이션 값을 앞에 붙이는 기능을 만들어본다.

1. 어노테이션 정의
    - 문자열 필드의 값 앞에 접두사를 붙이는 `Prefix` 어노테이션을 정의한다.
    ```java
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Prefix {
    
      String value();
    }
    ```
    - 특정 문자열(Field)을 대상으로 하기 위해 `@Target`은 `ElementType.FIELD`로 설정한다.
    - 접두사를 붙이는 행위는 런타임에 발생하기 때문에 `@Retention` 설정은 `RetentionPolicy.RUNTIME`으로 적용한다.


2. 어노테이션 프로세서 정의
    - 런타임에 어노테이션 값으로 접두사를 추가하는 처리 로직을 구현한다.
    ```java
    import example.Prefix;
    
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
    ```
    - 런타임에 리플렉션을 활용하여 `@Prefix` 어노테이션이 붙은 문자열 필드에 접두어를 추가한다.


3. 어노테이션 적용 및 테스트
    ```java
    import example.Prefix;
    
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
            return "example.Member{" + "name='" + name + ", age=" + age + '}';
        }
    }
    ```
   
    - `Member` 클래스의 `name` 필드 앞에 `prefix_` 값이 접두어로 붙도록 어노테이션을 적용한다.

    ```java
    import example.AnnotationProcessor;
    import example.Member;
    
    public class Main {
    
        public static void main(String[] args) {
            Member member = new Member("Name", 20);
            System.out.println("Before: " + member);
    
            AnnotationProcessor.process(member);
            System.out.println("After: " + member);
        }
    }
    ```
    - 출력 결과를 통해 어노테이션 프로세서 처리 후 `prefix_` 접두어가 추가된 걸 확인할 수 있다.
    ```markdown
    << 출력 결과 >>
    Before: Member{name='Name', age=20}
    After: Member{name='prefix_Name', age=20}
    ```
