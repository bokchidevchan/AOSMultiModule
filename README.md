# AOSMultiModule

Android 멀티모듈 아키텍처 샘플 프로젝트입니다. Clean Architecture와 Modern Android Development(MAD) 스택을 활용하여 확장 가능한 앱 구조를 구현합니다.

## 프로젝트 개요

이 프로젝트는 **멀티모듈 vs 싱글모듈** 빌드 성능 비교를 위해 만들어졌습니다.

| 구조 | 모듈 수 | 빌드 시간 |
|------|---------|-----------|
| 멀티모듈 | 13개 | **11~17초** |
| 싱글모듈 | 1개 | 70초 |

> 멀티모듈 구조가 약 **4~6배 빠른** 빌드 성능을 보여줍니다.

---

## 브랜치 구조

### `main` - 멀티모듈 + Clean Architecture (권장)

현재 가장 완성된 브랜치입니다.

```
📦 AOSMultiModule
├── 📂 app                    # Application 모듈
├── 📂 core
│   ├── 📂 model              # 도메인 모델 (Article, User, Settings)
│   ├── 📂 common             # 공통 유틸리티 (Result, Extensions)
│   ├── 📂 domain             # UseCase, Repository 인터페이스
│   ├── 📂 data               # Repository 구현체
│   ├── 📂 database           # Room Database, DAO, Entity
│   ├── 📂 network            # Retrofit, ApiService, DTO
│   ├── 📂 navigation         # Type-safe Route 정의
│   └── 📂 ui                 # 공통 Compose 컴포넌트
└── 📂 features
    ├── 📂 home               # 홈 화면
    ├── 📂 detail             # 상세 화면
    ├── 📂 search             # 검색 화면
    ├── 📂 settings           # 설정 화면
    └── 📂 profile            # 프로필 화면
```

**주요 특징:**
- Clean Architecture 데이터 흐름 (UI → ViewModel → UseCase → Repository → DataSource)
- Hilt 의존성 주입
- Type-safe Navigation (kotlinx.serialization)
- Room Database + 더미 데이터 시딩
- MockInterceptor로 오프라인 API 지원

---

### `feature/clean-architecture` - Clean Architecture 초기 버전

멀티모듈 구조의 기본 형태입니다. `main` 브랜치에 머지되었습니다.

```
커밋 히스토리:
- arch: Clean Architecture 멀티모듈 구조 추가
- build(gradle): Type-safe project accessor 적용
- arch: Hilt와 Navigation을 사용한 멀티모듈 아키텍처 구성
```

---

### `feature/single-module` - 싱글모듈 (빌드 비교용)

모든 코드가 단일 `app` 모듈에 통합된 버전입니다.

```
📦 AOSMultiModule
└── 📂 app                    # 모든 코드가 여기에 통합
    └── 📂 src/main/java
        └── 📂 io/github/bokchidevchan
            ├── 📂 model
            ├── 📂 common
            ├── 📂 domain
            ├── 📂 data
            ├── 📂 database
            ├── 📂 network
            ├── 📂 navigation
            ├── 📂 ui
            └── 📂 feature
                ├── 📂 home
                ├── 📂 detail
                ├── 📂 search
                ├── 📂 settings
                └── 📂 profile
```

**빌드 시간:** 약 70초 (멀티모듈 대비 4~6배 느림)

---

## 기술 스택

### Core
| 기술 | 버전 | 용도 |
|------|------|------|
| Kotlin | 2.0+ | 메인 언어 |
| Jetpack Compose | BOM | 선언적 UI |
| Hilt | 2.51+ | 의존성 주입 |
| Navigation Compose | 2.8+ | Type-safe 네비게이션 |

### Data
| 기술 | 버전 | 용도 |
|------|------|------|
| Room | 2.6+ | 로컬 데이터베이스 |
| Retrofit | 2.11+ | HTTP 클라이언트 |
| OkHttp | 4.12+ | 네트워크 인터셉터 |
| DataStore | 1.1+ | 설정 저장 |
| kotlinx.serialization | - | JSON 직렬화 |

