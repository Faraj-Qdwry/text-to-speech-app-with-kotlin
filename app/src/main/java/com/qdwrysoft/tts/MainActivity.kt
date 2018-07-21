package com.qdwrysoft.tts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity()  , TextToSpeech.OnInitListener{

    lateinit var textToSpeech : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(this,this)

        speekBtton.setOnClickListener {
            speek()
        }
    }

    private fun speek() {
        textToSpeech.speak(editText.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
        editText.text.clear()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            with(textToSpeech.setLanguage(Locale.US)){
                if (this == TextToSpeech.LANG_MISSING_DATA || this == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language is not supported")
                } else {
                    speekBtton.setEnabled(true)
                    speek()
                }
            }
        } else {
            Log.e("TTS", "Initilization Failed")
        }

    }

    override fun onDestroy() {
        textToSpeech?.let {
            with(it){
                stop()
                shutdown()
            }
        }
        super.onDestroy()
    }
}