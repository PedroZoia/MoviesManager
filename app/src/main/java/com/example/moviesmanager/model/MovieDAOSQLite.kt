package com.example.moviesmanager.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.moviesmanager.R
import java.sql.SQLException

class MovieDAOSQLite(context: Context) : MovieDAO {
    companion object Constant {
        private const val CONTACT_DATABASE_FILE = "movies"
        private const val CONTACT_TABLE = "movie"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "nome"
        private const val ANOLANCAMENTO_COLUMN = "anoLancamento"
        private const val ESTUDIO_COLUMN = "estudio"
        private const val TEMPODURACAO_COLUMN = "tempoDuracao"
        private const val FLAG_COLUMN = "flag"
        private const val NOTA_COLUMN = "nota"
        private const val GENERO_COLUMN = "genero"

        private const val CREATE_CONTACT_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $CONTACT_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL, " +
                    "$ANOLANCAMENTO_COLUMN TEXT NOT NULL, " +
                    "$ESTUDIO_COLUMN TEXT NOT NULL, " +
                    "$TEMPODURACAO_COLUMN TEXT NOT NULL, "+
                    "$FLAG_COLUMN TEXT NOT NULL, "+
                    "$NOTA_COLUMN TEXT NOT NULL, "+
                    "$GENERO_COLUMN TEXT NOT NULL);"
    }

    // ReferĂȘncia para o banco de dados
    private val movieSqliteDatabase: SQLiteDatabase

    init {
        // Criando ou abrindo o banco
        movieSqliteDatabase = context.openOrCreateDatabase(
            CONTACT_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            movieSqliteDatabase.execSQL(CREATE_CONTACT_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    private fun Movie.toContentValues() = with(ContentValues()) {
        put(NAME_COLUMN, nome)
        put(ANOLANCAMENTO_COLUMN, anoLancamento)
        put(ESTUDIO_COLUMN, estudio)
        put(TEMPODURACAO_COLUMN, tempoDuracao)
        put(FLAG_COLUMN, flag)
        put(NOTA_COLUMN, nota)
        put(GENERO_COLUMN, genero)
        this
    }


    private fun Cursor.rowToMovie() = Movie(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getString(getColumnIndexOrThrow(ANOLANCAMENTO_COLUMN)),
        getString(getColumnIndexOrThrow(ESTUDIO_COLUMN)),
        getString(getColumnIndexOrThrow(TEMPODURACAO_COLUMN)),
        getString(getColumnIndexOrThrow(FLAG_COLUMN)),
        getString(getColumnIndexOrThrow(NOTA_COLUMN)),
        getString(getColumnIndexOrThrow(GENERO_COLUMN)),

    )

    override fun createMovie(movie: Movie) = movieSqliteDatabase.insert(
        CONTACT_TABLE,
        null,
        movie.toContentValues()
    ).toInt()


    override fun retrieveMovie(id: Int): Movie? {
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $CONTACT_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val movie = if (cursor.moveToFirst()) cursor.rowToMovie() else null

        cursor.close()
        return movie
    }

    override fun retrieveMovies(): MutableList<Movie> {
        val movieList = mutableListOf<Movie>()
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $CONTACT_TABLE ORDER BY $NAME_COLUMN",
            null
        )
        while (cursor.moveToNext()) {
            movieList.add(cursor.rowToMovie())
        }
        cursor.close()
        return movieList
    }

    override fun updateMovie(movie: Movie) = movieSqliteDatabase.update(
        CONTACT_TABLE,
        movie.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(movie.id.toString())
    )

    override fun deleteMovie(id: Int) =
        movieSqliteDatabase.delete(
            CONTACT_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )
}