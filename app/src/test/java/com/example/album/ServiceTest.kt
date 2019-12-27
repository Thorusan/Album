package com.example.album


import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


class ServiceTest {
    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    var service: ApiService? = null

    @InjectMocks
    var userService: NetworkDataProvider? = null

    @Test
    fun emptyTest() {
        userService?.getUsers()
    }




}