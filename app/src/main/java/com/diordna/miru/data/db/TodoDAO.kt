package com.diordna.miru.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diordna.miru.data.TodoUiData

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo")
    fun selectAll(): List<TodoEntity>

    @Insert
    fun insert(todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)

}