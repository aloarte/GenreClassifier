package com.p4r4d0x.edmgenreclassifier.datasource.remote

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ServiceResponse

interface ClassifySongRemoteDataSource {
    fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO>
}
