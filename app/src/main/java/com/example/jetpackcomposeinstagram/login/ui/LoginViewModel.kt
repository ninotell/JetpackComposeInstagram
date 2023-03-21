package com.example.jetpackcomposeinstagram.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeinstagram.login.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginUseCase = LoginUseCase()

    private val _email = MutableLiveData<String>();
    val email: LiveData<String> = _email;

    private val _password = MutableLiveData<String>();
    val password: LiveData<String> = _password;

    private val _isLoginEnabled = MutableLiveData<Boolean>();
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled;

    private val _isLoading = MutableLiveData<Boolean>();
    val isLoading: LiveData<Boolean> = _isLoading;

    fun onLoginChanged(email: String, password: String) {
        _email.value = email;
        _password.value = password
        _isLoginEnabled.value = enableLogin(email, password)
    }

    fun enableLogin(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6
    }

    fun onLoginSelected(){
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase(email.value!!, password.value!!)
            if (result){
                //Navitage to Screen2
                Log.i("Nino", "result OK")
            }
            _isLoading.value = false
        }
    }
}