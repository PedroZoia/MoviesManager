package com.example.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.moviesmanager.databinding.ActivityMovieBinding
import com.example.moviesmanager.model.Constant.EXTRA_MOVIE
import com.example.moviesmanager.model.Constant.VIEW_MOVIE
import com.example.moviesmanager.model.Genero
import com.example.moviesmanager.model.Movie
import kotlin.random.Random

class MovieActivity: AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{ _receiveMovie ->
            with(amb) {
                with(_receiveMovie) {
                    nomeEt.setText(nome)
                    anoLancamentoEt.setText(anoLancamento)
                    estudioEt.setText(estudio)
                    duracaoEt.setText(tempoDuracao)
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
            amb.estudioEt.isEnabled = false
            amb.duracaoEt.isEnabled = false
            amb.notaEt.isEnabled = false
            amb.saveBt.visibility = View.GONE
        }

        amb.saveBt.setOnClickListener {
            val flagValue : String = if(amb.flagSw.isChecked) "checked" else "unchecked"
            val person = Movie(
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = amb.nomeEt.text.toString(),
                anoLancamento = amb.anoLancamentoEt.text.toString(),
                estudio = amb.estudioEt.text.toString(),
                tempoDuracao = amb.duracaoEt.text.toString(),
                flag = flagValue,
                nota = amb.notaEt.text.toString(),
                genero = amb.generoSp.selectedItem.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, person)
            setResult(AppCompatActivity.RESULT_OK, resultIntent)
            finish()
        }
    }
}