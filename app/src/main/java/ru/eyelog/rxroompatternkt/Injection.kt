package ru.eyelog.rxroompatternkt

import android.content.Context
import ru.eyelog.rxroompatternkt.persistance.UserDao
import ru.eyelog.rxroompatternkt.persistance.UsersDatabase
import ru.eyelog.rxroompatternkt.ui.ViewModelFactory

object Injection{

    fun provideUserDataSource(context: Context):UserDao{
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory{
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}