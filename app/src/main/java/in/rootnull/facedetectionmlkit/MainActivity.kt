package `in`.rootnull.facedetectionmlkit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import java.io.File
import java.io.IOException
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val imageName = "myPhoto.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image: InputImage
        try {
            image = InputImage.fromFilePath(
                this,
                Uri.fromFile(
                    File(
                        Environment.getExternalStorageDirectory().absolutePath
                                + "/FaceDetect/$imageName"
                    )
                )
            )

            val detector = FaceDetection.getClient()
            val result = detector.process(image)
                .addOnSuccessListener { faces ->
                    for (face in faces) {
                        val bounds = face.boundingBox
                        val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
                        val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

                        println(bounds.left)
                        println(bounds.right)
                        println(bounds.top)
                        println(bounds.bottom)

                        val intent = Intent(this, ImageProcessingIntentService::class.java)
                        val bundle = Bundle()
                        bundle.putInt("left", bounds.left);
                        bundle.putInt("right", bounds.right);
                        bundle.putInt("top", bounds.top);
                        bundle.putInt("bottom", bounds.bottom);
                        bundle.putSerializable("file", File(
                            Environment.getExternalStorageDirectory().absolutePath
                                    + "/FaceDetect/$imageName"
                        ))
                        intent.putExtras(bundle)
                        startService(intent)

                        /*// If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                        // nose available):
                        val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
                        leftEar?.let {
                            val leftEarPos = leftEar.position
                        }

                        // If contour detection was enabled:
                        val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
                        val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points

                        // If classification was enabled:
                        if (face.smilingProbability != null) {
                            val smileProb = face.smilingProbability
                        }
                        if (face.rightEyeOpenProbability != null) {
                            val rightEyeOpenProb = face.rightEyeOpenProbability
                        }

                        // If face tracking was enabled:
                        if (face.trackingId != null) {
                            val id = face.trackingId
                        }*/
                    }
                }
                .addOnFailureListener(Exception::printStackTrace)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}