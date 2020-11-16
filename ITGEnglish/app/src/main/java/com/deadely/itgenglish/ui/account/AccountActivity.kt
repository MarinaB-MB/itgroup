package com.deadely.itgenglish.ui.account

import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseActivity
import com.deadely.itgenglish.utils.FieldConverter

class AccountActivity : BaseActivity(R.layout.activity_account) {
    override fun initView() {
        title = FieldConverter.getString(R.string.account_title)
    }

    override fun setListeners() {

    }

    override fun initObserver() {

    }

    override fun getExtras() {

    }
}