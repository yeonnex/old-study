
## 개요
이 프로젝트는 아래의 스프링 기술을 사용하여 Self-Descriptive Message 와 HATEOAS(Hypermedia As The Engine Of Application Status)
를 만족하는 REST API 를 구축합니다.
- Spring Boot
- Spring Data JPA
- Spring Security OAuth2
- Spring HATEOAS
- Spring REST Docs

`Event`라는 도메인을 중심으로, 이벤트 등록, 조회, 수정 API 를 RESTful 하게 구현합니다.

> REST?
- HTTP 프로토콜이 기반
- 경량 데이터 포맷인 JSON 포맷 이용
  - 서버의 리소스의 상태를 표시하기에 최적화
- 마이크로 서비스간의 통신 방법으로 권장됨

## 목표
- Self-Descriptive Message 와 HATEOAS 를 만족하는 REST API 개발
- 스프링 HATEOAS 와 스프링 REST Docs 프로젝트 활용
- 테스트 주도 개발에 익숙해지기

## 용어 정리
  REST API : REST 아키텍쳐 스타일을 따르는 API

  REST 아키텍쳐 스타일 

- Client-Server
- Stateless
- Cache
- **Uniform Interface** (**self-descriptive messages, hateoas**...)
- ...

### Self-Descriptive Message
- 메시지 스스로 메시지에 대한 설명이 가능해야 한다
#### 해결방법
- 방법 1. 미디어 타입을 정의하고 IANA 에 등록한 뒤 그 미디어 타입을 리소스 리턴할 때 Content-Type 으로 사용한다.
- **방법 2. profile 링크를 추가한다** (본 프로젝트에서는 이 방법을 채택)
  - 브라우저들이 헤더에 링크 추가하는 스팩을 아직 지원을 잘 하지 않아
  - 대안으로 [HAL](https://stateless.group/hal_specification.html) 의 링크 데이터에 profile 링크를 추가하였음
### HATEOAS
- 하이퍼미디어(링크)를 통해 애플리케이션 상태변화가 가능해야 한다
- 링크 정보를 동적으로 바꿀 수 있어야 한다(**versioning 할 필요 없이!**)
#### 해결방법
- **방법1. 데이터에 링크 제공** (본 프로젝트에서는 이 방법을 채택. 링크 정의 방식은 HAL)
- 방법2. 링크 헤더나 Location 제공

