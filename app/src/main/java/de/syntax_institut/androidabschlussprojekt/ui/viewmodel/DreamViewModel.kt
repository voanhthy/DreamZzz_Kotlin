package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.remote.api.DreamImageApi
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import de.syntax_institut.androidabschlussprojekt.BuildConfig

class DreamViewModel: ViewModel() {

    private val TAG = "DreamViewModel"

    val dreamImageApi = DreamImageApi.retrofitService

    private val _dreamImage = MutableStateFlow<DreamImage?>(null)
    val dreamImage = _dreamImage.asStateFlow()

    // TODO: Stil integrieren
    fun fetchImage(prompt: String, apikey: String) {
        viewModelScope.launch {
            try {
                val response = dreamImageApi.generateImage(
                    authHeader = "Bearer ${BuildConfig.API_KEY}",
                    request = DreamImageRequest(prompt, model = "dall-e-2", n = 1, size = "1024x1024"))
            } catch (e: Exception) {
                Log.e(TAG, "Error: $e")
            }
        }
    }
}