package com.hfm.saghi

import com.intellij.ui.scale.JBUIScale.scale
import com.intellij.util.io.HttpRequests
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.awt.Font
import java.awt.FontFormatException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

object PoemsRepository {
    private val scope = CoroutineScope(Job() + CoroutineName("saghi caller scope"))
    val font = try {
            Font.createFont(Font.PLAIN, Objects.requireNonNull(javaClass.getResourceAsStream("/Vazir-Thin.ttf")))
                .deriveFont(scale(14f))
        } catch (e: FontFormatException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }


    var verse = decode("2b7YsyDYp9iyINqG2YbYr9uM2YYg2LTaqduM2KjYp9uM24wg2LTYqNuMINuM2Kcg2LHYqCDYqtmI2KfZhiDYr9uM2K/ZhiAgINqp2Ycg2LTZhdi52ZAg2K/bjNiv2Ycg2KfZgdix2YjYstuM2YUg2K/YsSDZhdit2LHYp9io2ZAg2KfYqNix2YjbjNiq")
        private set


    fun pickNewPoems() {
        scope.coroutineContext.cancelChildren()
        scope.launch(Dispatchers.IO) {
            try {
                val string =
                    HttpRequests.request("https://saghi-git-main-heftekharms-projects.vercel.app/api/v1/random?encode=true")
                        .readString()
                val raw: List<List<String>> = Json.decodeFromString(string)
                if (raw.isNotEmpty())
                    verse = raw.joinToString("      ") { verse -> verse.joinToString("  ") { decode(it) } }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun decode(b64: String) = String(
        Base64.getDecoder().decode(
            b64
        ), StandardCharsets.UTF_8
    )
}