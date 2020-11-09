package com.deadely.itgenglish.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deadely.itgenglish.R
import com.deadely.itgenglish.extensions.afterTextChanged
import com.deadely.itgenglish.extensions.makeGone
import com.deadely.itgenglish.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservable()
        setListeners()
    }

    private fun setListeners() {
        etUsername.afterTextChanged {
            loginViewModel.loginDataChanged(
                etUsername.text.toString(),
                etPassword.text.toString()
            )
        }

        etPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    etUsername.text.toString(),
                    etPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            etUsername.text.toString(),
                            etPassword.text.toString()
                        )
                }
                false
            }

            btnLogin.setOnClickListener {
                pvLoading.makeVisible()
                loginViewModel.login(etUsername.text.toString(), etPassword.text.toString())
            }
        }
    }

    private fun initObservable() {
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(
            this@LoginActivity,
            Observer {
                val loginState = it ?: return@Observer
                btnLogin.isEnabled = loginState.isDataValid
                loginState.usernameError?.let {
                    etUsername.error = getString(loginState.usernameError)
                }
                loginState.passwordError?.let {
                    etUsername.error = getString(loginState.passwordError)
                }
            }
        )

        loginViewModel.loginResult.observe(
            this@LoginActivity,
            Observer {
                val loginResult = it ?: return@Observer

                pvLoading.makeGone()
                loginResult.error?.let {
                    showLoginFailed(loginResult.error)
                }
                loginResult.success?.let {
                    updateUiWithUser(loginResult.success)
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
        )
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
