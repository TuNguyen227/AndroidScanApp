package com.nmt.stockcheck.view.exception

open class AppException(val exceptin: Throwable?): Exception();

open class NoNetworkException(exceptinNoNetwork: Throwable?): AppException(exceptinNoNetwork);