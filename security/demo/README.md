## cURL 로 HTTP Basic 인증하는 법
1. cURL 옵션 이용
````text
$ curl -u user:73102cc0-fb18-41cf-8b86-92a1cf4d2fc0 http://localhost:8080/hello
````

2. cURL 옵션 이용 X
```text
$ echo -n user:73102cc0-fb18-41cf-8b86-92a1cf4d2fc0 | base64 # 시큐리티 적용된 앱 구동시 자동 생성되는 유저의 이름과 비밀번호 인코딩
$ curl -H "Authorization: Basic dXNlcjo3MzEwMmNjMC1mYjE4LTQxY2YtOGI4Ni05MmExY2Y0ZDJmYzA=" http://localhost:8080/hello
```