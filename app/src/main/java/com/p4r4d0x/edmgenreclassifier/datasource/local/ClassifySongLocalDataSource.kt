package com.p4r4d0x.edmgenreclassifier.datasource.local

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import com.p4r4d0x.edmgenreclassifier.datasource.repository.ServiceResponse

interface ClassifySongLocalDataSource {
    fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO>
}
