package ru.eyelog.rxroompatternkt.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao{

    // Create and also update user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Completable

    // Get all users
    @Query("SELECT * FROM users")
    fun getAllUsers():Flowable<List<User>>

    // Get one user by id
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserById(id:String):Flowable<User>

    // Delete one user by id
    @Query("DELETE FROM users WHERE userId = :id")
    fun deleteOneUserById(id: String)

    // Delete all users
    @Query("DELETE FROM users")
    fun deleteAllUsers()
}