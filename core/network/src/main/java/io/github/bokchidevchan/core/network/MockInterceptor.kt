package io.github.bokchidevchan.core.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        val responseBody = when {
            url.contains("/users/") -> getUserById(url)
            url.contains("/users") -> getUsers()
            url.contains("/articles/") -> getArticleById(url)
            url.contains("/articles") -> getArticles()
            url.contains("/search") -> search(request.url.queryParameter("q") ?: "")
            else -> """{"error": "Not found"}"""
        }

        return Response.Builder()
            .code(200)
            .message("OK")
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .body(responseBody.toResponseBody("application/json".toMediaType()))
            .build()
    }

    private fun getUserById(url: String): String {
        val id = url.substringAfterLast("/users/").substringBefore("?").toLongOrNull() ?: 1
        return mockUsers.find { it.contains("\"id\":$id") } ?: mockUsers.first()
    }

    private fun getUsers(): String = "[${ mockUsers.joinToString(",") }]"

    private fun getArticleById(url: String): String {
        val id = url.substringAfterLast("/articles/").substringBefore("?").toLongOrNull() ?: 1
        return mockArticles.find { it.contains("\"id\":$id") } ?: mockArticles.first()
    }

    private fun getArticles(): String = "[${ mockArticles.joinToString(",") }]"

    private fun search(query: String): String {
        val lowerQuery = query.lowercase()
        val filteredArticles = mockArticles.filter {
            it.lowercase().contains(lowerQuery)
        }
        val filteredUsers = mockUsers.filter {
            it.lowercase().contains(lowerQuery)
        }

        return """
        {
            "query": "$query",
            "articles": [${filteredArticles.joinToString(",")}],
            "users": [${filteredUsers.joinToString(",")}],
            "total_count": ${filteredArticles.size + filteredUsers.size},
            "page": 1,
            "has_more": false
        }
        """.trimIndent()
    }

    companion object {
        private val mockUsers = listOf(
            """
            {
                "id": 1,
                "name": "김개발",
                "email": "dev.kim@example.com",
                "avatar_url": "https://i.pravatar.cc/150?u=1",
                "bio": "Android 개발자 | Kotlin 애호가",
                "created_at": 1700000000000
            }
            """.trimIndent(),
            """
            {
                "id": 2,
                "name": "이코드",
                "email": "code.lee@example.com",
                "avatar_url": "https://i.pravatar.cc/150?u=2",
                "bio": "풀스택 개발자 | Clean Architecture 전문가",
                "created_at": 1700100000000
            }
            """.trimIndent(),
            """
            {
                "id": 3,
                "name": "박모듈",
                "email": "module.park@example.com",
                "avatar_url": "https://i.pravatar.cc/150?u=3",
                "bio": "소프트웨어 아키텍트 | 멀티모듈 설계 전문",
                "created_at": 1700200000000
            }
            """.trimIndent(),
            """
            {
                "id": 4,
                "name": "최컴포즈",
                "email": "compose.choi@example.com",
                "avatar_url": "https://i.pravatar.cc/150?u=4",
                "bio": "UI/UX 개발자 | Jetpack Compose 마스터",
                "created_at": 1700300000000
            }
            """.trimIndent()
        )

        private val mockArticles = listOf(
            """
            {
                "id": 1,
                "title": "Jetpack Compose 시작하기",
                "content": "Jetpack Compose는 Android의 최신 UI 툴킷입니다. 선언적 UI 패러다임을 사용하여 더 적은 코드로 아름다운 UI를 만들 수 있습니다.",
                "author_id": 4,
                "author_name": "최컴포즈",
                "thumbnail_url": null,
                "tags": ["Compose", "Android", "UI", "Kotlin"],
                "view_count": 1520,
                "like_count": 89,
                "created_at": 1700000000000,
                "updated_at": 1700000000000
            }
            """.trimIndent(),
            """
            {
                "id": 2,
                "title": "Hilt로 의존성 주입 마스터하기",
                "content": "Hilt는 Android 앱에서 의존성 주입을 쉽게 구현할 수 있게 해주는 라이브러리입니다. Dagger 위에 구축되어 보일러플레이트 코드를 줄여줍니다.",
                "author_id": 2,
                "author_name": "이코드",
                "thumbnail_url": null,
                "tags": ["Hilt", "DI", "Android", "Architecture"],
                "view_count": 980,
                "like_count": 67,
                "created_at": 1700100000000,
                "updated_at": 1700100000000
            }
            """.trimIndent(),
            """
            {
                "id": 3,
                "title": "Navigation Compose 완벽 가이드",
                "content": "Type-safe Navigation은 Compose 앱에서 화면 간 이동을 안전하게 처리합니다. kotlinx.serialization을 활용한 타입 안전한 라우팅을 구현할 수 있습니다.",
                "author_id": 1,
                "author_name": "김개발",
                "thumbnail_url": null,
                "tags": ["Navigation", "Compose", "Android"],
                "view_count": 756,
                "like_count": 45,
                "created_at": 1700200000000,
                "updated_at": 1700200000000
            }
            """.trimIndent(),
            """
            {
                "id": 4,
                "title": "멀티모듈 아키텍처의 장점",
                "content": "멀티모듈 아키텍처는 대규모 Android 앱 개발에 필수적인 설계 패턴입니다. 빌드 시간 단축, 코드 재사용성 향상 등 다양한 이점이 있습니다.",
                "author_id": 3,
                "author_name": "박모듈",
                "thumbnail_url": null,
                "tags": ["Architecture", "Multi-Module", "Android", "Clean Architecture"],
                "view_count": 2100,
                "like_count": 156,
                "created_at": 1700300000000,
                "updated_at": 1700300000000
            }
            """.trimIndent(),
            """
            {
                "id": 5,
                "title": "Kotlin Coroutines와 Flow 활용하기",
                "content": "Coroutines는 비동기 프로그래밍을 간단하게 만들어주는 Kotlin의 핵심 기능입니다. Flow는 반응형 스트림을 처리하는 콜드 스트림입니다.",
                "author_id": 1,
                "author_name": "김개발",
                "thumbnail_url": null,
                "tags": ["Kotlin", "Coroutines", "Flow", "Async"],
                "view_count": 1340,
                "like_count": 92,
                "created_at": 1700400000000,
                "updated_at": 1700400000000
            }
            """.trimIndent(),
            """
            {
                "id": 6,
                "title": "Room Database 심화 가이드",
                "content": "Room은 SQLite 위에 구축된 Android의 공식 ORM 라이브러리입니다. TypeConverter, Migration, Flow 반환 등 다양한 고급 기능을 지원합니다.",
                "author_id": 2,
                "author_name": "이코드",
                "thumbnail_url": null,
                "tags": ["Room", "Database", "Android", "SQLite"],
                "view_count": 890,
                "like_count": 58,
                "created_at": 1700500000000,
                "updated_at": 1700500000000
            }
            """.trimIndent()
        )
    }
}
