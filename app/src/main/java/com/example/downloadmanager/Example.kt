package com.example.downloadmanager

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Example(
        @SerializedName("AgeProof")
        @Expose
        private val ageProof: String? = null,

        @SerializedName("AgeProofDocCode")
        @Expose
        private val ageProofDocCode: String? = null,
        @SerializedName("AnnualPremiumAmount")
        @Expose
        private val annualPremiumAmount: BigDecimal? = null,

        @SerializedName("BackDate")
        @Expose
        private val backDate: BigDecimal? = null,

        @SerializedName("BackDatePremium")
        @Expose
        private val backDatePremium: BigDecimal? = null,

        @SerializedName("BasePremium")
        @Expose
        private val basePremium: BigDecimal? = null,

        @SerializedName("ChildAge")
        @Expose
        private val childAge: String? = null,

        @SerializedName("ChildDob")
        @Expose
        private val childDob: String? = null,

        @SerializedName("ChildName")
        @Expose
        private val childName: String? = null,

        @SerializedName("CurrentValueOfGoal")
        @Expose
        private val currentValueOfGoal: String? = null,

        @SerializedName("ExBackDatePremium")
        @Expose
        private val exBackDatePremium: String? = null,

        @SerializedName("ExistingSumAssured")
        @Expose
        private val existingSumAssured: Int? = null,

        @SerializedName("ExTaxPremium")
        @Expose
        private val exTaxPremium: BigDecimal? = null,

        @SerializedName("FundValueFor4")
        @Expose
        private val fundValueFor4: BigDecimal? = null,

        @SerializedName("FundValueFor8")
        @Expose
        private val fundValueFor8: String? = null,

        @SerializedName("FutureValueOfGoal")
        @Expose
        private val futureValueOfGoal: String? = null,

        @SerializedName("GST")
        @Expose
        private val gST: Int? = null,

        @SerializedName("InstallmentPremium")
        @Expose
        private val installmentPremium: Int? = null,

        @SerializedName("IsBackDateRequired")
        @Expose
        private val isBackDateRequired: String? = null,

        @SerializedName("isLAProposerSame")
        @Expose
        private val isLAProposerSame: String? = null,

        @SerializedName("IsNSAP")
        @Expose
        private val isNSAP: Boolean? = null,

        @SerializedName("IsStaff")
        @Expose
        private val isStaff: Boolean? = null,

        @SerializedName("IsUlip")
        @Expose
        private val isUlip: Boolean? = null,

        @SerializedName("LA")
        @Expose
        private val lA: LA? = null,

        @SerializedName("LifeGoal")
        @Expose
        private val lifeGoal: String? = null,

        @SerializedName("LifeStage")
        @Expose
        private val lifeStage: String? = null,

        @SerializedName("lstFund")
        @Expose val lstFund: ArrayList<LstFund> = ArrayList(),

        @SerializedName("lstRider")
        @Expose val lstRider: ArrayList<LstRider> = ArrayList(),

        @SerializedName("No.ofPolicies")
        @Expose
        private val noOfPolicies: String? = null,

        @SerializedName("PaymentFrequency")
        @Expose
        private val paymentFrequency: String? = null,

        @SerializedName("PaymentTermType")
        @Expose
        private val paymentTermType: Int? = null,

        @SerializedName("PdfData")
        @Expose
        private val pdfData: PdfData? = null,

        @SerializedName("PolicyFees")
        @Expose
        private val policyFees: BigDecimal? = null,

        @SerializedName("PolicyTerm")
        @Expose
        private val policyTerm: Int? = null,

        @SerializedName("PreferedRenewalMode")
        @Expose
        private val preferedRenewalMode: Int? = null,

        @SerializedName("PremiumPayingTerm")
        @Expose
        private val premiumPayingTerm: Int? = null,

        @SerializedName("PremiumServiceTax")
        @Expose
        private val premiumServiceTax: BigDecimal? = null,

        @SerializedName("ProductCode")
        @Expose
        private val productCode: String? = null,

        @SerializedName("ProductId")
        @Expose
        private val productId: String? = null,

        @SerializedName("ProductName")
        @Expose
        private val productName: String? = null,

        @SerializedName("Proposer")
        @Expose
        private val proposer: Proposer? = null,

        @SerializedName("ProposerAnnualIncome")
        @Expose
        private val proposerAnnualIncome: Int? = null,

        @SerializedName("ProtectionCover")
        @Expose
        private val protectionCover: BigDecimal? = null,

        @SerializedName("QuoteNo")
        @Expose
        private val quoteNo: String? = null,

        @SerializedName("ReferenceNumber")
        @Expose
        private val referenceNumber: String? = null,

        @SerializedName("RiskApproach")
        @Expose
        private val riskApproach: String? = null,

        @SerializedName("SumAssured")
        @Expose
        private val sumAssured: Int? = null,

        @SerializedName("TimetoAccomplish")
        @Expose
        private val timetoAccomplish: String? = null,

        @SerializedName("TotalPremiumwithGST")
        @Expose
        private val totalPremiumwithGST: Int? = null)
