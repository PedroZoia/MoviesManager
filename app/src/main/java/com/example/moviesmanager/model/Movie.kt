package com.example.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Movie {
    @Parcelize
    data class Person(
        val id: Int,
        var nome: String,
        var anoLancamento: String,
        var estudio: String,
        var tempoDuracao: String,
        var flag: String,
        var nota: String,
        var genero: String,
    ): Parcelable
}