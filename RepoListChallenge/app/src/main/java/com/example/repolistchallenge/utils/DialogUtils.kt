package com.example.repolistchallenge.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.repolistchallenge.R
import com.example.repolistchallenge.network.ApiError
import java.lang.Exception

object DialogUtils {

    fun handleApiError(
        context: Context?,
        error: ApiError? = ApiError.createErrorFromString(),
        asDialog: Boolean = false
    ) {
        handleError(context, error?.Error?.ErrorMessage, asDialog)
    }

    fun handleError(context: Context?, error: String?, asDialog: Boolean = false) {
        showMessage(context, error ?: "Beklenmeyen bir hata oluştu!", asDialog)
    }

    fun showMessage(context: Context?, message: String, asDialog: Boolean = false) {
        //  showDialog(context, message) {}

        if (asDialog)
            showDialog(context, message) {}
        else
            context.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
    }

    fun showDialog(context: Context?, message: String?, positiveButtonPressed: () -> Unit) {
        try {
            context?.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage(message)
                builder.setCancelable(false)

                builder.setPositiveButton(R.string.ok) { _, _ ->
                    positiveButtonPressed()
                }

                builder.show()
            }
        }catch (e: Exception){
            positiveButtonPressed()
        }
    }
}