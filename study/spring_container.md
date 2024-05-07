# 스프링 컨테이너(Spring Container)

Java에서 스태틱이 아닌 코드를 사용하려면 **인스턴스화**가 필요하다. <br>
하지만 스프링에서는 스프링 컨테이너를 통해 클래스를 관리한다.

## 스프링 컨테이너

- 서버가 시작될 때, 함께 시작되는 클래스들을 담는 거대한 공간
- 스프링 빈을 등록하고 관리하는 역할을 한다.
- 이를 통해 객체의 생성, 의존성 관리, 라이프사이클 관리 등을 처리한다.
- `build.gradle` 파일에 설정한 의존성들도 함께 스프링 빈으로 등록된다.
- 스프링 빈을 등록하는 과정에서 필요한 의존성을 함께 설정해준다.

> 의존성(Dependency)
> - 하나의 요소가 다른 요소에게 영향을 받는 관계
> - 소프트웨어의 유연성과 유지보수성에 영향을 준다.

## 스프링 빈(Spring Bean)

- 컨테이너 안에 다양한 클래스가 들어가게 된다.
- 이때 다양한 정보도 함께 들어있고, 인스턴스화도 이루어진다.
- 스프링 컨테이너 안으로 들어간 클래스를 **스프링 빈**이라고 한다.