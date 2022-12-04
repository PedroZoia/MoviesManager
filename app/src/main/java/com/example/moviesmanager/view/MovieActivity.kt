package com.example.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.moviesmanager.databinding.ActivityMovieBinding
import com.example.moviesmanager.model.Constant.EXTRA_LIST_MOVIE_NAMES
import com.example.moviesmanager.model.Constant.EXTRA_MOVIE
import com.example.moviesmanager.model.Constant.VIEW_MOVIE
import com.example.moviesmanager.model.Genero
import com.example.moviesmanager.model.Movie
import kotlin.random.Random

class MovieActivity : AppCompatActivity(){
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val listMovies = intent.getStringArrayListExtra(EXTRA_LIST_MOVIE_NAMES)
        Log.i("log", listMovies?.size.toString())

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{ _receiveMovie ->
            with(amb) {
                with(_receiveMovie) {
                    nomeEt.isEnabled = false
                    nomeEt.setText(nome)
                    duracaoEt.setText(tempoDuracao)
                    anoLancamentoEt.setText(anoLancamento)
                    if(flag == "checked") flagSw.toggle()
                    estudioEt.setText(estudio)
                    notaEt.setText(nota)
                    for (i in 0 until Genero.values().size){
                        if(genero == Genero.values()[i].toString()) {
                            generoSp.setSelection(i)
                        }
                    }
                }
            }
        }
        val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)
        if (viewMovie) {
            amb.nomeEt.isEnabled = false
            amb.anoLancamentoEt.isEnabled = false
            amb.duracaoEt.isEnabled = false
            amb.estudioEt.isEnabled = false
            amb.flagSw.isEnabled = false
            amb.notaEt.isEnabled = false
            amb.generoSp.isEnabled = false
            amb.saveBt.visibility = View.GONE
        }

        amb.saveBt.setOnClickListener {
            val flagValue : String = if(amb.flagSw.isChecked) "checked" else "unchecked"

            if (listMovies != null) {
                Log.i("log", "listMovieNotNull")
                for (i in 0 until listMovies.size){
                    Log.i("log", "entrou no for")
                    if(listMovies[i] == amb.nomeEt.text.toString()) {
                        Toast.makeText(this, "nome já cadastrado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            val movie = Movie(
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = amb.nomeEt.text.toString(),
                tempoDuracao = amb.duracaoEt.text.toString(),
                anoLancamento = amb.anoLancamentoEt.text.toString(),
                estudio = amb.estudioEt.text.toString(),
                flag = flagValue,
                nota = amb.notaEt.text.toString(),
                genero = amb.generoSp.selectedItem.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
    }