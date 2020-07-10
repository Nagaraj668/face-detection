package `in`.rootnull.facedetectionmlkit

import android.graphics.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageProcessing {
    fun process(imageFile: File?, rect: Rect) {
        val myOptions = BitmapFactory.Options()
        myOptions.inDither = true
        myOptions.inScaled = false
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 // important
        myOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(imageFile!!.absolutePath, myOptions)

        val paint = Paint();
        paint.isAntiAlias = true;
        paint.color = Color.BLUE;

        val workingBitmap = Bitmap.createBitmap(bitmap);
        val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        val canvas = Canvas(mutableBitmap);
        canvas.drawLine(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(),
            rect.top.toFloat(), paint
        )

        canvas.drawLine(rect.left.toFloat(), rect.bottom.toFloat(), rect.right.toFloat(),
            rect.bottom.toFloat(), paint
        )

        canvas.drawLine(rect.left.toFloat(), rect.top.toFloat(), rect.left.toFloat(),
            rect.bottom.toFloat(), paint
        )

        canvas.drawLine(rect.right.toFloat(), rect.top.toFloat(), rect.right.toFloat(),
            rect.bottom.toFloat(), paint
        )

        try {
            FileOutputStream(imageFile)
                .use { out -> mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, out) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        println("Image processing is completed.")
    }
}