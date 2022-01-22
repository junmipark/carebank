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
- 기능 구현의 편의성을 위해 Oracle Enterprise Edition 기반의 DB 서버를 구축하여 프로젝트에서 사용하였다. <br>

### 이용하기 - 식단, 영양제, 병원, 약국 정보 제공
- 식단 및 영양제 정보 제공
  - 사이트 자체적으로 구성된 DB에서 식단 및 영양제 정보를 카테고리별로 분류하고 제공한다.
  - 상세보기 페이지를 제공하고 각 정보 게시글에 대하여 댓글을 작성할 수 있다.
  - 영양제 정보에 대해서는 네이버 검색 API와 연계되어 이와 관련된 영양제 상품 정보를 제공한다. <br>
- 병원 및 약국 정보 제공
  - 건강보험심사평가원에서 제공하는 병원정보서비스 및 약국정보서비스 API와 연계하여 지역별로 병원 및 약국 정보를 제공한다.
  - 상세보기 페이지를 제공하고 카카오 지도 API와 연계되어 위치 정보를 확인할 수 있다. <br>

### 추천받기 - 식단, 영양제, 운동, 명상 정보를 추천
- 식단 및 영양제 정보 추천 기능 제공
   - 회원이 설정한 관심사를 기반으로 식단 및 영양제 정보를 추천한다.
   - 현재 날짜(사용자가 사용하는 시점)를 기준으로 일주일간 댓글이 많이 달린 정보를 내림차순으로 제공한다.
   - 회원이 설정한 관심사에 해당하는 정보를 모아서 해당 페이지에서 확인할 수 있다. <br>

### 마이페이지 > 다이어리 - 날짜별로 사용자 정보를 기록
- 식단 기록 기능
  - 회원이 날짜(기본 설정은 현재 날짜) 및 식사 시간(아침/점심/저녁)과 함께 식단을 기록할 수 있다.
  - 식단 기록 시 사진을 업로드할 수 있으며 Google Teachable Machine API와 연계되어 학습된 정보와 가장 유사한 식단의 이름과 영양소 정보를 제공한다.
  - 인식된 정보가 부정확한 경우 사용자로부터 직접 입력을 받을 수 있다. <br>

### 관리자 페이지 - 사이트 DB 관리
- 식단/영양제/운동/명상 정보 관리
  - 각 기능별로 정보를 조회할 수 있으며, 새로운 정보를 추가하거나 기존의 정보를 수정 또는 삭제할 수 있다.
    - 정보를 추가/수정할 때 사진 정보를 입력하기 위해 링크를 붙여넣기 하거나 직접 업로드할 수 있다.
    - 업로드 된 이미지는 온라인 스토리지에 저장된다. 이미지 서버는 AWS S3 서비스를 이용하여 구축하였다.
  - 검색 기능을 제공하여 입력한 키워드를 포함하는 정보만 조회할 수 있다. <br>
- 회원 정보 관리
  - 사이트에 가입한 회원 정보를 확인할 수 있으며, 검색 기능을 제공한다.
  - 활성화(가입) 상태의 회원을 비활성화(탈퇴)할 수 있으며, 그 반대도 가능하다.
  - 회원 비밀번호 변경 기능을 제공한다. <br>
- 회원 통계
  - 일주일간 웹사이트에서 회원의 활동 내역을 기반으로 통계 정보를 제공한다.
  - 일주일간 회원 활동이 가장 많은 기능을 확인할 수 있으며, 각 기능별로 활발히 활동했던 회원을 확인할 수 있다. <br>

---

## 참고 사진
![image](https://user-images.githubusercontent.com/92344242/150634599-a185ac88-5d69-475b-ab44-7365281bdb0e.png)
![image](https://user-images.githubusercontent.com/92344242/150634625-67a2f268-ad97-4da4-aa2a-3391e4777997.png)
![image](https://user-images.githubusercontent.com/92344242/150634646-8fddaa28-4708-41df-8816-046c77df74c4.png)
![image](https://user-images.githubusercontent.com/92344242/150634662-d4b9fb7b-be7d-48c2-a339-bf7e2d643b41.png)
![image](https://user-images.githubusercontent.com/92344242/150634667-34de4464-c6aa-443a-af4c-e47aa71aafa5.png)
<br>
![image](https://user-images.githubusercontent.com/92344242/150634677-d535f989-487c-4139-8fd0-727696b3e8dd.png)
<br>
![image](https://user-images.githubusercontent.com/92344242/150634689-498bd2c1-139f-4d5a-bf6e-dedc6dbf97e5.png)
![image](https://user-images.githubusercontent.com/92344242/150634749-b6e04d0e-7e75-4734-951f-7a60c3713e8f.png)
<br>
![image](https://user-images.githubusercontent.com/92344242/150634760-5eb1bc56-1b20-4d20-978d-19c27fa007a7.png)
![image](https://user-images.githubusercontent.com/92344242/150634798-3dd5bfc9-576b-4539-885f-3cce93a56a54.png)
![image](https://user-images.githubusercontent.com/92344242/150634768-ba8be0e7-0799-476d-9aef-613ee8a006b7.png)
![image](https://user-images.githubusercontent.com/92344242/150634777-1f488667-c52a-4301-a9eb-219442cde8f9.png)
<br>
![image](https://user-images.githubusercontent.com/92344242/150634873-2b6a61b0-de93-4b30-a6f8-641f79c9d407.png)
개인정보 보호를 위해 일부 정보는 모자이크 처리하였습니다.<br>
<br>
![image](https://user-images.githubusercontent.com/92344242/150634938-c673043e-bca7-4b64-a7fc-fad5573d62e8.png)
<br>

---

## 발표 자료
https://docs.google.com/presentation/d/1ilA0S7D6fzqZCHDzvNPNNySNUXBsmB7b/edit?usp=sharing&ouid=109431752741517625780&rtpof=true&sd=true
