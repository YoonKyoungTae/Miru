package com.diordna.miru.data

import com.diordna.miru.data.db.TodoDatabase

interface TodoRepository {
    val todoDatabase: TodoDatabase
}