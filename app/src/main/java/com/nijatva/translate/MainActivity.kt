package com.nijatva.translate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nijatva.signature.SignatureTheme
import com.nijatva.translate.ui.NijatvaTranslateApp
import com.nijatva.translate.ui.TranslateViewModel
import com.nijatva.translate.ui.theme.NijatvaTranslateTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedText = incomingText(intent)
        val accent = SignatureTheme.accent(this)
        val dark = SignatureTheme.isDark(this)

        setContent {
            NijatvaTranslateTheme(darkTheme = dark, accent = accent) {
                val viewModel: TranslateViewModel = viewModel(
                    factory = TranslateViewModel.factory(applicationContext),
                )
                NijatvaTranslateApp(viewModel = viewModel, sharedText = sharedText)
            }
        }
    }

    /** Text handed to us by the share sheet or by "Translate" in a text selection menu. */
    private fun incomingText(intent: Intent?): String? = when (intent?.action) {
        Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)
        Intent.ACTION_PROCESS_TEXT ->
            intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
        else -> null
    }
}
