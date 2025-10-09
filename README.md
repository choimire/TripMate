## ✈️TRIPMATE🏖️
여행지를 입력하면 자동으로 동선까지 고려한 일정 생성 웹사이트

“가고 싶은 곳을 골라주면 척척 일정표 완성!🗓️"

## 🎯 프로젝트 목적

“여행 코스 짜기 귀찮다!”라는 생각에서 시작된 프로젝트입니다.

원하는 여행지를 추가하면 숙소와 교통수단까지 고려해 자동으로 일정과 동선을 만들어주는 개인 프로젝트입니다.

## ✨ 주요 기능

1. 교통수단 & 숙소 설정

   - 여행 첫날: 교통수단 → 숙소 → 일정

   - 여행 마지막 날: 일정 → 숙소 → 교통수단

   - 나머지 일정: 항상 숙소를 기준으로 동선 생성

2. 동선 최적화

   - 구글맵 API를 활용해 같은 날에 근처 여행지끼리 일정 자동 배치
  
3. Google Maps 지도에 경로 표시
   
4. 일정별 여행지 마커 및 애니메이션


## 🖥️화면 구성
|메인화면|지도 및 일정표|
|--------|--------|
|<img width="400" height="200" alt="Image" src="https://github.com/user-attachments/assets/421daa1d-fd1a-4c4d-9086-b0f224e28e5f" />|<img width="400" height="200" alt="Image" src="https://github.com/user-attachments/assets/018a72b2-0479-4a09-b91e-271013ddae04" />|
---
반응형
|메인화면|지도|일정표|
|------|------|------|
|<img width="200" height="400" alt="Image" src="https://github.com/user-attachments/assets/4ffd4fea-3156-41a7-8d7a-10a1640eac6d" /> |<img width="200" height="400" alt="Image" src="https://github.com/user-attachments/assets/f6f10d0c-3a41-4e38-9ddb-a2870f009f06" /> |<img width="200" height="400" alt="Image" src="https://github.com/user-attachments/assets/956e8ca5-169c-4849-9529-38c9346a9594" />|
---
## 🎨기술스택
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![npm](https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)

## 💻개발환경

|항목|버전|
|------|------|
|운영체제|	macOS 26.0.1 (aarch64)|
|IDE / 에디터|	VS Code|
|Node.js|	v23.11.0|
|npm|	10.9.2|
|Java|	OpenJDK 17.0.15|
|Gradle|	8.14.3|
|Spring Boot|	3.5.5|
|브라우저|	Chrome|
|기타 도구|	Git, AWS,ChatGPT|

## 🚀 실행 방법 (How to Run)

### 1️⃣ 환경 변수 설정  
프로젝트 실행 전, 각 폴더(`frontend`, `backend`)에 `.env` 파일을 생성해 주세요.  

> 예시:
> ```bash
> # frontend/.env
> REACT_APP_GOOGLE_MAPS_API_KEY=YOUR_API_KEY
>
> # backend/.env
> DB_URL=jdbc:mysql://localhost:3306/tripmate
> DB_USERNAME=YOUR_DB_USERNAME
> DB_PASSWORD=YOUR_DB_PASSWORD
> ```

---

### 2️⃣ Frontend 실행  
```bash
cd frontend
npm install
npm start
```
> ✅ 실행 후: http://localhost:3000 접속
---
### 3️⃣ backend 실행
```bash
cd backend
./gradlew bootRun
```
> ✅ 실행 후: http://localhost:8080 에서 서버API 동작 확인
---

## 🚨 Troubleshooting

### 1. React에서 주소가 `[object Object]`로 표시됨
- **원인:** 프론트에서 주소 필드를 객체 형태로 잘못 처리
- **해결:** 
  - `MapComponent`와 `TravelInputForm`에서 주소를 문자열(`formatted_address`)로 가져오도록 수정
  - 예: `selectedPlace.address` 사용

---

### 2. 구글 지도 마커 경고
- **내용:** `google.maps.Marker`가 deprecated
- **원인:** 최신 Google Maps JS SDK 변경
- **해결:** 
  - 당장은 기존 Marker 사용 가능
  - 향후 `AdvancedMarkerElement`로 마이그레이션 예정
  - 참고: [Google Maps Advanced Marker Migration](https://developers.google.com/maps/documentation/javascript/advanced-markers/migration)

---

### 3. 최적화 동선 생성 오류
- **내용:** `axios.fetch is not a function`
- **원인:** `axios.get` 대신 잘못된 메서드 호출
- **해결:** 
```js
  const res = await axios.get(url);
  const data = res.data;
```
---

### 4. **AWS EC2 SSH 연결 문제**
   - **증상:** SSH로 EC2 접속 불가
   - **원인:** 보안 그룹에서 포트 22 미허용, 탄력적 IP 미연결, SSH 키 권한 문제
   - **해결 방법:** 
     - EC2 보안 그룹에서 인바운드 규칙에 포트 22 허용
     - 탄력적 IP를 EC2 인스턴스에 연결
     - SSH 키 파일 권한(`chmod 400 key.pem`) 확인

---

### 5. EC2 재부팅 후 서버 자동 실행 문제
- **증상:** EC2 재시작 시 백엔드 서버가 종료되어 API 호출 불가
- **원인:** 수동 실행(`nohup`)만 사용 → 인스턴스 재시작 시 프로세스 종료됨
- **해결:**
  - `systemd` 서비스 등록 (`tripmate.service`)
  - 자동 실행 설정  
    ```bash
    sudo systemctl enable tripmate
    sudo systemctl start tripmate
    ```
  - 확인 명령:  
    ```bash
    sudo systemctl status tripmate
    ```

## ☄️개선 예정 기능 (Improvement Plan)

현재 서비스는 기본적인 여행지 추가 및 일정 자동 최적화 기능에 초점을 맞추고 있습니다.  
향후 아래의 기능들을 순차적으로 업데이트할 예정입니다. ✨

### 🧭 여행 계획 관련
- ✅ 여행지 간 **거리 및 시간 계산 정확도 개선**
- ✅ **AI 추천 루트 기능** 추가 (날씨 / 교통량 / 리뷰 기반)
- ✅ 사용자가 직접 **여행지 순서 수정** 가능하도록 기능 개선

### 💾 사용자 편의 기능
- ✅ 여행지 정보에 **이미지 미리보기 썸네일** 추가
- ✅ 다크 모드 지원 🌙

### 💬 커뮤니티 및 공유
- ✅ 여행 일정 **공유 링크 생성 기능**

### 🔒 계정 및 데이터 관리
- ✅ 로그인 / 회원가입 UX 개선 (소셜 로그인 포함)
- ✅ 사용자별 **저장된 여행 일정 관리 페이지** 추가

### ⚙️ 성능 및 안정성
- ✅ 서버 리소스 최적화 및 로딩 속도 개선
- ✅ AWS EC2 + S3 기반 배포 자동화


## 🌐 배포 링크
- **Frontend (S3)**: [http://tripmate-front.s3-website.ap-northeast-2.amazonaws.com](http://tripmate-front.s3-website.ap-northeast-2.amazonaws.com)
- **Backend (EC2)**: `http://54.180.37.41:8080`

## 🕖개발기간
2025.09.13~2025.10.03
