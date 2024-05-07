# Spring Bean

## 등록 방법

- `@Configuration`
    - 클래스에 붙이는 어노테이션
    - `@Bean`을 사용할 때 함께 사용해 주어야 한다.

- `@Bean`
    - 메서드에 붙는 어노테이션
    - 메서드에서 반환되는 객체를 스프링 빈으로 등록한다.

## @Component

- 주어진 클래스를 **컴포넌트**로 간주한다.
- 컴포넌트로 간주된 클래스들은 스프링 서버가 뜰 때 자동으로 감지된다.
- `@RestController`, `@Service`, `@Repository`, `@Configuration` 어노테이션은 모두 `@Component` 어노테이션을 가지고 있다.
    - `@Component` 어노테이션 덕분에 지금까지 모두 자동으로 감지된 것이다.

> - `@Service`, `@Repository`
>   - 개발자가 직접 만든 클래스를 스프링 빈으로 등록할 때 사용한다.
> - `@Configuration` + `@Bean`
>   - 외부 라이브러리, 프레임워크에서 만든 클래스를 등록할 때 사용한다.
> - `@Component`
>   - Controller, Service, Repository 모두 아닌 추가적인 클래스를 스프링 빈으로 등록할 때 사용한다.
