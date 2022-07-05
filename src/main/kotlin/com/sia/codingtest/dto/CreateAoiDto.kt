package com.sia.codingtest.dto

import com.vividsolutions.jts.geom.Polygon

data class CreateAoiDto(val name: String, val area: Polygon) {
}