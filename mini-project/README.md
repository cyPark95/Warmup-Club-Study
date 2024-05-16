# MINI PROJECT

간단한 출퇴근 사내 시스템

### Stack

- Java 17 버전
- Spring Boot 3.xx 버전
- JPA
- MySQL

<details>
<summary>프로젝트 1단계</summary>

- 팀 등록 기능
    - 회사에 있는 팀을 등록할 수 있어야 한다.
    - 팀의 필수 정보
        - `팀 이름`

- 직원 등록 기능
    - 직원을 등록할 수 있어야 한다.
    - 직원의 필수 정보
        - `직원 이름`
        - `팀의 매니저인지 매니저가 아닌지 여부`
        - `회사에 들어온 일자`
        - `생일`

- 팀 조회 기능
    - 모든 팀의 정보를 한번에 조회할 수 있어야 한다.

```json
{
  "teams": [
    {
      "name": "팀 이름",
      "manager": "팀 매니저 이름",
      "memberCount": "팀 인원 수"
    }
  ]
}
```

- 직원 조회 기능
    - 모든 직원의 정보를 한 번에 조회할 수 있어야 한다.

```json
{
  "employees": [
    {
      "name": "직원 이름",
      "teamName": "소속 팀 이름",
      "role": "MANAGER or MEMBER",
      "birthDate": "1995-10-07",
      "joinDate": "2024-04-26"
    }
  ]
}
```

</details>
