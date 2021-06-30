# MVC와 MVVM
![차이점](https://blog.yena.io/assets/post-img19/190316-mvc-mvvm.png)

## MVC란?
- 액티비티 == 컨트롤러
- 뷰와 연결되어 유저와 상호작용도 하고, 모델과 연결되어 데이터도 처리
- 즉 뷰와 모델 사이에서 중재자 역할
- EX) MVC에서는 컨트롤러가 유저의 클릭 액션을 확인하고, 모델에 데이터를 갱신하도록 요청하고, 뷰에도 화면을 업데이트 하라고 요청을 해야한다. 코딩하다 무언가 빠뜨리면, 때로는 DB만 갱신되고 화면은 갱신되지 않는 경우도 나타난다. 액티비티가 해야 할 역할이 많아 바쁘다.


## MVVM이란?
- MVVM에서는 뷰에서 뷰모델로, 뷰모델에서 모델로 작업을 처리하며, 뷰에서 모델을 직접 참조하지 않는다
- 대신 뷰에서 뷰모델을 관찰하며 데이터의 변경 사항을 감지한다.

1. 뷰가 데이터를 실시간으로 관찰!
- LiveData, 즉 Observable 패턴을 이용하기 때문에 데이터베이스를 관찰하고 자동으로 UI를 갱신
- 직접 뷰를 바꾸어주는 번거로움도 없으며 데이터와 불일치할 확률이 줄어든다.

2. 생명주기로부터 안전!
- 메모리 릭 방지! 뷰모델을 통해 데이터를 참조하기 때문에 액티비티/프래그먼트의 생명주기를 따르지 않는다. 화면전환과 같이 액티비티가 파괴된 후 재구성 되어도 뷰모델이 데이터를 홀드하고 있기 때문에 영향을 받지 않는다. 또한 뷰가 활성화되어있을 경우에만 작동하기 때문에 불필요한 메모리 사용을 줄일 수 있다.

3. 역할 분리! 모듈화! 
- UI, 비즈니스 로직, 데이터베이스가 기능별로 모듈화 되어있어서 역할 별로 정리가 ★☆ 깔-끔 ☆★



![구조](https://blog.yena.io/assets/post-img19/190316-mvvm-detail.png)


# mvvm 예제
![예제 구조](https://blog.yena.io/assets/post-img19/190327-02-class-list.png)

### 뷰 모델
뷰모델은 UI를 위한 데이터를 가지고 있으며, 구성(configuration)이 변경되어도 살아남는다. (예를 들어 화면 회전이라던가, 언어 변경 등)
AsyncTask는 액티비티나 프래그먼트의 생명 주기에서 자유로울 수 없지만, 뷰모델은 뷰와 분리되어 있기 때문에 액티비티가 Destroy 되었다가 다시 Create 되어도 종료되지 않고 데이터를 여전히 가지고 있다.



### LiveData - 라이브데이터
관찰이 가능한(Observable) 데이터 홀더 클래스이다.
뷰에서 뷰모델의 라이브데이터를 관찰하게 되면 데이터가 변경될 때 자동으로 알려줌.
액티비티나 프래그먼트의 생명 주기를 인지한다. 즉, 액티비티가 화면 위에 활성화되어 있을 때에만 UI변경 등의 기능을 동작하게 되고, Destroy 된 상태에서는 동작하기 않기 때문에 메모리 릭의 발생을 줄여준다.

### Repository - 리포지토리
뷰모델과 상호작용하기 위해 잘 정리된(Clean) 데이터 API를 들고 있는 클래스이다.
앱에 필요한 데이터, 즉 내장 데이터베이스나 외부 웹 서버 등에서 데이터를 가져온다.
따라서 뷰모델은 DB나 서버에 직접 접근하지 않고, 리포지토리에 접근하는 것으로 앱의 데이터를 관리한다.

### Room - 룸
SQLite 데이터베이스를 편하게 사용하게 해주는 라이브러리이다. SQLite의 코드를 직접 작성하는 경우, 직접 테이블을 Create 하거나 쿼리문을 일일이 변수를 통해 작성해주어야 했지만, Room을 쓰면 조금 더 직관적이고 편리하게 DB를 사용 가능

<pre><code> Entitly</code></pre>
<pre><code> DAO</code></pre> 


1. Dependency 추가
2. Room 생성 (Entity, DAO, Database)
3. Repository 생성
4. ViewModel 생성
5. MainActivity 설정
6. RecyclerView 설정 (xml, Adapter)
7. AddActivity 생성



### ROOM 생성 (1) Entitiy 생성
#### sql을 편하게 사용해주는 라이브러리,이를 통해 편하게 db 관리 가능 

#### Entitiy란?
- 실체, 객체. 업무에 필요하고 유용한 정보를 저장, 관리하기 위한 집합
- 정보를 저장할 수 있는 어떤 것, 장소 물건, 사람 등의 명사
ex) 학생이란 엔티티 == 학번, 이름, 학점, 등록일자, 생일, 전공 등의 속성

<pre><code>
// romm- Contact.kt
package com.example.mvvm_1.rom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
//형식을 위해 나타내줌. () 안의 내용 생략 가능

data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
//말그대로 아이디
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "number")
    var number: String,

    @ColumnInfo(name = "initial")
    var initial: String
)
{

    constructor() : this(null, "","","")
    // primary constructor가 있는 경우
    //secondary constructor는 this()생성자를 통해
    // 직간접적으로 primarty constructor에 위임
    // 보조생성자가 this 키워드를 사용하여 기본 생성자 호출
   // dlqfurrkqt 없이 초기화하는 생성자 실행 
   // {println("${this.name} 보조생성사 실행") } 
   
}

</code></pre>
