package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PdfData (
    @SerializedName("PdfName")
    @Expose
    var pdfName: String? = null,

    @SerializedName("PdfPath")
    @Expose
    var pdfPath: String? = null

)