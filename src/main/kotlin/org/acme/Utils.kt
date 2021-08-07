package org.acme

import java.util.logging.Logger
import javax.transaction.Transactional

fun onHandleError(e: Exception) {
    Logger.getLogger("QUARKUS").info(e.toString())
}

fun safeCall(
    onError: (Exception) -> Unit = ::onHandleError,
    block: () -> Unit,
) {
    try {
        block()
    } catch (e: Exception) {
        onError(e)
    }
}

@Transactional(Transactional.TxType.REQUIRES_NEW)
fun newTransaction(block: () -> Unit) {
    block()
}

fun safeCallTransaction(
    onError: (Exception) -> Unit = ::onHandleError,
    block: () -> Unit,
) {
    safeCall(onError = onError) {
        newTransaction {
            block()
        }
    }
}
