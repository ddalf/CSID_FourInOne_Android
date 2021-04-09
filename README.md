<h1 align="center">만능 검색 갤러리</h1>

## 프로젝트 소개

### 주제 선정 이유

"이미지 내의 텍스트 검색 갤러리"

* 정보화시대에 넘쳐나는 정보 사이에서 사람들은 자신에게 필요한 정보를 수집한다. 스마트폰을 사용하는 일반 스마트폰 사용자들은 스마트폰을 이용하는 과정에서 필요한 정보가 나타나면 스크린 샷을 통해 이미지로 정보를 수집한다.
* 스마트폰에 탑재된 카메라의 기능이 고도화되며 성능이 웬만한 디지털카메라에 필적할 정도로 좋아짐에 따라 스마트폰 사용자들이 관리하는 이미지의 양 또한 방대해지고 있다. 따라서, 이미지에 존재하는 정보를 쉽게 수집하고 전달 가능하게끔 하는 시스템의 필요성에 대하여 논하게 되었다.
<br>



## 업무 분담 구조

![image](https://user-images.githubusercontent.com/42609000/114146236-4e012d00-9952-11eb-813b-a8dc0c2410a4.png)

만능검색 갤러리의 설계는 크게 고객 요구사항 이해, 기능 요구사항 분석, 상세설계, 설계 프로세스 문서화, 프로젝트 관리 활동, 구현으로 이루어졌습니다. 

프로젝트를 진행 초기 단계에서 고객 요구사항을 이해하기 위해 프로젝트 기술 문서를 명시하고, 요구사항 정의서를 작성하며 목적나무를 개발하였고, 기능 요구사항 분석을 위해 팀원들이 모여 브레인스토밍을 진행하였습니다. 상세설계 단계에서는 시스템구성도, 시스템흐름도, 클래스 다이어그램, 스토리보드를 작성하고 데이터베이스를 설계하였습니다. 이러한 설계 단계에서 프로세스에 대하여 문서화 작업을 진행했습니다. 이후, 구현을 진행하였고, 구현 단계에서 구현 도구를 설정하고, UI/UX 외주 받고 안드로이드, 알고리즘, 데이터베이스를 이용해 구현을 진행하였습니다.
<br>




## 개발 일정

개발기간 : 2020-03 ~ 2020-10

![image](https://user-images.githubusercontent.com/42609000/114143975-c4506000-994f-11eb-9173-111705f71dc1.png)
<br>




### 개발 환경

![image](https://user-images.githubusercontent.com/42609000/114144243-12656380-9950-11eb-8d36-1d77581c11f3.png)
<br>




## 상세 설계

* ![image](https://user-images.githubusercontent.com/42609000/114158216-69bf0000-995f-11eb-9376-4d82c606e94c.png)  

* 시스템 흐름도

  ![image](https://user-images.githubusercontent.com/42609000/114147615-d3d1a800-9953-11eb-8a0a-29b1f9b318db.png)

* 모듈 설계

  ![image](https://user-images.githubusercontent.com/42609000/114158266-77748580-995f-11eb-95ba-67911a4f01fb.png)
<br>


## 구현 화면

<table>
  <tr>
    <th><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145187-1e055a00-9951-11eb-9d3e-dcb40d9953ae.png"></th>
    <th><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145283-32e1ed80-9951-11eb-849c-a06177830795.png"></th>
  </tr>
  <tr>
    <td><b>스플래시 화면</b><br>애플리케이션 구동 시 1초간 등장하는 스플래시 화면</td>
    <td><b>앨범 목록 화면</b><br>스마트폰 내에 존재하는 앨범과 이미지를 동기화하여 보여주는 화</td>
  </tr>
  <tr>
    <td><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145328-3ecdaf80-9951-11eb-9d1d-e3ce9ca39517.png"></td>
    <td><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145371-47be8100-9951-11eb-9bd8-021481f00ea2.png"></td>
  </tr>
  <tr>
    <td><b>이미지 목록 화면</b><br>동기화된 이미지와 이미지에서 추출된 텍스트를 나타내는 화면</td>
    <td><b>검색 화면</b><br>키워드를 검색하면 키워드를 텍스트로 포함하는 이미지를 보여주는 화면</td>
  </tr> 
  <tr>
    <td><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145566-818f8780-9951-11eb-855f-2d1fbfe10579.png"></td>
    <td><img width="1067" src="https://user-images.githubusercontent.com/42609000/114145595-8a805900-9951-11eb-9081-9baea8b5e229.png"></td>
  </tr>
   <tr>
    <td><b>이미지 상세 화면</b><br>검색된 이미지를 누를 시 이미지와 추출된 텍스트를 상세히 보여주는 화면</td>
    <td><b>앨범 숨기기 화면</b><br>원하는 앨범 숨기기 기능을 제공하는 화면</td>
  </tr>      
</table>
<br>




## 예상 결과 및 기대 효과

* 일부 정보만으로도 원하는 이미지를 재탐색할 수 있다는 이점은 정보 탐색의 편의성과 더불어 신속성, 확장성을 제공합니다.
  예를 들어 메신저 대화 중 자막이 있는 영화 장면 캡처가 상황에 잘 맞아 바로 보내고 싶을 때 그 단어만을 검색해 바로 찾아 보낼 수 있습니다. 혹은 예전에 방문한 맛집을 다시 찾고 싶은데 가게 이름의 일부만이 생각난다 해도 그 문구만 검색해서 가게를 찾을 수도 있다. 이후에는 지도나 검색 포털 등을 이용해 정보 활용성을 확장 시킬 수 있으리라 판단합니다.  정보 탐색의 편의성, 신속성, 확장성을 제공하여 수집한 정보 활용의 형태를 확장하는 효과 또한 기대할 수 있습니다.

<br>



## 팀원

**박소희, 신지원, 이혜린**

1주일에 한 번씩 브레인스토밍, 설계 및 구현 과정에서의 의견 공유, 회의 진행 설계 단계에서 충분히 검토후 요구사항 정의서, 시스템구성도, 시스템흐름도, 클래스 다이어그램 등을 함께 작성. 작성한 스토리보드·UI/UX 설계서를 토대로 UI/UX 디자인을 외주업체 통해 진행, 검토와 보완 과정 후 디자인 완성

* 신지원, 박소희: Kotlin 언어를 이용하여 안드로이드 개발. 스플래쉬 화면부터 애플리케이션의 메인화면인 앨범보기 화면, 이미지 화면, 이미지 상세 화면 및 텍스트 복사하기, 앨범 숨기기 화면, 휴지통 화면 및 해당하는 기능 등 구현 
* 박소희: 키워드 기반으로 이미지 검색 시 검색 알고리즘 구현 
* 이혜린: Tesseract-OCR을 이용하여 텍스트를 인식하는 알고리즘을 구현, 데이터베이스를 Realm을 통하여 구현함으로써 데이터 동기화 속도 향상 / 최종적으로, 보고서 작성 함께 진행, 전체 과정 리뷰
<br>




## 성과

* 동국대학교 SW 공모전 은상 수상

* 정보과학회 논문 투고 

  정보 활용성 확장을 위한 Tesseract OCR 기술 이용 방안

  https://www.dbpia.co.kr/Journal/articleDetail?nodeId=NODE09301903

