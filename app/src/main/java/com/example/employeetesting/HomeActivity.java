package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.compiler.PluginProtos;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements  NavigationView
        .OnNavigationItemSelectedListener{
private DrawerLayout mNavDrawer;

ImageView imageView;

    TextView txtName,txtEmail;
FirebaseAuth firebaseAuth;

    FirebaseFirestore fStore;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View viewHeader;
    StorageReference storageReference;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawable_layout);

        imageView=findViewById(R.id.userImage);

        firebaseAuth=FirebaseAuth.getInstance();
        mCurrentUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");


        fStore = FirebaseFirestore.getInstance();

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Home");

        mNavDrawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.navigation_view);
        viewHeader=navigationView.getHeaderView(0);

        firebaseDatabase=FirebaseDatabase.getInstance();

        mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();

        txtEmail=viewHeader.findViewById(R.id.headeremail);
        txtName=viewHeader.findViewById(R.id.headername);

        try {

            DocumentReference docRef = fStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String mName = documentSnapshot.getString("first");
                        String mEmail = documentSnapshot.getString("email");

                        txtName.setText(mName);
                        txtEmail.setText(mEmail);
                    }
                }
            });

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mNavDrawer, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            mNavDrawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
        }catch (NullPointerException n){
            Toast.makeText(this, ""+n, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                startActivity(new Intent(HomeActivity.this, DashBoard.class));
                break;

            case R.id.nav_find_job:
                startActivity(new Intent(HomeActivity.this, FindJob.class));
                break;

            case R.id.nav_urgent_job:
                startActivity(new Intent(HomeActivity.this, UrgentJobActivity.class));
                break;

            case R.id.nav_set_availabilty:
                startActivity(new Intent(HomeActivity.this, EmployeeSetAvalibilty.class));
                break;

            case R.id.nav_edit_profile:
                startActivity(new Intent(HomeActivity.this, EditProfile.class));
                break;

            case R.id.profile:
                startActivity(new Intent(HomeActivity.this, EmployeeProfile.class));
                break;

            case R.id.nav_logout:
                firebaseAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
