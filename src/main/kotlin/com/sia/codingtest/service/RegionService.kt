package com.sia.codingtest.service

import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.CreateRegionDto
import com.sia.codingtest.repository.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RegionService(private val regionRepository: RegionRepository) {

    // 행정지역 정보 저장
    @Transactional
    fun saveRegion(createRegionDto : CreateRegionDto) : Long {
        val region : Region = Region.createRegionInfo(createRegionDto)
        regionRepository.save(region)
        return region.id
    }

    // 행정지역 조회 by id
    fun findOne(regionId: Long) : Region? {
        return  regionRepository.findRegionById(regionId)
    }

    // 행정지역에 지리적으로 포함되는 관심 지역 조회

}