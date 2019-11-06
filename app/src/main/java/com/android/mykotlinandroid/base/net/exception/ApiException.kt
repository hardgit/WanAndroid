package com.android.mykotlinandroid.base.net.exception

class ApiException(val errorCode: Int, errorMessage: String) : RuntimeException(errorMessage)