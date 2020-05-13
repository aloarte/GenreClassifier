package com.p4r4d0x.edmgenreclassifier.datasource.remote.impl

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import com.p4r4d0x.edmgenreclassifier.datasource.remote.ClassifySongRemoteDataSource
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ServiceResponse

class ClassifySongRemoteDataSourceImpl :
    ClassifySongRemoteDataSource {
    override fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO> {
        TODO("Not yet implemented")
    }
}
