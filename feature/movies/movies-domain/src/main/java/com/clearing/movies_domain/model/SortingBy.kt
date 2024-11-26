package com.clearing.movies_domain.model

enum class SortingBy(val value: String) {
    PopularityDesc(value = "popularity.desc"),
    RevenueDesc(value = "revenue.desc")
}