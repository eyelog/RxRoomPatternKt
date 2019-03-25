package ru.eyelog.rxroompatternkt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.eyelog.rxroompatternkt.persistance.User
import ru.eyelog.rxroompatternkt.persistance.UsersDatabase

@RunWith(AndroidJUnit4::class)
class UserDaoTest{

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UsersDatabase

    @Before
    fun initDB(){
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
            UsersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDB(){
        database.close()
    }

    @Test
    fun insertAndGetUser(){
        database.userDao().getUserById("123")
            .test()
            .assertNoValues()
    }

    @Test
    fun updateAndGetUser(){
        database.userDao().insertUser(USER).blockingAwait()

        val updateUser = User(USER.id, "Other userName")
        database.userDao().insertUser(updateUser).blockingAwait()

        database.userDao().getUserById(USER.id)
            .test()
            .assertValue{
                it.id == USER.id && it.userName == "Other userName"
            }
    }

    @Test
    fun deleteAndGetUser(){
        database.userDao().insertUser(USER).blockingAwait()

        database.userDao().deleteAllUsers()
        database.userDao().getUserById(USER.id)
            .test()
            .assertNoValues()
    }

    companion object {
        private val USER = User("id", "username")
    }
}