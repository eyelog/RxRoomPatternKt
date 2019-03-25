package ru.eyelog.rxroompatternkt.ui

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.eyelog.rxroompatternkt.persistance.User
import ru.eyelog.rxroompatternkt.persistance.UserDao

class UserViewModel(private val dataSource: UserDao): ViewModel(){

    fun userName():Flowable<String>{
        return dataSource.getUserById(USER_ID)
            .map { user -> user.userName }
    }

    fun updateUserName(userName: String): Completable{
        val user = User(USER_ID, userName)
        return dataSource.insertUser(user)
    }

    companion object {
        const val USER_ID = "1"
    }
}