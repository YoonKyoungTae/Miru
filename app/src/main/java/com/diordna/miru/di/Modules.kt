package com.diordna.miru.di

import android.content.Context
import androidx.room.Room
import com.diordna.miru.data.TodoRepositoryImpl
import com.diordna.miru.data.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Modules {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepositoryImpl {
        return TodoRepositoryImpl(todoDatabase)
    }



}