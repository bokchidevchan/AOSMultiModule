package io.github.bokchidevchan.core.data.repository

import io.github.bokchidevchan.core.common.IoDispatcher
import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.database.dao.UserDao
import io.github.bokchidevchan.core.database.entity.toDomain
import io.github.bokchidevchan.core.database.entity.toEntity
import io.github.bokchidevchan.core.domain.repository.UserRepository
import io.github.bokchidevchan.core.model.User
import io.github.bokchidevchan.core.network.ApiService
import io.github.bokchidevchan.core.network.dto.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override fun getUsers(): Flow<Result<List<User>>> {
        return userDao.getAllUsers()
            .map<_, Result<List<User>>> { entities ->
                Result.Success(entities.toDomain())
            }
            .catch { emit(Result.Error(it)) }
            .flowOn(ioDispatcher)
    }

    override fun getUserById(id: Long): Flow<Result<User>> {
        return userDao.getUserByIdFlow(id)
            .map<_, Result<User>> { entity ->
                entity?.let { Result.Success(it.toDomain()) }
                    ?: Result.Error(NoSuchElementException("User not found: $id"))
            }
            .catch { emit(Result.Error(it)) }
            .flowOn(ioDispatcher)
    }

    override suspend fun searchUsers(query: String): Result<List<User>> {
        return withContext(ioDispatcher) {
            try {
                val users = userDao.searchUsers(query).toDomain()
                Result.Success(users)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                userDao.updateUser(user.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun refreshUsers(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val remoteUsers = apiService.getUsers()
                val entities = remoteUsers.toDomain().map { it.toEntity() }
                userDao.insertUsers(entities)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
