package com.example.mediaplayer.domain

import com.example.mediaplayer.data.model.DataModel
import com.example.mediaplayer.data.repository.foo.FooRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val fooRepository: FooRepository
) {
    operator fun invoke(): Flow<DataModel> {
        return fooRepository.requestData()
    }
}