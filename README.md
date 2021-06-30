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

### View Model 뷰 모델
뷰모델은 UI를 위한 데이터를 가지고 있으며, 구성(configuration)이 변경되어도 살아남는다.
(예를 들어 화면 회전이라던가, 언어 변경 등)
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


### ROOM 생성 (2) SQL을 작성하기 위한 DAO 인터페이스 만들기

<pre><code>
//ContactDao.kt

@Dao

interface ContactDao {
    annotation class Dao
    
    // 전체 연락처 리스트 반환 함수
    // LiveData 반환
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll() : LiveData<List<Contact>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // onConflict 속성
    // 중복된 데이터의 경우 어떻게 처리할지 지정
    //onConflictStrategt인터페이스 호출 ->
    // REPLACE, INGNORE, APORT, FAIL, ROLLBACK 등 지정 가능 
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}


</code></pre>


### ROOM 생성 (3) 실질적인 데이터베이스 인스턴스를 생성할 Database 클래스
- RoomDatabase 클래스를 상속하는 추상 클래스로 생성 


#### 인스턴스(Instance)란?
메모리에 올라간 객체

#### 추상 클래스 (Abstract Class)
아직 구현되지 않고 선언만 된 추상메서드를 가지고 있는 클래스
- 메서드가 구현되지 않아서 이 클래스를 직접 객체로 만들 수 없음
- 반드시 상속을 받는 자식클래스 (SubClass) 가 필요
- 상속을 통해 생성될 자식클래스에서 메서드 오버라이딩에 강제성을 부여하기 위해 사용

#### RoomDatabase에서 추상 클래스를 사용하는 이유
를 모르겠어 


<pre><code>
// ContactDatabase.kt

// @Database 어노테이션을 이용해
// entity를 정의
// Contact class가 entity다! 
// SQLite 버전을 지정
@Database(entities = [Contact::class],version = 1)
//변경된적이 없고, 처음이라 1임
** // 그럼 나중에 변경해야하는건가? **

//RoomDatabase()를 상속 
abstract class ContactDatabase : RoomDatabase() {

// 미리 만들어놓은 ContactsDao를 접근하게
//abstract fun 이용
// ContactsDao() 만들기

    abstract fun contactDao() : ContactDao

//어디서든 접근가능
//중복 생성되지 않게
//싱글톤으로 companion object 에 만들기


    companion object{
    

     
        private var INSTANCE : ContactDatabase? = null
        @InternalCoroutinesApi
        
        // 여러 스레드가 접근 못하게
        // synchronized로 설정 
        fun getInstance(context : Context) : ContactDatabase? {

            if(INSTANCE == null){

                synchronized(ContactDatabase::class){
                //인스턴스 생성 
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactDatabase::class.java, "contact")
                        // 데이터베이스가 갱신될 때
                        //기존의 테이블을 버리고 새로 사용
                        .fallbackToDestructiveMigration()
                        .build()

// 이렇게 만들어지는 DB인스턴스는
// Repository에서 호출, 사용 

                }
            }
            return INSTANCE

        }
    }
}


</code></pre>

### ROOM 생성 (4) Repository - 위 과정에서 만든 DB 인스턴스를 Repository에서 호출해서 사용하기 

<pre><code>

@InternalCoroutinesApi
class ContactRepository(application: Application) {

    @InternalCoroutinesApi
    // Database, Dao, contacts 각각 초기화 
    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao : ContactDao = contactDatabase.contactDao()
    private val contacts : LiveData<List<Contact>> = contactDao.getAll()



// ViewModel에서 DB에 접근을 요청할 때 수행할 함수

// 메인 스레드에서 Room DB에 접근하면 크래쉬 발생
//별도의 스레드로 Room의 데이터에 접근

    fun getAll() : LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact){
        try {
            val thread = Thread(Runnable {

                contactDao.insert(contact) })
            thread.start()
        } catch (e: Exception) {}

    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e : Exception) { }
    }

}

</code></pre>



### View Model 생성
<pre><code>
ContactViewModel.kt
//AndroidViewModel를 상속받는 ContactViewModel

class ContactViewModel(application: Application) : AndroidViewModel(application) {

//Repository : 뷰모델과 상호작용하기 위해 정리된 데이터 API를 들고 있는 클래스
// 뷰모델은 Repository와 상호작용해야함!!!!! 
//뷰모델은 DB나 서버에 직접접근하지 않고 Repository에 접근하며 데이터 관리
//그래서 Reopositroy 의 DB 제어 함수 사용 

    @InternalCoroutinesApi
    private val repository = ContactRepository(application)
    @InternalCoroutinesApi
    private val contacts = repository.getAll()

    @InternalCoroutinesApi
    fun getAll() : LiveData<List<Contact>> {
        return this.contacts
    }

    @InternalCoroutinesApi
    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    @InternalCoroutinesApi
    fun delete(contact: Contact) {

        repository.delete(contact)
    }


}

</code></pre>






**질문리스트**
1. // SQLite 버전을 지정
@Database(entities = [Contact::class],version = 1)
//변경된적이 없고, 처음이라 1임
그럼 나중에 변경해야하는건가?

2. RoomDatabase에서 추상 클래스를 사용하는 이유
- RoomDattabase if부분도 자세히 이해가 안감솔직히

3. 이거 서버통신이랑 어떻게 같이해야하냐.... 
