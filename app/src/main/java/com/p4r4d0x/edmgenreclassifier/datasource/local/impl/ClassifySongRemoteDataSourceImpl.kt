package com.p4r4d0x.edmgenreclassifier.datasource.local.impl

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import com.p4r4d0x.edmgenreclassifier.datasource.local.ClassifySongLocalDataSource
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ServiceResponse

class ClassifySongLocalDataSourceImpl :
    ClassifySongLocalDataSource {
    override fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO> {
        TODO("Not yet implemented")
    }
}
