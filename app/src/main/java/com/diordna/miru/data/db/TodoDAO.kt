package com.diordna.miru.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo")
    fun selectAll(): List<TodoEntity>

    @Query("DELETE FROM todo WHERE id = :id")
    fun deleteForId(id: Long)

    @Insert
    fun insert(todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)

}