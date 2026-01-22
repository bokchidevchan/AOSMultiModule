package io.github.bokchidevchan.core.domain.repository

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>>>
    fun getUserById(id: Long): Flow<Result<User>>
    suspend fun searchUsers(query: String): Result<List<User>>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun refreshUsers(): Result<Unit>
}
