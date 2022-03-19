package kpfu.itis.valisheva.android_app.domain.exceptions

import java.lang.RuntimeException

class NotFoundLocationException(message: String?, cause: Throwable?) :
    RuntimeException(message, cause)
