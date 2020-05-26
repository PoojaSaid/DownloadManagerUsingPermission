package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LstFund(

        @SerializedName("FundID")
        @Expose
        var fundID: String? = null,

        @SerializedName("FundCode")
        @Expose
        var fundCode: String? = null,

        @SerializedName("FundComposition")
        @Expose
        var fundComposition: String? = null

)