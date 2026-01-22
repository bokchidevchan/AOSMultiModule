package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.UserRepository
import io.github.bokchidevchan.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<List<User>>> {
        return userRepository.getUsers()
    }
}
