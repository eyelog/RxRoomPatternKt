package ru.eyelog.rxroompatternkt.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao{

    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserById(id:String):Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Completable

    @Query("DELETE FROM users")
    fun deleteAllUsers()
}