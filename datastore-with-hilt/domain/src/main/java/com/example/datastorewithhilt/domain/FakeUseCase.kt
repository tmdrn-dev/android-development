package com.example.datastorewithhilt.domain

import com.example.datastorewithhilt.data.model.FakeDataModel
import com.example.datastorewithhilt.data.repository.fake.FakeDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeUseCase @Inject constructor(
    private val fakeDataRepository: FakeDataRepository
) {
    suspend operator fun invoke(): Flow<FakeDataModel> {
        return fakeDataRepository.requestData()
    }
}