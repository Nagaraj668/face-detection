package `in`.rootnull.facedetectionmlkit

import android.app.IntentService
import android.content.Intent
import android.graphics.Rect
import java.io.File

class ImageProcessingIntentService :
    IntentService(BROADCAST_MESSAGE_NAME) {
    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val bundle = intent.extras
            val file = bundle!!.getSerializable("file") as File?
            val imageProcessing = ImageProcessing()
            val left = bundle.getInt("left")
            val right = bundle.getInt("right")
            val top = bundle.getInt("top")
            val bottom = bundle.getInt("bottom")

            val rect = Rect(left, top, right, bottom)
            imageProcessing.process(file, rect)
        }
    }

    companion object {
        const val BROADCAST_MESSAGE_NAME = "ImageProcessingIntentService"
    }
}