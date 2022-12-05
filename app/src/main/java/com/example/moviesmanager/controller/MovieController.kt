package com.example.moviesmanager.controller

import com.example.moviesmanager.model.Movie
import com.example.moviesmanager.model.MovieDAO
import com.example.moviesmanager.model.MovieDAOSQLite
import com.example.moviesmanager.view.MainActivity

class MovieController(mainActivity: MainActivity) {
    private val contactDaoImpl: MovieDAO = MovieDAOSQLite(mainActivity)

    fun insertContact(movie: Movie) = contactDaoImpl.createMovie(movie)
    fun getMovie(id: Int) = contactDaoImpl.retrieveMovie(id)
    fun getMovies() = contactDaoImpl.retrieveMovies()
    fun editMovie(movie: Movie) = contactDaoImpl.updateMovie(movie)
    fun removeMovie(id: Int) = contactDaoImpl.deleteMovie(id)
}