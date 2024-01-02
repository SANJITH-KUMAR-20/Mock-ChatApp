package com.example.chatbot

import android.os.Bundle
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

    fun getResponse(question: String, callback: (Any) -> Unit){
    val api = "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"
    val post = "https://api.openai.com/v1/chat/completions"

    val requestBody = """
        
    """.trimIndent()
    }


    }
