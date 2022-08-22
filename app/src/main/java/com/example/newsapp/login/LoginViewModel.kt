package com.example.newsapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.User

class LoginViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var users = mutableListOf<User>()

    init {
        users.add(User("abc", "1234"))
        users.add(User("xyz", "4567"))
    }

    fun canLogin():Boolean {
        for (user in users) {
            if (user.name == name.value && user.password == password.value)
            {
                _errorMessage.value = ""
                return true
            }
        }
        _errorMessage.value = "Invalid Name/Password"
        return false
    }

}