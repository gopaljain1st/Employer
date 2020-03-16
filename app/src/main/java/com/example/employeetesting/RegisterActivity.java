package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.compiler.PluginProtos;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {


    EditText et_f_name, et_l_name, et_post_code, et_phone_number,
            et_password, et_cnfm_pswd, et_account_number, et_address,
            et_email,et_paytm_number,et_tez_number,et_IFSC_CODE,et_bank_name;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    Button btnRegister;

    private ImageView userImage;

    private Uri imageUri = null;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");


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
        btnRegister=findViewById(R.id.btn_registerEmployee);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


         storageReference = FirebaseStorage.getInstance().getReference();
       userImage.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                 if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                                     ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                                                     //Toast.makeText(RegisterActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                                                 } else {
                                                     choseImage();
                                                 }
                                             } else {
                                                 choseImage();
                                             }
                                         }
                                     }
        );


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_f_name.getText().toString().isEmpty()|| et_l_name.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()
                        || et_account_number.getText().toString().isEmpty()|| et_paytm_number.getText().toString().isEmpty() || et_tez_number.getText().toString().isEmpty()
                        || et_bank_name.getText().toString().isEmpty()|| et_email.getText().toString().isEmpty() || et_IFSC_CODE.getText().toString().isEmpty()
                        ||et_phone_number.getText().toString().isEmpty()|| et_address.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    {
                    DocumentReference docRef = fStore.collection("users").document(userID);
                    //DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("first",et_f_name.getText().toString());
                    user.put("last",et_l_name.getText().toString());
                    user.put("email",et_email.getText().toString());

                    user.put("password",et_password.getText().toString());
                    user.put("accountNumber",et_account_number.getText().toString());
                    user.put("PaytmNumber",et_paytm_number.getText().toString());

                    user.put("Address", et_address.getText().toString());
                    user.put("IFSCCODE",et_IFSC_CODE.getText().toString());
                    user.put("BankName",et_bank_name.getText().toString());

                    user.put("TezNumber",et_tez_number.getText().toString());
                    user.put("PhoneNumber",et_phone_number.getText().toString());

                    FileUplode();

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        }
                    });
                }

            }
        });
    }
   /* private String getExtension(Uri uri){
    ContentResolver cr=getContentResolver();
    MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
    return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }*/

    private void FileUplode() {
try {
    if (imageUri != null) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Image Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded" + (int) progress + "%");
            }
        });
    }
               else{
            Toast.makeText(this, "Please Chose Image", Toast.LENGTH_SHORT).show();
        }
}
        catch(Exception e){
            Log.d("exception","ex"+e);
        }
    }

    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(RegisterActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                userImage.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
