# 한신대학교 캠퍼스 정보 앱 리팩토링 프로젝트
이 프로젝트는 이전에 개발한 [HanshinInfo](https://github.com/HanshinInfo/SummerProject2024) 앱 리팩토링했습니다.
- 개발 기간: 2024.07 - 2024.9  
- 팀원: **[DWinging](https://github.com/DWinging)** (1인 프로젝트)
- 사용 기술: Kotlin, Jetpack Compose, RoomDB
- 주요 기능: 캠퍼스 지도, 학사 일정, 전화번호부, 공식 사이트 접근

## 주요 변경 사항
- 이미지 최신화 : 과거 사진 -> 2025.08 모든 이미지 재촬영
- 학사 일정 UI 변경: 달력 → 리스트로 변경, 한눈에 파악 가능
- 전화번호부 저장 방식: 하드코딩 → 크롤링 + RoomDB, 최신화 가능
- 주변 상권 : 하드 코딩 -> 최신화가 불가능하여 기능 삭제