### Async
| 기술 | 버전 | 용도 |
|------|------|------|
| Coroutines | 1.8+ | 비동기 처리 |
| Flow | - | 반응형 스트림 |

### UI
| 기술 | 용도 |
|------|------|
| Material3 | 디자인 시스템 |
| Coil | 이미지 로딩 |

---

## 아키텍처

### Clean Architecture 레이어

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                    │
│              (Screen, ViewModel, UiState)               │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                      Domain Layer                        │
│              (UseCase, Repository Interface)            │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                       Data Layer                         │
│         (RepositoryImpl, DataSource, DTO, Entity)       │
└─────────────────────────────────────────────────────────┘
```

### 모듈 의존성 그래프

```
                        ┌─────┐
                        │ app │
                        └──┬──┘
           ┌───────────────┼───────────────┐
           ▼               ▼               ▼
    ┌────────────┐  ┌────────────┐  ┌────────────┐
    │  features  │  │ core:data  │  │core:database│
    │   (home,   │  └─────┬──────┘  └─────┬──────┘
    │  detail..) │        │               │
    └─────┬──────┘        └───────┬───────┘
          │                       ▼
          │               ┌────────────┐
          └──────────────►│core:domain │
                          └─────┬──────┘
                                ▼
                          ┌────────────┐
                          │ core:model │
                          └────────────┘
```

### 데이터 흐름 예시 (Home)

```kotlin
// 1. UI Layer - HomeScreen.kt
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    // UI 렌더링
}

// 2. Presentation Layer - HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    init {
        getArticlesUseCase().collect { result -> /* 상태 업데이트 */ }
    }
}

// 3. Domain Layer - GetArticlesUseCase.kt
class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke(): Flow<Result<List<Article>>> =
        articleRepository.getArticles()
}

// 4. Data Layer - ArticleRepositoryImpl.kt
class ArticleRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val apiService: ApiService
) : ArticleRepository {
    override fun getArticles() = articleDao.getAllArticles()
        .map { entities -> Result.Success(entities.toDomain()) }
}
```

---

## 네비게이션

### Type-safe Route 정의

```kotlin
// core/navigation/Route.kt
sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data class Detail(
        val itemId: Int,
        val title: String
    ) : Route
}
```

### 네비게이션 사용

```kotlin
// 화면 이동
navController.navigate(Route.Detail(itemId = 1, title = "Compose"))

// 뒤로가기
navController.popBackStack()
```

---

## 빌드 및 실행

### 요구사항
- Android Studio Ladybug 이상
- JDK 11+
- Android SDK 36

### 빌드

```bash
# 디버그 빌드
./gradlew assembleDebug

# 빌드 시간 측정
time ./gradlew clean assembleDebug
```

### 브랜치 전환

```bash
# 멀티모듈 (권장)
git checkout main

# 싱글모듈 (비교용)
git checkout feature/single-module
```

---

## 더미 데이터

앱 첫 실행 시 자동으로 시딩되는 데이터:

### 사용자 (4명)
| ID | 이름 | 역할 |
|----|------|------|
| 1 | 김개발 | Android 개발자 |
| 2 | 이코드 | 풀스택 개발자 |
| 3 | 박모듈 | 소프트웨어 아키텍트 |
| 4 | 최컴포즈 | UI/UX 개발자 |

### 아티클 (6개)
| ID | 제목 | 작성자 |
|----|------|--------|
| 1 | Jetpack Compose 시작하기 | 최컴포즈 |
| 2 | Hilt로 의존성 주입 마스터하기 | 이코드 |
| 3 | Navigation Compose 완벽 가이드 | 김개발 |
| 4 | 멀티모듈 아키텍처의 장점 | 박모듈 |
| 5 | Kotlin Coroutines와 Flow 활용하기 | 김개발 |
| 6 | Room Database 심화 가이드 | 이코드 |

---

## 라이선스

MIT License
