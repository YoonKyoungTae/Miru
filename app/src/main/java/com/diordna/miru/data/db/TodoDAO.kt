package com.diordna.miru.data.db

import androidx.room.*

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo")
    fun selectAll(): List<TodoEntity>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun selectForId(id: Long): TodoEntity

    @Query("DELETE FROM todo WHERE id = :id")
    fun deleteForId(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: TodoEntity): Long

    @Update
    fun update(todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)

}