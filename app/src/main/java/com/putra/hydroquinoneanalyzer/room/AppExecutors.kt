package com.putra.hydroquinoneanalyzer.room

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(private var diskIO: Executor?,private var mainThread: Executor?,
                   private var networkIO: Executor?) {

    fun diskIO(): Executor? {
        return diskIO
    }

    fun mainThread(): Executor? {
        return mainThread
    }

    fun networkIO(): Executor? {
        return networkIO
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler =
            Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object{
        private var LOCK = Any()
        private var sInstance: AppExecutors? = null

        fun getInstance(): AppExecutors? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = AppExecutors(
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        MainThreadExecutor()
                    )
                }
            }
            return AppExecutors.sInstance
        }
    }
}