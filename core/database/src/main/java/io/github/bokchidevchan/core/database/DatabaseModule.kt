package io.github.bokchidevchan.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.bokchidevchan.core.database.dao.ArticleDao
import io.github.bokchidevchan.core.database.dao.UserDao
import io.github.bokchidevchan.core.database.entity.ArticleEntity
import io.github.bokchidevchan.core.database.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        callback: DatabaseCallback
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase): ArticleDao {
        return database.articleDao()
    }
}

@Singleton
class DatabaseCallback @javax.inject.Inject constructor(
    private val databaseProvider: Provider<AppDatabase>
) : RoomDatabase.Callback() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val database = databaseProvider.get()
        val userDao = database.userDao()
        val articleDao = database.articleDao()

        // Insert dummy users
        val users = listOf(
            UserEntity(
                id = 1,
                name = "김개발",
                email = "dev.kim@example.com",
                avatarUrl = "https://i.pravatar.cc/150?u=1",
                bio = "Android 개발자 | Kotlin 애호가"
            ),
            UserEntity(
                id = 2,
                name = "이코드",
                email = "code.lee@example.com",
                avatarUrl = "https://i.pravatar.cc/150?u=2",
                bio = "풀스택 개발자 | Clean Architecture 전문가"
            ),
            UserEntity(
                id = 3,
                name = "박모듈",
                email = "module.park@example.com",
                avatarUrl = "https://i.pravatar.cc/150?u=3",
                bio = "소프트웨어 아키텍트 | 멀티모듈 설계 전문"
            ),
            UserEntity(
                id = 4,
                name = "최컴포즈",
                email = "compose.choi@example.com",
                avatarUrl = "https://i.pravatar.cc/150?u=4",
                bio = "UI/UX 개발자 | Jetpack Compose 마스터"
            )
        )
        users.forEach { userDao.insertUser(it) }

        // Insert dummy articles
        val articles = listOf(
            ArticleEntity(
                id = 1,
                title = "Jetpack Compose 시작하기",
                content = """
                    Jetpack Compose는 Android의 최신 UI 툴킷입니다.
                    선언적 UI 패러다임을 사용하여 더 적은 코드로 아름다운 UI를 만들 수 있습니다.

                    주요 특징:
                    • 선언적 UI - 상태 기반 렌더링
                    • 코틀린 기반 - 타입 안전한 DSL
                    • 재사용 가능한 컴포넌트
                    • 빠른 개발 속도와 실시간 프리뷰

                    이 글에서는 Compose의 기본 개념부터 실제 앱 개발까지 다룹니다.
                """.trimIndent(),
                authorId = 4,
                authorName = "최컴포즈",
                tags = listOf("Compose", "Android", "UI", "Kotlin"),
                viewCount = 1520,
                likeCount = 89
            ),
            ArticleEntity(
                id = 2,
                title = "Hilt로 의존성 주입 마스터하기",
                content = """
                    Hilt는 Android 앱에서 의존성 주입을 쉽게 구현할 수 있게 해주는 라이브러리입니다.
                    Dagger 위에 구축되어 보일러플레이트 코드를 줄여줍니다.

                    핵심 개념:
                    • @HiltAndroidApp - 앱 클래스에 적용
                    • @AndroidEntryPoint - Activity, Fragment 등에 적용
                    • @Inject - 생성자 주입
                    • @Module과 @Provides - 외부 의존성 제공

                    Clean Architecture와 함께 사용하면 테스트 가능하고 유지보수하기 좋은 코드를 작성할 수 있습니다.
                """.trimIndent(),
                authorId = 2,
                authorName = "이코드",
                tags = listOf("Hilt", "DI", "Android", "Architecture"),
                viewCount = 980,
                likeCount = 67
            ),
            ArticleEntity(
                id = 3,
                title = "Navigation Compose 완벽 가이드",
                content = """
                    Type-safe Navigation은 Compose 앱에서 화면 간 이동을 안전하게 처리합니다.
                    kotlinx.serialization을 활용한 타입 안전한 라우팅을 구현할 수 있습니다.

                    구현 단계:
                    1. Route sealed interface 정의
                    2. NavHost 설정
                    3. composable 함수로 화면 등록
                    4. NavController로 네비게이션 수행

                    딥링크, 인자 전달, 애니메이션 등 다양한 기능도 지원합니다.
                """.trimIndent(),
                authorId = 1,
                authorName = "김개발",
                tags = listOf("Navigation", "Compose", "Android"),
                viewCount = 756,
                likeCount = 45
            ),
            ArticleEntity(
                id = 4,
                title = "멀티모듈 아키텍처의 장점",
                content = """
                    멀티모듈 아키텍처는 대규모 Android 앱 개발에 필수적인 설계 패턴입니다.

                    장점:
                    • 빌드 시간 단축 - 변경된 모듈만 재빌드
                    • 코드 재사용성 향상
                    • 팀 간 병렬 개발 가능
                    • 의존성 명확화
                    • 기능별 독립적인 테스트

                    Clean Architecture와 결합하면:
                    - core:model - 도메인 모델
                    - core:domain - UseCase, Repository 인터페이스
                    - core:data - Repository 구현체
                    - features:* - 기능별 UI 모듈
                """.trimIndent(),
                authorId = 3,
                authorName = "박모듈",
                tags = listOf("Architecture", "Multi-Module", "Android", "Clean Architecture"),
                viewCount = 2100,
                likeCount = 156
            ),
            ArticleEntity(
                id = 5,
                title = "Kotlin Coroutines와 Flow 활용하기",
                content = """
                    Coroutines는 비동기 프로그래밍을 간단하게 만들어주는 Kotlin의 핵심 기능입니다.
                    Flow는 반응형 스트림을 처리하는 콜드 스트림입니다.

                    주요 개념:
                    • suspend 함수 - 일시 중단 가능한 함수
                    • CoroutineScope - 코루틴 생명주기 관리
                    • Flow - 비동기 데이터 스트림
                    • StateFlow - UI 상태 관리에 적합

                    Room, Retrofit과 함께 사용하면 반응형 데이터 레이어를 구축할 수 있습니다.
                """.trimIndent(),
                authorId = 1,
                authorName = "김개발",
                tags = listOf("Kotlin", "Coroutines", "Flow", "Async"),
                viewCount = 1340,
                likeCount = 92
            ),
            ArticleEntity(
                id = 6,
                title = "Room Database 심화 가이드",
                content = """
                    Room은 SQLite 위에 구축된 Android의 공식 ORM 라이브러리입니다.

                    핵심 컴포넌트:
                    • @Entity - 테이블 정의
                    • @Dao - 데이터 접근 객체
                    • @Database - 데이터베이스 홀더

                    고급 기능:
                    - TypeConverter로 커스텀 타입 저장
                    - Migration으로 스키마 변경 관리
                    - Flow 반환으로 실시간 업데이트
                    - Relation으로 테이블 조인

                    Repository 패턴과 함께 사용하여 데이터 레이어를 추상화하세요.
                """.trimIndent(),
                authorId = 2,
                authorName = "이코드",
                tags = listOf("Room", "Database", "Android", "SQLite"),
                viewCount = 890,
                likeCount = 58
            )
        )
        articleDao.insertArticles(articles)
    }
}
