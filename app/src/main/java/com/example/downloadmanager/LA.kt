package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LA {
    @SerializedName("ClientFullName")
    @Expose
    var clientFullName: String? = null

    @SerializedName("ClientId")
    @Expose
    var clientId: Any? = null

    @SerializedName("DOB")
    @Expose
    var dOB: String? = null

    @SerializedName("EmailId")
    @Expose
    var emailId: String? = null

    @SerializedName("FirstName")
    @Expose
    var firstName: Any? = null

    @SerializedName("IsJNKResident")
    @Expose
    var isJNKResident: Any? = null

    @SerializedName("IsMale")
    @Expose
    var isMale: Boolean? = null

    @SerializedName("IsSmoker")
    @Expose
    var isSmoker: Boolean? = null

    @SerializedName("LastName")
    @Expose
    var lastName: Any? = null

    @SerializedName("MiddleName")
    @Expose
    var middleName: Any? = null

    @SerializedName("MobileNumber")
    @Expose
    var mobileNumber: String? = null

}