package com.example.downloadmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonElement

class RiderDisplayModel : AppCompatActivity() {
    var riderList: ArrayList<LstRider> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var riderAdapter: RecyclerView.Adapter<*>

    var fundList: ArrayList<LstFund> = ArrayList()
    lateinit var fundRecyclerView: RecyclerView
    lateinit var fundAdapter: RecyclerView.Adapter<*>

    private val TAG = RiderDisplayModel::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_view_model)
        recyclerView = findViewById(R.id.list_fund)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        fundRecyclerView = findViewById(R.id.list_fund)
        fundRecyclerView.setHasFixedSize(true)
        fundRecyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        convertToGson("{\"LA\":{\"IsSmoker\":false,\"IsJNKResident\":null,\"ClientId\":null,\"ClientFullName\":\"Purshottam Thakkar\",\"FirstName\":null,\"MiddleName\":null,\"LastName\":null,\"IsMale\":true,\"DOB\":\"20 Apr 1980\",\"MobileNumber\":\"8169938807\",\"EmailId\":\"tano88@yahoo.com\"},\"Proposer\":{\"ClientId\":null,\"ClientFullName\":\"PurshottamDad Thakkar\",\"FirstName\":null,\"MiddleName\":null,\"LastName\":null,\"RelationWithProposer\":\"Brother\",\"IsMale\":false,\"DOB\":\"20 Jan 1980\",\"MobileNumber\":\"9773097566\",\"EmailId\":\"test@gmail.com\"},\"lstRider\":[{\"RiderId\":\"30004\",\"RiderName\":\"Future Generali Linked Accidental Death Rider\",\"RiderCode\":null,\"RiderBasePremium\":null,\"GST\":45,\"InstallmentPremium\":250,\"AnnualPremiumAmount\":295,\"PremiumServiceTax\":null,\"ExTaxPremium\":null,\"PolicyTerm\":10,\"PremiumPayingTerm\":5,\"SumAssured\":500000,\"ProtectionCover\":null,\"ExBackDatePremium\":null,\"BackDatePremium\":null}],\"lstFund\":[{\"FundID\":\"21001\",\"FundCode\":null,\"FundComposition\":\"5\"},{\"FundID\":\"21005\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21004\",\"FundCode\":null,\"FundComposition\":\"15\"},{\"FundID\":\"21003\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21002\",\"FundCode\":null,\"FundComposition\":\"10\"},{\"FundID\":\"21007\",\"FundCode\":null,\"FundComposition\":\"25\"},{\"FundID\":\"21006\",\"FundCode\":null,\"FundComposition\":\"25\"}],\"ProductCode\":\"U44\",\"ProductId\":\"21022\",\"QuoteNo\":null,\"ProductName\":\"Future Generali Dhan Vridhi \",\"BasePremium\":null,\"AgeProofDocCode\":\"LEVGCERT\",\"GST\":0,\"isLAProposerSame\":\"0\",\"SumAssured\":500000,\"InstallmentPremium\":50000,\"AnnualPremiumAmount\":50000,\"ReferenceNumber\":\"PQRS\",\"TotalPremiumwithGST\":50000,\"PremiumServiceTax\":null,\"ExTaxPremium\":null,\"PolicyFees\":null,\"PolicyTerm\":\"10\",\"PremiumPayingTerm\":\"5\",\"PaymentFrequency\":\"1\",\"ProtectionCover\":null,\"IsBackDateRequired\":null,\"BackDate\":null,\"ExBackDatePremium\":null,\"BackDatePremium\":null,\"FundValueFor4\":null,\"FundValueFor8\":null,\"PreferedRenewalMode\":null,\"IsUlip\":true,\"IsNSAP\":false,\"AgeProof\":\"Leaving Certificate\",\"IsStaff\":true,\"LifeStage\":\"1\",\"ProposerAnnualIncome\":\"5.0\",\"RiskApproach\":\"3\",\"ExistingSumAssured\":\"0.0\",\"No.ofPolicies\":\"0\",\"LifeGoal\":\"2\",\"CurrentValueOfGoal\":\"15\",\"TimetoAccomplish\":\"15\",\"FutureValueOfGoal\":\"36\"}",
                        fundList)
        /*convertToGson("{ \"AgeProof\": \"Aadhar with DOB\", \"AgeProofDocCode\": \"AADHARCD\", \"AnnualPremiumAmount\": 23154, \"BackDate\": null, \"BackDatePremium\": null, \"BasePremium\": null, \"ChildAge\": \"1\", \"ChildDob\": \"24 May 2019\", \"ChildName\": \"Bffb Nnhmh\", \"CurrentValueOfGoal\": \"10000\", \"ExBackDatePremium\": null, \"ExistingSumAssured\": 0, \"ExTaxPremium\": null, \"FundValueFor4\": null, \"FundValueFor8\": null, \"FutureValueOfGoal\": \"24000\", \"GST\": 1042, \"InstallmentPremium\": 23154, \"IsBackDateRequired\": null, \"isLAProposerSame\": \"1\", \"IsNSAP\": false, \"IsStaff\": false, \"IsUlip\": false, \"LA\": { \"ClientFullName\": \"Rggvv Gmhhmng\", \"ClientId\": null, \"DOB\": \"24 May 1996\", \"EmailId\": \"\", \"FirstName\": null, \"IsJNKResident\": null, \"IsMale\": true, \"IsSmoker\": false, \"LastName\": null, \"MiddleName\": null, \"MobileNumber\": \"8055599716\" }, \"LifeGoal\": \"5\", \"LifeStage\": \"3\", \"lstFund\": [ ], \"lstRider\": [ { \"AnnualPremiumAmount\": 354, \"BackDatePremium\": null, \"ExBackDatePremium\": null, \"ExTaxPremium\": null, \"GST\": 54, \"InstallmentPremium\": 300, \"PolicyTerm\": 16, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProtectionCover\": null, \"RiderBasePremium\": null, \"RiderCode\": null, \"RiderId\": \"30001\", \"RiderName\": \"Future Generali Non-Linked Accidental Death Rider\", \"SumAssured\": 600000 }, { \"AnnualPremiumAmount\": 283, \"BackDatePremium\": null, \"ExBackDatePremium\": null, \"ExTaxPremium\": null, \"GST\": 43, \"InstallmentPremium\": 240, \"PolicyTerm\": 16, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProtectionCover\": null, \"RiderBasePremium\": null, \"RiderCode\": null, \"RiderId\": \"30002\", \"RiderName\": \"Future Generali Non-Linked Accidental Total & Permanent Disability Rider\", \"SumAssured\": 600000 } ], \"No.ofPolicies\": \"0\", \"PaymentFrequency\": \"1\", \"PaymentTermType\": null, \"PdfData\": { \"PdfName\": \"M002900033\", \"PdfPath\": \"/storage/emulated/0/Documents/FutureGenerali/M002900033.pdf\" }, \"PolicyFees\": null, \"PolicyTerm\": 16, \"PreferedRenewalMode\": null, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProductCode\": \"E68\", \"ProductId\": \"21045\", \"ProductName\": \"Future Generali Assured Education Plan\", \"Proposer\": { \"ClientFullName\": \"Rggvv Gmhhmng\", \"ClientId\": null, \"DOB\": \"24 May 1996\", \"EmailId\": \"\", \"FirstName\": null, \"IsMale\": true, \"LastName\": null, \"MiddleName\": null, \"MobileNumber\": \"8055599716\" }, \"ProposerAnnualIncome\": 6, \"ProtectionCover\": null, \"QuoteNo\": null, \"ReferenceNumber\": \"BFL12845841590260234374\", \"RiskApproach\": \"1\", \"SumAssured\": 600000, \"TimetoAccomplish\": \"15\", \"TotalPremiumwithGST\": 24833 }\n",
             riderList)*/
    }

    fun convertToGson(jsonString: String, inputList: ArrayList<LstFund>) {

        var jsonString = jsonString.replace("\\/".toRegex(), "")

        val gson = Gson()

        try {
            val jsonObject: Example = gson.fromJson(jsonString, Example::class.java)

            //For Rider List
            if (inputList == riderList) {

                val riderListCurrent = jsonObject.lstRider!!.toList()

                for (element in riderListCurrent) {
                    riderList.add(element)
                }

                riderAdapter = RiderAdapter(this, riderList)

                recyclerView.adapter = riderAdapter

                Log.e(TAG, "Rider list  $riderList")
            }

            //For Lost Fund List
            if (inputList == fundList) {
                val fundListCurrent = jsonObject.lstFund!!.toList()

                for (element in fundListCurrent) {
                    fundList.add(element)
                }

                fundAdapter = FundAdapter(this, fundList)

                fundRecyclerView.adapter = fundAdapter

                Log.e(TAG, "Fund list  $fundList")
            }


        } catch (e: Exception) {
            Log.e(TAG, "Exception $e")
        }
    }

}
