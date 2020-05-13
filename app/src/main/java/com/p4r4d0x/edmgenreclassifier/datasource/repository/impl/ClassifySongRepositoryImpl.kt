package com.p4r4d0x.edmgenreclassifier.datasource.repository.impl

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import com.p4r4d0x.edmgenreclassifier.datasource.remote.ClassifySongRemoteDataSource
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ClassifySongRepository
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ServiceResponse

class ClassifySongRepositoryImpl(remoteDataSource: ClassifySongRemoteDataSource) :
    ClassifySongRepository {

    override fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO> {
        //Use retrofit instance to call the service
        return ServiceResponse(500, null, null)
    }

}