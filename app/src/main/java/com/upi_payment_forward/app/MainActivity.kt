package com.upi_payment_forward.app

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val c: Date = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
        val transcId: String = df.format(c)

        idBtnMakePayment.setOnClickListener{
            val amount: String = idEdtAmount.getText().toString()
            val upi: String = idEdtUpi.getText().toString()
            val name: String = idEdtName.getText().toString()
            val desc: String = idEdtDescription.getText().toString()
            // on below line we are validating our text field.
            // on below line we are validating our text field.
            if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upi) && TextUtils.isEmpty(name) && TextUtils.isEmpty(
                    desc
                )
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter all the details..",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // if the edit text is not empty then
                // we are calling method to make payment.
                makePayment(amount, upi, name, desc, transcId)
            }
        }

    }

    private fun makePayment(
        amount: String,
        upi: String,
        name: String,
        desc: String,
        transactionId: String
    ) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        val easyUpiPayment: EasyUpiPayment = Builder()
            .with(this) // on below line we are adding upi id.
            .setPayeeVpa(upi) // on below line we are setting name to which we are making payment.
            .setPayeeName(name) // on below line we are passing transaction id.
            .setTransactionId(transactionId) // on below line we are passing transaction ref id.
            .setTransactionRefId(transactionId) // on below line we are adding description to payment.
            .setDescription(desc) // on below line we are passing amount which is being paid.
            .setAmount(amount) // on below line we are calling a build method to build this ui.
            .build()
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment()
        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener(this)
    }

    fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        // on below line we are getting details about transaction when completed.
        val transcDetails: String = transactionDetails.getStatus().toString()
            .toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId()
        transactionDetailsTV.setVisibility(View.VISIBLE)
        // on below line we are setting details to our text view.
        transactionDetailsTV.setText(transcDetails)
    }
}