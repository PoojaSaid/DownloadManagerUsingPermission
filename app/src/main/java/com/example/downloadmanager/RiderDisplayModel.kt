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

class RiderDisplayModel : AppCompatActivity(), RiderAdapter.ItemClicked {
    lateinit var riderList: ArrayList<LstRider>
    lateinit var recyclerView: RecyclerView
    lateinit var riderAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    val ACTIVITY2 = 2
    private val TAG = RiderDisplayModel::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_view_model)
        recyclerView = findViewById(R.id.list_rider)
        recyclerView.setHasFixedSize(true)
        //layoutManager = LinearLayoutManager(this)
        //recyclerView.setLayoutManager(layoutManager)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        riderList = ArrayList()
        convertToGson()
    }

    override fun onItemClikcked(index: Int) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY2) {
            if (resultCode == Activity.RESULT_OK) {

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }


    fun convertToGson() {

        var jsonStr = "{ \"AgeProof\": \"Aadhar with DOB\", \"AgeProofDocCode\": \"AADHARCD\", \"AnnualPremiumAmount\": 23154, \"BackDate\": null, \"BackDatePremium\": null, \"BasePremium\": null, \"ChildAge\": \"1\", \"ChildDob\": \"24 May 2019\", \"ChildName\": \"Bffb Nnhmh\", \"CurrentValueOfGoal\": \"10000\", \"ExBackDatePremium\": null, \"ExistingSumAssured\": 0, \"ExTaxPremium\": null, \"FundValueFor4\": null, \"FundValueFor8\": null, \"FutureValueOfGoal\": \"24000\", \"GST\": 1042, \"InstallmentPremium\": 23154, \"IsBackDateRequired\": null, \"isLAProposerSame\": \"1\", \"IsNSAP\": false, \"IsStaff\": false, \"IsUlip\": false, \"LA\": { \"ClientFullName\": \"Rggvv Gmhhmng\", \"ClientId\": null, \"DOB\": \"24 May 1996\", \"EmailId\": \"\", \"FirstName\": null, \"IsJNKResident\": null, \"IsMale\": true, \"IsSmoker\": false, \"LastName\": null, \"MiddleName\": null, \"MobileNumber\": \"8055599716\" }, \"LifeGoal\": \"5\", \"LifeStage\": \"3\", \"lstFund\": [ ], \"lstRider\": [ { \"AnnualPremiumAmount\": 354, \"BackDatePremium\": null, \"ExBackDatePremium\": null, \"ExTaxPremium\": null, \"GST\": 54, \"InstallmentPremium\": 300, \"PolicyTerm\": 16, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProtectionCover\": null, \"RiderBasePremium\": null, \"RiderCode\": null, \"RiderId\": \"30001\", \"RiderName\": \"Future Generali Non-Linked Accidental Death Rider\", \"SumAssured\": 600000 }, { \"AnnualPremiumAmount\": 283, \"BackDatePremium\": null, \"ExBackDatePremium\": null, \"ExTaxPremium\": null, \"GST\": 43, \"InstallmentPremium\": 240, \"PolicyTerm\": 16, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProtectionCover\": null, \"RiderBasePremium\": null, \"RiderCode\": null, \"RiderId\": \"30002\", \"RiderName\": \"Future Generali Non-Linked Accidental Total & Permanent Disability Rider\", \"SumAssured\": 600000 } ], \"No.ofPolicies\": \"0\", \"PaymentFrequency\": \"1\", \"PaymentTermType\": null, \"PdfData\": { \"PdfName\": \"M002900033\", \"PdfPath\": \"/storage/emulated/0/Documents/FutureGenerali/M002900033.pdf\" }, \"PolicyFees\": null, \"PolicyTerm\": 16, \"PreferedRenewalMode\": null, \"PremiumPayingTerm\": 16, \"PremiumServiceTax\": null, \"ProductCode\": \"E68\", \"ProductId\": \"21045\", \"ProductName\": \"Future Generali Assured Education Plan\", \"Proposer\": { \"ClientFullName\": \"Rggvv Gmhhmng\", \"ClientId\": null, \"DOB\": \"24 May 1996\", \"EmailId\": \"\", \"FirstName\": null, \"IsMale\": true, \"LastName\": null, \"MiddleName\": null, \"MobileNumber\": \"8055599716\" }, \"ProposerAnnualIncome\": 6, \"ProtectionCover\": null, \"QuoteNo\": null, \"ReferenceNumber\": \"BFL12845841590260234374\", \"RiskApproach\": \"1\", \"SumAssured\": 600000, \"TimetoAccomplish\": \"15\", \"TotalPremiumwithGST\": 24833 }\n"

        jsonStr = jsonStr.replace("\\/".toRegex(), "")
        // Creates new instance of Gson
        val gson = Gson()

        val element: JsonElement

        try {
            /*//Converts the json string to JsonElement without POJO
            element = gson.fromJson(jsonStr, JsonElement::class.java)

            var data = element.asJsonObject

            var listRider = data.get("lstRider")*/
            /*productRecyclerView.itemAnimator = DefaultItemAnimator()
            productRecyclerView.layoutManager = LinearLayoutManager(context)
            productRecyclerView.adapter = recommendedProductAdapter*/

            val jsonObject: Example = gson.fromJson(jsonStr, Example::class.java)

            var result = jsonObject.lstRider.toString()
            val riderListCurrent = jsonObject.lstRider!!.toList()
            for (element in riderListCurrent) {
                riderList.add(element)
            }

            riderAdapter = RiderAdapter(this, riderList)
            recyclerView.adapter = riderAdapter

            //print(result)
            Log.e(TAG, "Result $result")
            Log.e(TAG, "Rider list  $riderList")


        } catch (e: Exception) {
            Log.e(TAG, "Exception $e")
        }
    }

}
