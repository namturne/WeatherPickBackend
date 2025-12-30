# 🌤️ WeatherPick

<div align="center">

**장소 추천 및 리뷰 플랫폼**

[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-6DB33F?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql)](https://www.mysql.com/)

</div>

## 📖 프로젝트 소개

사용자가 직접 위치를 등록하여 글을 작성하고 지도에서 위치의 순서대로 경로를 제공하여 장소에 대한 리뷰를 공유할 수 있는 서비스입니다.

### 아키텍처
![Architecture](img/Architecture.png)

### 기술 스택
![Tech Stack](img/stack.png)

## 🖼️ 서비스 화면
<div align="center">

### 네비게이션
![Navigation](img/Nevi.png)

### 장소 검색
![Place Search](img/PlaceSharch.png)

### 리뷰
![Review](img/Review.png)

</div>

---

[//]: # (서비스 설명)
## 🔗 Service

<div align="center">

|        Service        | Description                                                                      |
|:---------------------:|:---------------------------------------------------------------------------------|
|      🔒 **Auth**      | JWT 기반 인증·인가 및 사용자 인증 처리                                             |
|     👤 **User**       | 사용자 회원가입, 로그인, 사용자 정보 조회 및 수정 등 **사용자 관리**                       |
|     🌤️ **TMAP**    | TMAP API 연동을 통한 경로 정보 제공                                          |
|     📝 **Review**     | 장소별 리뷰 게시글 작성, 수정, 삭제, 좋아요, 스크랩 및 댓글 관리                          |
|     📍 **Place**      | 네이버 장소 검색 API 연동을 통한 장소 정보 조회 및 관리                                   |
|     📁 **File**       | 이미지 파일 업로드 및 AWS S3를 통한 파일 관리                                        |

</div>





[//]: # (아키텍처 - 수정 중)




[//]: # (사용 기술 - 수정 중)
## 🛠️ Tech Stack

### 💻 Develop

<div>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">
</div>

### 🚀 Infra & CI/CD

<div>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/Git%20Flow-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>
