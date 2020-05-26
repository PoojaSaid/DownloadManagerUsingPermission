package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 data class Proposer (
    @SerializedName("ClientFullName")
    @Expose
    var clientFullName: String? = null,

    @SerializedName("ClientId")
    @Expose
    var clientId: String? = null,

    @SerializedName("DOB")
    @Expose
    var dOB: String? = null,

    @SerializedName("EmailId")
    @Expose
    var emailId: String? = null,

    @SerializedName("FirstName")
    @Expose
    var firstName: String? = null,

    @SerializedName("IsMale")
    @Expose
    var isMale: Boolean? = null,

    @SerializedName("LastName")
    @Expose
    var lastName: String? = null,

    @SerializedName("MiddleName")
    @Expose
    var middleName: String? = null,

    @SerializedName("MobileNumber")
    @Expose
    var mobileNumber: String? = null

)