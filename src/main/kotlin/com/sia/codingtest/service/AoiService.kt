package com.sia.codingtest.service

import com.sia.codingtest.domain.AOI
import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.CreateAoiDto
import com.sia.codingtest.dto.CreateRegionDto
import com.sia.codingtest.repository.AoiRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AoiService(private val aoiRepository: AoiRepository) {

    // 관심 지역 정보 저장
    @Transactional
    fun saveAoi(createAoiDto: CreateAoiDto ) : Long {
        val aoi = AOI.createRegionInfo(createAoiDto)
        aoiRepository.save(aoi)
        return aoi.id
    }

    // 관심 지역 조회 by id
    fun findOne(aoiId : Long) : AOI?{
        return aoiRepository.findAOIById(aoiId)
    }

}