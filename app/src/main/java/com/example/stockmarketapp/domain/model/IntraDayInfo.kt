package com.example.stockmarketapp.domain.model

import java.time.LocalDateTime
import java.util.Date

data class IntraDayInfo(
    val date: LocalDateTime,
    val close :Double
)
