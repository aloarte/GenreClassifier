package com.p4r4d0x.edmgenreclassifier.datasource.repository

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO

interface ClassifySongRepository {

    fun classifySong(request: GenreClassifyRequestDTO): ServiceResponse<GenreClassifyResponseDTO>
}