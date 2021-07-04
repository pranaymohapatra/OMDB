package com.example.omdb.helpers

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.launchOrError(
    func: suspend CoroutineScope.() -> Unit,
    error: (error: Throwable) -> Unit
): Job {
    return launch {
        try {
            func.invoke(this)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            if (throwable !is CancellationException) {
                error.invoke(throwable)
            }
            Job()
        }
    }
}