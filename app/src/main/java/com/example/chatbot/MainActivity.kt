package com.example.chatbot

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatbot.ui.theme.ChatBotTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.lang.IllegalArgumentException

class MainActivity : ComponentActivity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val question = findViewById<EditText>(R.id.Question)
        val sbt = findViewById<Button>(R.id.Submit)
        val txtResponse = findViewById<TextView>(R.id.Response)

        sbt.setOnClickListener {
            val ques = question.text.toString()
            Toast.makeText(this,ques,Toast.LENGTH_LONG).show()
            val response = getResponse(ques){response ->
                runOnUiThread{
                    txtResponse.text = response
                }
            }
        }
    }

    private fun getApiKeyFromFile(context : Context) : String{
        val cont = R.string.api_key
        return context.getString(cont)
    }

    private fun getResponse(question: String, callback: (String) -> Unit){
    val api = getApiKeyFromFile(this)
    Log.v("data",api)
    val post = "https://api.openai.com/v1/chat/completions"

    val requestBody = """
        {
                "model": "gpt-3.5-turbo",
                "messages": [
                    {
                        "role": "system",
                        "content": "You are a helpful assistant."
                    },
                    {
                        "role": "user",
                        "content": "$question"
                    }
                ]
            }
    """.trimIndent()
    val request = Request.Builder().url(post)
        .addHeader("Content-type","application/json")
        .addHeader("Authorization", "Bearer $api")
        .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
        .build()

    client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if(body != null){
                    Log.v("data", body)
                }else{
                    Log.v("data", "empty")
                }
            }
        }    )
    }


    }
