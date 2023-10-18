package com.diordna.miru.data

import com.diordna.miru.data.db.TodoDatabase

class TodoRepositoryImpl(override val todoDatabase: TodoDatabase) : TodoRepository {
}