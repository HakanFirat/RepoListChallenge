package com.example.repolistchallenge.network.model

import com.example.repolistchallenge.network.ErrorAction
import com.example.repolistchallenge.utils.Constant

class Error(action: ErrorAction?, code: String?, message: String?) {
    var Action: ErrorAction? = action
        get() = if (field == null)
            ErrorAction.UnExpected
        else
            field
    var ErrorCode: String? = code
        get() = if (field == null)
            "0"
        else
            field
    var ErrorMessage: String? = message
        get() = if (field == null)
            Constant.API_CALL_ERROR_MESSAGE
        else
            field
}