package com.diordna.miru.di

import android.content.Context
import androidx.room.Room
import com.diordna.miru.data.TodoRepository
import com.diordna.miru.data.TodoRepositoryImpl
import com.diordna.miru.data.db.TodoDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Modules {

    @Singleton
    @Provides
    fun providesTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo-database"
        ).build()
    }

    @Singleton
    @Binds
    abstract fun bindsTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository

}