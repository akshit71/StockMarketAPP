package com.example.stockmarketapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.example.stockmarketapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntraDayInfoDto.toIntaDayInfo():IntraDayInfo{
    val pattern="yyyy-MM-dd HH:mm:ss"
    val formatter=DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localeDateTime=LocalDateTime.parse(timeStamp,formatter)
    return IntraDayInfo(
        date = localeDateTime,
        close = close
    )
}