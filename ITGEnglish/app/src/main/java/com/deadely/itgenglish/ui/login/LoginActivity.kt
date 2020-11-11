package com.deadely.itgenglish.ui.login

import android.content.Intent
import android.graphics.Paint
import androidx.activity.viewModels
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseActivity
import com.deadely.itgenglish.extensions.makeGone
import com.deadely.itgenglish.extensions.makeVisible
import com.deadely.itgenglish.ui.main.MainActivity
import com.deadely.itgenglish.utils.DataState
import com.deadely.itgenglish.utils.FieldConverter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*


@AndroidEntryPoint
class LoginActivity : BaseActivity(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    private var isLogin = false

    override fun initView() {
        supportActionBar?.hide()
        if (isLogin) {
            showLoginMode()
        } else {
            showRegMode()
        }
        tvUno.paintFlags = tvUno.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun showLoginMode() {
        btnLogin.text = getString(R.string.login)
        tvUno.text = getString(R.string.account_no_exist)
    }

    private fun showRegMode() {
        btnLogin.text = getString(R.string.register)
        tvUno.text = getString(R.string.is_account_exist)
    }

    override fun setListeners() {
        tvUno.setOnClickListener {
            loginViewModel.setMode(!isLogin)
        }
        btnLogin.setOnClickListener {
            if (loginViewModel.isEmailValid(etEmail.text.toString()) && loginViewModel.isPasswordValid(
                    etPassword.text.toString()
                )
            ) {
                if (isLogin) {
                    loginViewModel.login(etEmail.text.toString(), etPassword.text.toString())
                } else {
                    loginViewModel.getAuthToken(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                }
            }
        }
    }

    override fun initObserver() {
        loginViewModel.validError.observe(this, {
            it?.let {
                Snackbar.make(btnLogin, it, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        })
        loginViewModel.isLogin.observe(this, {
            isLogin = it
            if (isLogin) {
                showLoginMode()
            } else {
                showRegMode()
            }
        })
        loginViewModel.authToken.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    btnLogin.makeGone()
                    tvUno.makeGone()
                    rlFields.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    FieldConverter.getString(R.string.default_error)?.let { error ->
                        Snackbar.make(btnLogin, error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    }
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    if (it.data == "0") {
                        Snackbar.make(btnLogin, "Этот email уже занят", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    } else {
                        loginViewModel.setToken(it.data)
                        openMainScreen()
                    }
                }
            }
        })
        loginViewModel.loginToken.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    btnLogin.makeGone()
                    tvUno.makeGone()
                    rlFields.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    FieldConverter.getString(R.string.default_error)?.let { error ->
                        Snackbar.make(btnLogin, error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    }
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    if (it.data == "0") {
                        Snackbar.make(
                            btnLogin,
                            "Возникла ошибка при авторизации",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Action", null).show()
                    } else {
                        loginViewModel.setToken(it.data)
                        openMainScreen()
                    }
                }
            }
        }
        )
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun getExtras() {}
}
