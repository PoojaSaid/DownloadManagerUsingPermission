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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.*
import java.util.zip.ZipInputStream


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val ACTIVITY2 = 2
        private val TAG = MainActivity::class.java.simpleName
        const val PERMISSION_EXTERNALSTORAGE_CODE = 1000
    }

    private var downloadID: Long = 0

    var downloadFilePath: File? = null

    val downloadUrl = "http://151.106.35.177:1006/DownloadZip.aspx?ProductId=21029&Data=ProductData"

    private val isExist = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mDownloadExternalBtn = findViewById<Button>(R.id.btn_download)
        mDownloadExternalBtn.setOnClickListener(this)

        var mCopyBtn = findViewById<Button>(R.id.btn_copy)
        mCopyBtn.setOnClickListener(this)

        var mUnzipBtn = findViewById<Button>(R.id.btn_unzip)
        mUnzipBtn.setOnClickListener(this)

        var mGsonArrayBtn = findViewById<Button>(R.id.btn_Gson)
        mGsonArrayBtn.setOnClickListener(this)

        var mListRiderBtn = findViewById<Button>(R.id.btn_riderList)
        mListRiderBtn.setOnClickListener(this)

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
                    downloadFile(downloadUrl)
                }
            } else {
                //OS is less than the marshmellow then perform download
                downloadFile(downloadUrl)

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

        if (view.id == R.id.btn_unzip) {
            if (downloadID > 0) {
                startUnzipping()
                Toast.makeText(this@MainActivity, "Unzip file successfully", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@MainActivity, "First download the file and then Unzip it.", Toast.LENGTH_SHORT).show()
            }
        }

        if (view.id == R.id.btn_Gson) {
            var result = convertToGson()
            Toast.makeText(this@MainActivity, "Conversion successful $result", Toast.LENGTH_SHORT).show()
        }

        if (view.id == R.id.tv_riderID) {

            val intent = Intent(this@MainActivity, com.example.downloadmanager.RiderDisplayModel::class.java)

            startActivityForResult(intent, ACTIVITY2)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_EXTERNALSTORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile(downloadUrl)
                } else {
                    //Permission denied from popup, show error message
                    Toast.makeText(this, "Permisssion denied......", Toast.LENGTH_SHORT).show()
                }
            }

            ACTIVITY2 -> {

            }

        }
    }

    fun downloadFile(downloadUrl: String) {

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        downloadFilePath = File(Environment.getDataDirectory(), "productID.zip")

        val sourcePath = downloadFilePath!!.absolutePath
        try {
            if (manager != null) {
                val request = DownloadManager.Request(Uri.parse(downloadUrl))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setTitle("Download") //Set title in download notification
                        .setDescription("Downloading file.....")
                        .setAllowedOverRoaming(true) //Set description in download notification
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                        .setMimeType(getMimeType(Uri.parse(downloadUrl)))
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, sourcePath)
                downloadID = manager.enqueue(request)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            Log.e("ERROR:MAIN", "E: " + e.toString())
        }

    }

    fun convertToGson(): String {

        var jsonStr ="{\"LA\":{\"IsSmoker\":false,\"IsJNKResident\":null,\"ClientId\":null,\"ClientFullName\":\"Purshottam Thakkar\",\"FirstName\":null,\"MiddleName\":null,\"LastName\":null,\"IsMale\":true,\"DOB\":\"20 Apr 1980\",\"MobileNumber\":\"8169938807\",\"EmailId\":\"tano88@yahoo.com\"},\"Proposer\":{\"ClientId\":null,\"ClientFullName\":\"PurshottamDad Thakkar\",\"FirstName\":null,\"MiddleName\":null,\"LastName\":null,\"RelationWithProposer\":\"Brother\",\"IsMale\":false,\"DOB\":\"20 Jan 1980\",\"MobileNumber\":\"9773097566\",\"EmailId\":\"test@gmail.com\"},\"lstRider\":[{\"RiderId\":\"30004\",\"RiderName\":\"Future Generali Linked Accidental Death Rider\",\"RiderCode\":null,\"RiderBasePremium\":null,\"GST\":45,\"InstallmentPremium\":250,\"AnnualPremiumAmount\":295,\"PremiumServiceTax\":null,\"ExTaxPremium\":null,\"PolicyTerm\":10,\"PremiumPayingTerm\":5,\"SumAssured\":500000,\"ProtectionCover\":null,\"ExBackDatePremium\":null,\"BackDatePremium\":null}],\"lstFund\":[{\"FundID\":\"21001\",\"FundCode\":null,\"FundComposition\":\"5\"},{\"FundID\":\"21005\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21004\",\"FundCode\":null,\"FundComposition\":\"15\"},{\"FundID\":\"21003\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21002\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21007\",\"FundCode\":null,\"FundComposition\":\"25\"},{\"FundID\":\"21006\",\"FundCode\":null,\"FundComposition\":\"25\"}],\"ProductCode\":\"U44\",\"ProductId\":\"21022\",\"QuoteNo\":null,\"ProductName\":\"Future Generali Dhan Vridhi \",\"BasePremium\":null,\"AgeProofDocCode\":\"LEVGCERT\",\"GST\":0,\"isLAProposerSame\":\"0\",\"SumAssured\":500000,\"InstallmentPremium\":50000,\"AnnualPremiumAmount\":50000,\"ReferenceNumber\":\"PQRS\",\"TotalPremiumwithGST\":50000,\"PremiumServiceTax\":null,\"ExTaxPremium\":null,\"PolicyFees\":null,\"PolicyTerm\":\"10\",\"PremiumPayingTerm\":\"5\",\"PaymentFrequency\":\"1\",\"ProtectionCover\":null,\"IsBackDateRequired\":null,\"BackDate\":null,\"ExBackDatePremium\":null,\"BackDatePremium\":null,\"FundValueFor4\":null,\"FundValueFor8\":null,\"PreferedRenewalMode\":null,\"IsUlip\":true,\"IsNSAP\":false,\"AgeProof\":\"Leaving Certificate\",\"IsStaff\":true,\"LifeStage\":\"1\",\"ProposerAnnualIncome\":\"5.0\",\"RiskApproach\":\"3\",\"ExistingSumAssured\":\"0.0\",\"No.ofPolicies\":\"0\",\"LifeGoal\":\"2\",\"CurrentValueOfGoal\":\"15\",\"TimetoAccomplish\":\"15\",\"FutureValueOfGoal\":\"36\",\"PdfData\":{\"PdfName\":\"M002200140\",\"PdfPath\":\"\\/storage\\/emulated\\/0\\/Documents\\/FutureGenerali\\/M002200140.pdf\"}}"
        jsonStr = jsonStr.replace("\\/".toRegex(), "")

        // Creates new instance of Gson
        val gson = Gson()

        try {

            val jsonObject: Example = gson.fromJson(jsonStr, Example::class.java)

            var result = jsonObject.lstFund.toString()
            var riderList = jsonObject.lstFund!!.toList()

            //print(result)
            Log.e(TAG, "Result $result")
            Log.e(TAG, "Rider list  $riderList")


            return (result)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    var onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) { //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                try {
                    Toast.makeText(this@MainActivity, "Download Zip file successfully.", Toast.LENGTH_SHORT).show()
//                    startUnzipping()
//                    copyFile()
//                    Toast.makeText(this@MainActivity, "file copy successfully.", Toast.LENGTH_SHORT).show()

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
        val sdCard = Environment.getExternalStorageDirectory().toString()
        //val sdCard = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()

        val mydir: File = application.getDir("mydir", Context.MODE_PRIVATE) //Creating an internal dir;

        CommonMethod.log(TAG, "Internal directory path " + mydir.absoluteFile)

        val fileWithinMyDir = File(mydir, "myfile.zip") //Getting a file within the dir.

        // the file to be moved or copied
        val sourceLocation = File("$sdCard/Download/data/productID.zip")

        // make sure your target location folder exists!
        //val targetLocation = File("$sdCard/MyNewFolder/Derby.pdf")
        //val targetLocation = File("$sdCard/Download/Derby2.pdf")
        val targetLocation = fileWithinMyDir

        // just to take note of the location sources
        CommonMethod.log(TAG, "sourceLocation: $sourceLocation")
        CommonMethod.log(TAG, "targetLocation: $targetLocation")

        try {
            // 1 = move the file, 2 = copy the file
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

    private fun startUnzipping() {

        val sdCard = Environment.getExternalStorageDirectory().toString()
        //Creating an internal dir;
        val mydir: File = application.getDir("mydir", Context.MODE_PRIVATE)

        CommonMethod.log(TAG, "Internal directory path " + mydir.absoluteFile)

        //Getting a file within the dir.
        val fileWithinMyDir = File(mydir, "myfile.zip")
        val targetPath = fileWithinMyDir.absolutePath
        // the file to be moved or copied
        val sourcePath = File("$sdCard/Download/data/productID.zip").absolutePath
        try {
            unzip(sourcePath, targetPath)
            /* val unZipFilePath= unzip(sourcePath, targetPath)

             if(unZipFilePath.length == 0){
                 CommonMethod.log(TAG, "Unzip function is not exist")
             }*/

        } catch (e: java.lang.Exception) {
            CommonMethod.log(TAG, "Exception while unzipping $e")
        }
    }

    private fun unzip(zipFilePath: String, destDir: String): String {
        val zipFile = File(zipFilePath)
        CommonMethod.log(TAG, "Source path passed " + zipFile.absolutePath)
        if (!zipFile.exists()) {
            CommonMethod.log(TAG, "Returning because source file does not exist $isExist")
            return "Empty"
        }
        var filePath: String? = null
        val fis: FileInputStream
        //buffer for read and write data to file
        val buffer = ByteArray(1024)
        try {
            fis = FileInputStream(zipFilePath)
            val zis = ZipInputStream(fis)
            var ze = zis.nextEntry
            while (ze != null) {
                val fileName = ze.name
                val newFile = File(destDir + File.separator + fileName)
                CommonMethod.log(TAG, "Unzipping to " + newFile.absolutePath)
                filePath = newFile.absolutePath
                //create directories for sub directories in zip
                File(newFile.parent).mkdirs()
                val fos = FileOutputStream(newFile)
                var len: Int
                while (zis.read(buffer).also { len = it } > 0) {
                    fos.write(buffer, 0, len)
                }
                fos.close()
                //close this ZipEntry
                zis.closeEntry()
                ze = zis.nextEntry
            }

            //close last ZipEntry
            zis.closeEntry()
            zis.close()
            fis.close()
            return filePath.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            CommonMethod.log(TAG, "IO Exception while unzipping $e")
        }
        return "Exception:Main"
    }
}