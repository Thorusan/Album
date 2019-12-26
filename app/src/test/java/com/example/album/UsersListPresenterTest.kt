package com.example.album


import com.example.album.network.DataProvider
import com.example.album.network.NetworkDataProvider
import com.example.album.ui.users.UsersListPresenter
import com.example.album.ui.users.UsersListViewPresenterContract
import com.example.album.ui.users.UsersModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.mock
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import java.util.concurrent.Callable


class UsersListPresenterTest {
    private lateinit var presenter: UsersListPresenter
    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private va dataProvider: DataProvider
    @Mock
    private var mockModel: UsersModel = UsersModel(dataProvider)
    @Mock
    private lateinit var mockView: UsersListViewPresenterContract.ViewInterface

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.io() }

        presenter = UsersListPresenter(mockView, mockModel)

    }

    @Test
    fun when_getting_users_list_then_show_progres_bar() {
        //given
        //presenter.(mockView)

        //when
        presenter.getUsersList()

        //then
        then(mockView).should().showProgress()
    }


}