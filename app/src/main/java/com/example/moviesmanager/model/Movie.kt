package com.example.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val id: Int,
    var nome: String,
    var anoLancamento: String,
    var estudio: String,
    var tempoDuracao: String,
    var flag: String,
    var nota: String,
    var genero: String,
): Parcelable
