package com.pingfangx.demo.apkstructuredemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        val text = "${stringFromJNI()}\n${getStringFromAssets()}\n${getStringFromRaw()}"
        sample_text.text = text
    }

    private fun getStringFromAssets(): String {
        return try {
            getStringFromInputStream(assets.open("sub/demo.txt"))
        } catch (e: Exception) {
            ""
        }
    }

    private fun getStringFromRaw(): String {
        return try {
            getStringFromInputStream(resources.openRawResource(R.raw.raw_demo))
        } catch (e: Exception) {
            ""
        }
    }

    private fun getStringFromInputStream(inputStream: InputStream?): String {
        var inputStreamReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        var result: String? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            result = bufferedReader.readLines().joinToString()
        } catch (e: Exception) {
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
            }
            try {
                inputStreamReader?.close()
            } catch (e: Exception) {
            }
            try {
                bufferedReader?.close()
            } catch (e: Exception) {
            }
        }
        return result ?: ""
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
