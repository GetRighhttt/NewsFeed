package com.example.newsfeed.data.db

import androidx.room.TypeConverter
import com.example.newsfeed.data.model.Source

/**
 * Our Source response from the API represents another data class. We can either annotate both
 * as tables or create a converter class. However, it only has two properties and we just want
 * the name of the source.
 *
 * So instead, we are going to create a converter class using Room type converters. We will create
 * a function to return the name of the source instead of the subject.
 * @TypeConverter used for conversion.
 */
class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String {
        return source.name
    }

    /*
    Because we created the function above, Room will only save the name of the source and
    not the entire source.

    So we need to create another function to return a Source Instance from the database.
     */
    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name, name)
    }
}