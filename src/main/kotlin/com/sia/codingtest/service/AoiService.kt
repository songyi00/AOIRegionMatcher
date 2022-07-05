package com.sia.codingtest.service

import com.sia.codingtest.domain.AOI
import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.CreateAoiDto
import com.sia.codingtest.repository.AoiRepository
import com.sia.codingtest.repository.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AoiService(private val aoiRepository: AoiRepository, private val regionRepository: RegionRepository) {

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

    // 행정지역에 지리적으로 포함되는 관심 지역 조회
    fun findAoiInRegion(regionId : Long) : List<AOI>? {
            return aoiRepository.findAOISByRegion(regionId)
    }

}