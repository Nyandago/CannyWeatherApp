package com.cannybits.cannyweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    val CITY: String = "dodoma,tz"
    val API: String = "91b54cb3ec1afcb7a8fd49835a557d32" // from open weather map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Presenter().execute()
    }

    class Presenter : CoroutineScope {
        private var job: Job = Job()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job // to run code in Main(UI) Thread

        // call this method to cancel a coroutine when you don't need it anymore,
        // e.g. when user closes the screen
        fun cancel() {
            job.cancel()
        }

        fun execute() = launch {
            onPreExecute()
            val result = doInBackground() // runs in background thread without blocking the Main Thread
            onPostExecute(result)
        }

        private suspend fun doInBackground(): String = withContext(Dispatchers.IO) { // to run code in Background Thread
            // do async work
            delay(1000) // simulate async work
            return@withContext "SomeResult"
        }

        // Runs on the Main(UI) Thread
        private fun onPreExecute() {
            // show progress
        }

        // Runs on the Main(UI) Thread
        private fun onPostExecute(result: String) {
            // hide progress
        }
    }
}

