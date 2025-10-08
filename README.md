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
|기타 도구|	Git, AWS|


## 🕖개발기간
2025.09.13~2025.10.03


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

---

### 4. **AWS EC2 SSH 연결 문제**
   - **증상:** SSH로 EC2 접속 불가
   - **원인:** 보안 그룹에서 포트 22 미허용, 탄력적 IP 미연결, SSH 키 권한 문제
   - **해결 방법:** 
     - EC2 보안 그룹에서 인바운드 규칙에 포트 22 허용
     - 탄력적 IP를 EC2 인스턴스에 연결
     - SSH 키 파일 권한(`chmod 400 key.pem`) 확인
     - SSH 연결 명령 예시:  
       ```bash
       ssh -i ~/key/my-key.pem ubuntu@<탄력적 IP>
       ```

---

### 5. **환경 변수(API Key) 문제**
   - **증상:** Google Maps, API 호출 시 인증 오류
   - **원인:** `.env` 파일 미설정 또는 React 환경 변수 미적용
   - **해결 방법:** 
     - 프로젝트 루트에 `.env` 생성  
       ```env
       REACT_APP_GOOGLE_MAPS_API_KEY=google_api_key
       REACT_APP_API_BASE=http://backend-url
       ```
     - React 앱 재시작 후 적용 (`npm start` 또는 `npm run build`)
     - 빌드 배포 시 `process.env.REACT_APP_...` 값 확인

---

### 6. **모바일 Safari 호환성**
   - **증상:** iPhone Safari에서 지도/버튼/레이아웃 깨짐
   - **원인:** 로컬 호스트 환경에서는 모바일에서 테스트 불가, 일부 CSS/JS 호환 문제
   - **해결 방법:** 
     - 프론트 빌드 후 S3 + CloudFront로 배포
     - 모바일 Safari에서 접속 테스트
     - 필요 시 Tailwind responsive 클래스와 meta viewport 재검토
