package com.stslex.atten.feature.home.domain.interactor

import com.stslex.atten.core.paging.model.PagingResponse
import com.stslex.atten.feature.home.domain.model.ToDoDomainModel

interface HomeScreenInteractor {

    suspend fun getToDoList(
        page: Int,
        pageSize: Int
    ): PagingResponse<ToDoDomainModel>
}
