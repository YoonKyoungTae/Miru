package com.diordna.miru.data

import androidx.room.Room
import com.diordna.miru.data.db.TodoDatabase

class TodoLocalDataStore(override val todoDatabase: TodoDatabase) : TodoRepository {




}