# MSA Messenger

* MatterMost 메신저 알림 관리 및 이력


## TODO List
- 로그인 페이지 - thymeleaf - 민선+주영
- 문의하기 글 작성하면 - 연습 - Q&A 문의 글 작성 + 첨부파일 ( 첨부파일 - 민선+재광)
- 화면 디자인 꾸미기 - 민선+유진
- 대시보드 차트 - 민선+유진

- 메신저 관련 - 주영


## 참고 url 
- custom login page
https://dkyou.tistory.com/22

- thymeleaf
https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=bgpoilkj&logNo=221982228705&parentCategoryNo=20&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView



## 사용페이지

* 메신저 사용 등록 페이지 
* 메신저 호출 이력 페이지



## 개발환경
* vscode 에서 lombok 플러그인 설치
* Need to set the following in settings.json in VS Code
- debug.focusWindowOnBreak: false




# db 백업 방법

D:\Program Files\PostgreSQL\9.6\bin\pg_dump.exe --file "d:\\pims-mas.backup" --host "211.170.25.109" --port "8432" --username "hipms" --no-password --verbose --format=t --blobs "PIMS_MSA"

# db 복원 방법
- db create
D:\Program Files\PostgreSQL\9.6\bin\pg_restore.exe --host "devops-tools.pmsplus.co.kr" --port "9432" --username "hipms" --no-password --dbname "PIMS-MSA-MESSENGER-DEV" --verbose "D:\\pims-mas.backup"


# SQL 참조
- db create

CREATE DATABASE "PIMS-MSA-MESSENGER-DEV"
    WITH 
    OWNER = hipms
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

- db명 변경
ALTER DATABASE "PIMS-MSA-MESSENGER-BEFORE" RENAME TO "PIMS-MSA-MESSENGER-AFTER";
