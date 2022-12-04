package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.MovieAdapter
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant.EXTRA_MOVIE
import com.example.moviesmanager.model.Movie

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var parl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        movieAdapter = MovieAdapter(this, movieList)
        amb.movieLv.adapter = movieAdapter

        movieAdapter.notifyDataSetChanged()

        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),) {
                result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.id == _movie.id }
                    if (position != -1) {
                        movieList[position] = _movie
                    }
                    else {
                        movieList.add(_movie)
                    }
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.movieLv)
        }

    private fun fillMovieList() {
        for (i in 1..3) {
            movieList.add(
                Movie(
                    id = i,
                    nome = "Nome $i",
                    anoLancamento = "Ano de Lançamento $i",
                    estudio = "Estudio $i",
                    tempoDuracao = "Duração $i",
                    flag = "Flag $i",
                    nota = "Nota $i",
                    genero = "Gênero $i",
                )
            )
        }
    }

}