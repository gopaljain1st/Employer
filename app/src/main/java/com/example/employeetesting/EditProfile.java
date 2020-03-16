package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.employeetesting.model.EmployeeData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

            EditText et_f_name, et_l_name, et_post_code, et_phone_number,
            et_password, et_cnfm_pswd, et_account_number, et_address,
            et_email,et_paytm_number,et_tez_number,et_IFSC_CODE,et_bank_name;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;

    Button btnUpdate;

    private ImageView userImage;
    EmployeeData et;

    private Uri imageUri = null;
    private StorageReference storageReference;

    String mName,mEmail,mPhone,mAddress,mIFSCCODE,mBankName,mPaytmNumber,mTezNumber,mAccountnumber,mLastNumber,mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseAuth=FirebaseAuth.getInstance();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("EditProfile");

        userImage = findViewById(R.id.ProfileImage);
        et_f_name = findViewById(R.id.et_f_name);
        et_l_name = findViewById(R.id.et_l_name);
        et_password = findViewById(R.id.et_password);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_email = findViewById(R.id.et_email);
        et_address=findViewById(R.id.et_address);
        et_paytm_number=findViewById(R.id.et_paytm_number);
        et_tez_number=findViewById(R.id.et_tez_number);
        et_account_number = findViewById(R.id.et_account_number);
        et_IFSC_CODE=findViewById(R.id.IFSCCODE);
        et_bank_name=findViewById(R.id.et_bank_name);
        btnUpdate=findViewById(R.id.btn_updateEmployee);


        fStore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fStore.collection("users").document(userID);

        et=new EmployeeData();
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

                     //et.setEt_f_name(mName);
                    et_f_name.setText(mName);
                    et_l_name.setText(mLastNumber);
                    et_email.setText(mEmail);
                    et_phone_number.setText(mPhone);
                    et_account_number.setText(mAccountnumber);
                    et_address.setText(mAddress);
                    et_bank_name.setText(mBankName);
                    et_IFSC_CODE.setText(mIFSCCODE);
                    et_paytm_number.setText(mPaytmNumber);
                    et_tez_number.setText(mTezNumber);
                    et_password.setText(mPassword);

btnUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        docRef.update("first",et_f_name.getText().toString(),"last",et_l_name.getText().toString()
        ,"email",et_email.getText().toString(),"password",et_password.getText().toString(),
                "accountNumber",et_account_number.getText().toString(),"PaytmNumber",et_paytm_number.getText().toString(),
                "Address", et_address.getText().toString(),"IFSCCODE",et_IFSC_CODE.getText().toString(),
                "BankName",et_bank_name.getText().toString(),"TezNumber",et_tez_number.getText().toString(),
                "PhoneNumber",et_phone_number.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditProfile.this, "Update", Toast.LENGTH_SHORT).show();
            }
        });
    }
});
                }else {
                    Log.d("update", "Retrieving Data: Profile Data Not Found ");
                }
            }
        });
    }
}
