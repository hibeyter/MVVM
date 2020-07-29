package com.football.mvvmarchitecture.db

import androidx.room.TypeConverter
import com.football.mvvmarchitecture.models.Source


class Converter {


    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun tosource(name: String):Source {
        return Source(name,name)
    }


}