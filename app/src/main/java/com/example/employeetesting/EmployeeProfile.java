package com.example.employeetesting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmployeeProfile extends AppCompatActivity {


    public static final String TAG = "TAG";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    //TextView fullName,email,phone;
    String mName,mEmail,mPhone,mAddress,mIFSCCODE,mBankName,mPaytmNumber,mTezNumber,mAccountnumber,mLastNumber,mPassword;
    //Toolbar toolbar;

    TextView txtFirstName,txtLastName,txtEmail,txtPhoneNumber,txtPaytmNumber,txtTezNumber,txtAddress,txtIFSCCODe,txtBankName,
            txtPassword,txtAccountNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("EmployeeProfile");


        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // fullName = findViewById(R.id.profileFullName);
        //email = findViewById(R.id.profileEmail);
        //phone = findViewById(R.id.profilePhone);

        txtFirstName=findViewById(R.id.et_f_name);
        txtLastName=findViewById(R.id.et_l_name);
        txtEmail=findViewById(R.id.et_email);
        txtAddress=findViewById(R.id.et_address);
        txtPassword=findViewById(R.id.et_password);
        txtPaytmNumber=findViewById(R.id.et_paytm_number);
        txtIFSCCODe=findViewById(R.id.IFSCCODE);
        txtTezNumber=findViewById(R.id.et_tez_number);
        txtBankName=findViewById(R.id.et_bank_name);
        txtPhoneNumber=findViewById(R.id.et_phone_number);
        txtAccountNumber=findViewById(R.id.et_account_number);

        DocumentReference docRef =fStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    mName = documentSnapshot.getString("first");
                    mLastNumber=documentSnapshot.getString("last");
                    mEmail = documentSnapshot.getString("email");
                    mPhone = firebaseAuth.getCurrentUser().getPhoneNumber();
                    mAddress=documentSnapshot.getString("Address");
                    mPaytmNumber=documentSnapshot.getString("PaytmNumber");
                    mTezNumber=documentSnapshot.getString("TezNumber");
                    mIFSCCODE=documentSnapshot.getString("IFSCCODE");
                    mAccountnumber=documentSnapshot.getString("accountNumber");
                    mBankName=documentSnapshot.getString("BankName");
                    mPassword=documentSnapshot.getString("password");

                    txtFirstName.setText(mName);
                    txtLastName.setText(mLastNumber);
                    txtEmail.setText(mEmail);
                    txtPhoneNumber.setText(mPhone);
                    txtAddress.setText(mAddress);
                    txtPaytmNumber.setText(mPaytmNumber);
                    txtTezNumber.setText(mTezNumber);
                    txtIFSCCODe.setText(mIFSCCODE);
                    txtAccountNumber.setText(mAccountnumber);
                    txtBankName.setText(mBankName);
                    txtPassword.setText(mPassword);
                }else {
                    Log.d(TAG, "Retrieving Data: Profile Data Not Found ");
                }
            }
        });

    }
}
