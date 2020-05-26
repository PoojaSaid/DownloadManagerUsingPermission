package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class LstRider(@SerializedName("AnnualPremiumAmount")
                    @Expose
                    var annualPremiumAmount: Int? = null,
                    @SerializedName("BackDatePremium")
                    @Expose
                    var backDatePremium: BigDecimal? = null,

                    @SerializedName("ExBackDatePremium")
                    @Expose
                    var exBackDatePremium: BigDecimal? = null,

                    @SerializedName("ExTaxPremium")
                    @Expose
                    var exTaxPremium: BigDecimal? = null,

                    @SerializedName("GST")
                    @Expose
                    var gST: BigDecimal? = null,

                    @SerializedName("InstallmentPremium")
                    @Expose
                    var installmentPremium: Int? = null,

                    @SerializedName("PolicyTerm")
                    @Expose
                    var policyTerm: Int? = null,

                    @SerializedName("PremiumPayingTerm")
                    @Expose
                    var premiumPayingTerm: BigDecimal? = null,

                    @SerializedName("PremiumServiceTax")
                    @Expose
                    var premiumServiceTax: BigDecimal? = null,

                    @SerializedName("ProtectionCover")
                    @Expose
                    var protectionCover: Int? = null,

                    @SerializedName("RiderBasePremium")
                    @Expose
                    var riderBasePremium: BigDecimal? = null,

                    @SerializedName("RiderCode")
                    @Expose
                    var riderCode: String? = null,

                    @SerializedName("RiderId")
                    @Expose
                    var riderId: String? = null,

                    @SerializedName("RiderName")
                    @Expose
                    var riderName: String? = null,

                    @SerializedName("SumAssured")
                    @Expose
                    var sumAssured: BigDecimal? = null
)