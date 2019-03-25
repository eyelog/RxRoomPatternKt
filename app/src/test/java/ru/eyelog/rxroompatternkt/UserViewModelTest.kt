package ru.eyelog.rxroompatternkt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import ru.eyelog.rxroompatternkt.persistance.User
import ru.eyelog.rxroompatternkt.persistance.UserDao
import ru.eyelog.rxroompatternkt.ui.UserViewModel

class UserViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataSource: UserDao

    @Captor
    private lateinit var userArgumentCaptor: ArgumentCaptor<User>

    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp(){
        initMocks(this)
        viewModel = UserViewModel(dataSource)
    }

    @Test
    fun getUserName_whenNoUserSaved(){
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.empty<User>())

        viewModel.userName()
            .test()
            .assertNoValues()
    }

    @Test
    fun getUserName_whenUserSaved(){
        val user = User(userName = "userName")
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.just(user))

        viewModel.userName()
            .test()
            .assertValue("userName")
    }

    @Test
    fun updateUserName_updateNameInDataSource(){

        dataSource.insertUser(User(UserViewModel.USER_ID, "userName"))

        val userName = "New userName"
        val expectedUser = User(UserViewModel.USER_ID, userName)
        `when`(dataSource.insertUser(expectedUser)).thenReturn(Completable.complete())

        viewModel.updateUserName(userName)
            .test()
            .assertComplete()
    }
}