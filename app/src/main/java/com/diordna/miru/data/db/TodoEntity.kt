package com.diordna.miru.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diordna.miru.data.TodoUiData

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo var title: String? = "",
    @ColumnInfo var isDone: Boolean? = false,
    @ColumnInfo var viewingDate: String? = "",
    @ColumnInfo val createAtMillis: Long? = 0,
    @ColumnInfo var updateAtMillis: Long? = 0
)

fun TodoEntity.toUiData() = TodoUiData(
    id = this.id,
    title = this.title ?: "",
    isDone = this.isDone ?: false,
    viewingDate = this.viewingDate ?: "20000101",
    createAtMillis = this.createAtMillis ?: 0,
    updateAtMillis = this.updateAtMillis ?: 0,
)