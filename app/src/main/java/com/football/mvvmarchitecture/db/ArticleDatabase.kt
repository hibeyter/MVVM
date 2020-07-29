package com.football.mvvmarchitecture.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.football.mvvmarchitecture.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticles() :ArticleDao

     companion object{
         @Volatile
         private var instance : ArticleDatabase?=null
         private val LOCK = Any()


         operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
                instance ?: createDatabase(context).also{ instance = it }
         }

         private fun createDatabase(context: Context)=
             Room.databaseBuilder(
                 context.applicationContext,
                 ArticleDatabase::class.java,
                 "news_db.db"
             ).build()
     }


}