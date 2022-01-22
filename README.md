# CareBank

![image](https://user-images.githubusercontent.com/92344242/150632344-f99dec60-06f0-4d2d-a52d-012b3cf8e959.png)

## 주제
맞춤형 건강관리 사이트

## 기획 의도 및 목표
- 사용자의 종합 건강 관리를 편리하게 돕는 웹 어플리케이션으로, 다양한 API를 활용하여 건강 관리 서비스를 제공한다. 
- 선호도를 기반으로 맞춤 건강정보를 제공하며, 다이어리 및 건강관리 대시보드로 사용자의 건강 상태를 체크한다.

## 개발 기간
2021.12-2021.01

## 기술 스택
### Front-End 
HTML/CSS, javascript, jQuery
### Back-End 
Java (Spring), AWS(S3, RDS)
### Database 
Oracle

---

## 구현 기능
### AWS RDS를 통한 DB 서버 구축
- 기능 구현의 편의성을 위해 Oracle Enterprise Edition 기반의 DB 서버를 구축하여 프로젝트에서 사용하였다.

### 이용하기 - 식단, 영양제, 병원, 약국 정보 제공
- 식단 및 영양제 정보 제공
  - 사이트 자체적으로 구성된 DB에서 식단 및 영양제 정보를 카테고리별로 분류하고 제공한다.
  - 상세보기 페이지를 제공하고 각 정보 게시글에 대하여 댓글을 작성할 수 있다.
  - 영양제 정보에 대해서는 네이버 검색 API와 연계되어 이와 관련된 영양제 상품 정보를 제공한다.
- 병원 및 약국 정보 제공
  - 건강보험심사평가원에서 제공하는 병원정보서비스 및 약국정보서비스 API와 연계하여 지역별로 병원 및 약국 정보를 제공한다.
  - 상세보기 페이지를 제공하고 카카오 지도 API와 연계되어 위치 정보를 확인할 수 있다.

---

## 발표 자료
https://docs.google.com/presentation/d/1ilA0S7D6fzqZCHDzvNPNNySNUXBsmB7b/edit?usp=sharing&ouid=109431752741517625780&rtpof=true&sd=true
