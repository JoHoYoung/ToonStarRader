# 멘티분들 이슈 확인 부탁드려요~!(asap)
https://github.com/NAVER-CAMPUS-HACKDAY/toon-star-rader/issues/1

# toon-star-rader
업로드 된 웹툰의 별점 테러 모니터링 시스템

## 목적
작품별, 일자별 별점 태러가 발생하는 시간과 별점 태러를 진행한 유저정보를 실시간으로 모니터링 하는것

## 요구사항 
1. rest api 개발 
	- 특정 컨텐츠의 회차에 별점을 추가하는 api
2. kafka Producer 개발 
	- 큐에 메시지(사용자의 컨텐츠별 회차의 별점 정보) 메시지 삽입
3. kafka Consumer 개발 
	- 큐에서 읽은 데이터 DB 삽입 
4. 별점 평균 타임라인(area chart) 조회 화면 개발 (예시 https://naver.github.io/billboard.js/demo/#Chart.AreaRangeChart)
	- 조회조건: 컨텐츠(default), 회차(optional), 기간(optional)(기본 2019/11/10/22 ~ 2019/11/11/22)

## 기술스택 
- Java
- Spring boot 2.x
- Message Queue (kafka) 
- DB(Mysql, mongodb, redis) 
- 간단한 FrontEnd (graph 표현)

