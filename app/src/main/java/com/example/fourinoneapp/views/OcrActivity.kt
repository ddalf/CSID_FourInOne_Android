package com.example.fourinoneapp.views

import com.googlecode.tesseract.android.TessBaseAPI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fourinoneapp.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class OcrActivity : AppCompatActivity() {
    internal var image: Bitmap? = null //사용되는 이미지
    private var mTess: TessBaseAPI? = null //Tess API reference
    internal var datapath = "" //언어데이터가 있는 경로

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr);

        //언어파일 경로
        datapath = "$filesDir/tesseract/"

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(File(datapath + "tessdata/"), "eng")
        checkFile(File(datapath + "tessdata/"), "kor")

        //Tesseract API
        val lang = "eng+kor"
        mTess = TessBaseAPI()
        mTess!!.init(datapath, lang)

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera"

        subDirList(path)
    }

    fun subDirList(source: String) { //디렉토리 재귀 탐색

        val dir = File(source)
        val fileList = dir.listFiles()

        try {

            for (i in fileList!!.indices) {

                val file = fileList[i]
                val nowPath = file.canonicalPath //파일의 경로

                if (file.isFile) { //파일 발견시

                    Log.d("파일 이름 = ", file.name)
                    processImage(nowPath)

                } else if (file.isDirectory) { //서브디렉토리 발견시
                    Log.d("디렉토리 이름 = ", file.name)

                    // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                    subDirList(file.canonicalPath)
                }
            }
        } catch (e: IOException) {

        }

    }

    //Process an Image
    fun processImage(nowPath: String) {
        //이미지 디코딩을 위한 초기화
        image = BitmapFactory.decodeFile(nowPath)
        mTess!!.setImage(image)
        val OCR_result = mTess!!.utF8Text
        Log.d("OCR 인식 결과", OCR_result)
    }

    //copy file to device
    private fun copyFiles(lang: String) {
        try {
            val filepath = datapath + "tessdata/" + lang + ".traineddata"
            if (!File(filepath).exists()) {
                val assetManager = assets

                val instream = assetManager.open("tessdata/$lang.traineddata")
                val outstream = FileOutputStream(filepath)

                val buffer = ByteArray(1024)
                var read: Int=0
                while (instream.read(buffer).also { read = it } >=0) {
                    outstream.write(buffer, 0, read)
                }

                outstream.flush()
                outstream.close()
                instream.close()

                val file = File(filepath)
                if (!file.exists()) {
                    throw FileNotFoundException()
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    //check file on the device
    private fun checkFile(dir: File, lang: String) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang)
        }
        if (dir.exists()) {
            val datafilePath = datapath + "tessdata/" + lang + ".traineddata"
            val datafile = File(datafilePath)
            if (!datafile.exists()) {
                copyFiles(lang)
            }
        }
    }
}