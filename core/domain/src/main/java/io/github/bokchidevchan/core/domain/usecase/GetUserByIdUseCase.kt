package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.common.Result
import io.github.bokchidevchan.core.domain.repository.UserRepository
import io.github.bokchidevchan.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: Long): Flow<Result<User>> {
        return userRepository.getUserById(id)
    }
}
