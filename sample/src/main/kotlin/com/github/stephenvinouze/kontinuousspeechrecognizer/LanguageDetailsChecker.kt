package com.github.stephenvinouze.kontinuousspeechrecognizer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import java.util.*

class LanguageDetailsChecker : BroadcastReceiver() {

    var doAfterReceive: OnLanguageDetailsListener? = null

    /**
     * @return the supportedLanguages
     */
    var supportedLanguages: ArrayList<Pair<String, String>>? = null
        private set

    /**
     * @return the languagePreference
     */
    var languagePreference: String? = null
        private set


    interface OnLanguageDetailsListener {
        fun onLanguageDetailsReceived(receiver: LanguageDetailsChecker)
    }

    init {
        supportedLanguages = ArrayList()
    }

    fun printBundle(bundle: Bundle?) {
        if (bundle == null) {
            Log.v(TAG, "Empty bundle.")
            return
        }
        for (key in bundle.keySet()) {
            val value = bundle.get(key)
            Log.d(TAG, String.format("%s %s (%s)", key, "" + value!!, if (value != null) value.javaClass.name else " null"))
        }
    }

    override fun onReceive(context: Context, intent: Intent) {

        val results = getResultExtras(true)

        printBundle(results)

        if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
            languagePreference = results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)
        }
        if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
            val languages = results.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)
            val languageNames = results.getStringArrayList("android.speech.extra.SUPPORTED_LANGUAGE_NAMES")

            languages.forEachIndexed { index, language -> supportedLanguages?.add(Pair(language, languageNames[index])) }
        }

        doAfterReceive?.onLanguageDetailsReceived(this)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Language Preference: ").append(languagePreference)
                .append("\n")
        sb.append("languages supported: ").append("\n")
        for (lang in supportedLanguages!!) {
            sb.append(" ").append(lang).append("\n")
        }
        return sb.toString()
    }

    companion object {
        private val TAG = "LanguageDetailsChecker"
    }
}