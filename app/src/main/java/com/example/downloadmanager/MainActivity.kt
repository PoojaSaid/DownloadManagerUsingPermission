package com.example.downloadmanager

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadmanager.MainActivity
import java.io.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var downloadID: Long = 0

    var downloadFilePath: File? = null

    val downloadUrl = "http://speedtest.ftp.otenet.gr/files/test10Mb.db"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mDownloadExternalBtn = findViewById<Button>(R.id.btn_download)
        mDownloadExternalBtn.setOnClickListener(this)

        var mCopyBtn = findViewById<Button>(R.id.btn_copy)
        mCopyBtn.setOnClickListener(this)

        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_download) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //Permission denied, request it
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                } else {
                    //Permission already granted it then perform download
                    startDownloadingInExternal(downloadUrl)
                }
            } else {
                //OS is less than the marshmellow then perform download
                startDownloadingInExternal(downloadUrl)

            }
        }
        if (view.id == R.id.btn_copy) {

            if (downloadID > 0) {
                copyFile()
                Toast.makeText(this@MainActivity, "Copy file successfully", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@MainActivity, "First download the file and then copy it.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_EXTERNALSTORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloadingInExternal(downloadUrl)

                } else {
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun startDownloadingInExternal(downloadUrl: String) {

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        downloadFilePath = File(Environment.getDataDirectory(), "test10Mb.db")

        val sourcePath = downloadFilePath!!.absolutePath
        try {
            if (manager != null) {
                val request = DownloadManager.Request(Uri.parse(downloadUrl))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setTitle("Download") //Set title in download notification
                        .setDescription("Downloading file.....")
                        .setAllowedOverRoaming(true) //Set description in download notification
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setMimeType(getMimeType(Uri.parse(downloadUrl)))
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, sourcePath)
                downloadID = manager.enqueue(request)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            Log.e("ERROR:MAIN", "E: " + e.message)
        }

    }

    var onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) { //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                try {
                    Toast.makeText(this@MainActivity, "Download Completed", Toast.LENGTH_SHORT).show()
                    copyFile()
                    Toast.makeText(this@MainActivity, "file copy successfully.", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    e.message
                }
            }
        }
    }

    private fun getMimeType(uri: Uri): String? {
        val resolver = contentResolver
        val mimeTypemap = MimeTypeMap.getSingleton()
        return mimeTypemap.getExtensionFromMimeType(resolver.getType(uri))
    }


    fun copyFile() {
        // your sd card
        // your sd card
        val sdCard = Environment.getExternalStorageDirectory().toString()
        //val sdCard = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()

        val mydir: File = application.getDir("mydir", Context.MODE_PRIVATE) //Creating an internal dir;

        CommonMethod.log(TAG, "Internal directory path " + mydir.absoluteFile)

        val fileWithinMyDir = File(mydir, "myfile.db") //Getting a file within the dir.

        // the file to be moved or copied
        // the file to be moved or copied
        val sourceLocation = File("$sdCard/Download/data/test10Mb.db")

        // make sure your target location folder exists!
        //val targetLocation = File("$sdCard/MyNewFolder/Derby.pdf")
        //val targetLocation = File("$sdCard/Download/Derby2.pdf")
        val targetLocation = fileWithinMyDir

        // just to take note of the location sources
        // just to take note of the location sources
        CommonMethod.log(TAG, "sourceLocation: $sourceLocation")
        CommonMethod.log(TAG, "targetLocation: $targetLocation")

        try { // 1 = move the file, 2 = copy the file
            val actionChoice = 2
            // moving the file to another directory
            if (actionChoice == 1) {
                if (sourceLocation.renameTo(targetLocation)) {
                    CommonMethod.log(TAG, "Move file successful.")
                } else {
                    CommonMethod.log(TAG, "Move file failed.")
                }
            } else { // make sure the target file exists
                if (sourceLocation.exists()) {
                    val inputStream: InputStream = FileInputStream(sourceLocation)
                    val out: OutputStream = FileOutputStream(targetLocation)
                    // Copy the bits from instream to outstream
                    val buf = ByteArray(1024)
                    var len: Int
                    while (inputStream.read(buf).also { len = it } > 0) {
                        out.write(buf, 0, len)
                    }
                    inputStream.close()
                    out.close()
                    CommonMethod.log(TAG, "Copy file successful.")
                } else {
                    CommonMethod.log(TAG, "Copy file failed. Source file missing.")
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
            CommonMethod.log(TAG, "Null exception $e")
        } catch (e: Exception) {
            e.printStackTrace()
            CommonMethod.log(TAG, "Exception $e")
        }
    }
    companion object {
        private const val TAG = "MainActivity"
        const val PERMISSION_EXTERNALSTORAGE_CODE = 1000
    }

}