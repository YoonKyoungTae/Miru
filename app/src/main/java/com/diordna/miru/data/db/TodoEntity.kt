package com.diordna.miru.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diordna.miru.data.TodoUiData

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String,
    @ColumnInfo val isDone: Boolean,
    @ColumnInfo val isMiru: Boolean,
    @ColumnInfo val createAtMillis: Long,
    @ColumnInfo val updateAtMillis: Long
)

fun TodoEntity.toUiData() = TodoUiData(
    id = this.id,
    title = this.title,
    isDone = this.isDone,
    isMiru = this.isMiru,
    createAtMillis = this.createAtMillis,
    updateAtMillis = this.updateAtMillis,
)