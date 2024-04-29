# 어노테이션(Annotation)

강의에서는 어노테이션은 "다양한 역할을 하지만, 마법같은 일을 자동으로 해준다."고 언급했다.
`@SpringBootApplication` 어노테이션의 경우 스프링을 실행시키기 위한 설정들을 자동적으로 해주는 것 또한 마법같은 일이다.

그래서 어노테이션의 정의와 역할, 어떻게 만들고 사용할 수 있는지 정리한다.

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

- 자바 언어에서 이미 정의된 어노테이션


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

- 어노테이션을 정의할 때 사용되는 어노테이션


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
