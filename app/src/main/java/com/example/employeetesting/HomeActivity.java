package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements  NavigationView
        .OnNavigationItemSelectedListener{
private DrawerLayout mNavDrawer;


    private static final String TAG = "HomeActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

String userJobDate;

ImageView imageView;

    TextView txtName,txtEmail;
FirebaseAuth firebaseAuth;

    FirebaseFirestore fStore;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,ds1;
    View viewHeader;
    StorageReference storageReference;
    private FirebaseUser mCurrentUser;

    TextView txt_35,txt_10,txt_5,txt_25,dateDisplay,datePicker;
    SharedPreferences sp=null;

    String user,dateJob,date,kkk,mainDate;
    int id=0,s=0,k=0;


    int urgent=0,avilable=0,applied=0;

    Button btnAvaiable;

    EditText et_date_j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawable_layout);

        sp=getSharedPreferences("date",MODE_PRIVATE);
        datePicker=findViewById(R.id.date_picker);


        imageView=findViewById(R.id.userImage);

        txt_25=findViewById(R.id.txt_25);
        txt_35=findViewById(R.id.txt_35);
        txt_10=findViewById(R.id.txt_10);
        txt_5=findViewById(R.id.txt_5);
        dateDisplay=findViewById(R.id.date);



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

        // mDisplayDate =  findViewById(R.id.tvDate);

       datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
                int year = cal.get(android.icu.util.Calendar.YEAR);
                int month = cal.get(android.icu.util.Calendar.MONTH);
                int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

       mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                 mainDate = day + "/" + month + "/" + year;
                dateDisplay.setText("Selected Date ( "+mainDate+" )");
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("selectedDate",mainDate);
                editor.commit();
                urgent=0;
                avilable=0;
                applied=0;
                databaseReference=firebaseDatabase.getReference().child("Jobs");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                user=ds.getKey();
                                ds1=firebaseDatabase.getInstance().getReference().child("Jobs").child(user);
                                // user=ds.getKey();
                                ds1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                        if (dataSnapshots.exists())
                                        {
                                            for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                            {
                                                String  jobs=ds1.getKey();
                                                date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                                // s=(int)dataSnapshot.getChildrenCount();
                                              //  Calendar calendar=Calendar.getInstance();
                                                //String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

                                                //Log.d("current","urgent job="+currentDate);
                                                //Log.d("date","urgent job="+date);
                                                // date1= sc.next()==date;
                                                //date2 = sc.next();

                                                //Log.d("datejob","date"+kkk);

                                                 String arr1[] = mainDate.split("/");
                                                 String arr2[] = date.split("/");
                                                if(arr1[0].equals(arr2[0]) &&   Integer.parseInt(arr1[1])== Integer.parseInt(arr2[1]) && Integer.parseInt(arr1[2])==Integer.parseInt(arr2[2]))
                                                    urgent++;
                                                if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                    avilable++;
                                                if(Integer.parseInt(arr1[0])<Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                    applied++;

                                                txt_10.setText(String.valueOf(urgent));
                                                txt_25.setText(String.valueOf(applied));
                                                txt_35.setText(String.valueOf(avilable));

                                                //Log.d("available","available jobs jobs=="+s);
                                                //Log.d("applied","applied Jobs"+k);

                                                // UrgentJobs urgentJobs=new UrgentJobs();
                                                //urgentJobs.setUrgentJob(id);
                                                //ur=id;
                                                //total=s;
                                                //av=k;

                                       /* btnAvaiable.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dateJob=et_date_j.getText().toString();
                                                Log.d("datejob","datejob"+dateJob);

                                                try {
                                                    String arr1[] = dateJob.split("/");
                                                    String arr2[] = date.split("/");

                                                    if (arr1[0].equals(arr2[0]))
                                                        urgent++;
                                                    if (Integer.parseInt(arr1[0]) <= Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1]) < Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2]) < Integer.parseInt(arr2[2]))
                                                        avilable++;
                                                    if (Integer.parseInt(arr1[0]) < Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1]) < Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2]) < Integer.parseInt(arr2[2]))
                                                        applied++;


                                                    txt_10.setText(String.valueOf(urgent));
                                                    txt_25.setText(String.valueOf(applied));
                                                    txt_35.setText(String.valueOf(avilable));
                                                }catch (Exception e){
                                                    Log.d("error1","error1"+e);
                                                }

                                            }
                                        });*/



                                       /*if(arr1[0].equals(arr2[0]))
                                            urgent++;
                                        if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                            avilable++;
                                        if(Integer.parseInt(arr1[0])<Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                            applied++;*/

                                                // Log.d("Urgent = "+urgent+" Avilable = "+avilable+" Applied = "+applied);
                                                //  Log.d("urgent","Urgent jobs=="+urgent);
                                                //  txt_10.setVisibility(View.VISIBLE);
                                                // txt_10.setText(String.valueOf(id));
                                                //txt_25.setText(String.valueOf(k));
                                                // txt_25.setVisibility(View.VISIBLE);
                                                //txt_35.setText(String.valueOf(s));
                                                //txt_35.setVisibility(View.VISIBLE);
                                                // Log.d("available","available jobs jobs=="+avilable);
                                                //  Log.d("applied","applied Jobs"+applied);

                                                // UrgentJobs urgentJobs=new UrgentJobs();
                                                //urgentJobs.setUrgentJob(id);
                                                //ur=id;
                                                //total=s;
                                                //av=k;
                                                // special=dataSnapshot.child(jobs).child("Job_Special").getValue().toString();

                                                // Log.d("UserId ",user);
                                     /*   Log.d("title : ",jobtitle);
                                        Log.d("company : ",companynamem);
                                        Log.d("description : ",description);
                                        Log.d("rupee : ",rupee);
                                        Log.d("date : ",date);
                                        Log.d("timeofreporitng : ",timeofreporting);
                                        Log.d("endtime : ",end_time);
                                        Log.d("bookingradius : ",booking_radius);
                                        //Log.d("special : ",special);
                                       // Log.d("date : ",date);*/

                                                //jobModel.add(jobM);
                                            }



                                            //  Log.d("id","Urgent jobs=="+id);
                                            //  txt_10.setVisibility(View.VISIBLE);
                                            // txt_10.setText(String.valueOf(id));
                                            //txt_25.setText(String.valueOf(k));
                                            // txt_25.setVisibility(View.VISIBLE);
                                            //txt_35.setText(String.valueOf(s));
                                            //txt_35.setVisibility(View.VISIBLE);
                                            // Log.d("k","Applied jobs jobs=="+k);
                                            //Log.d("jobs","Avaliable Jobs"+s);


                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                // JobListModel jobM = dataSnapshot.getValue(JobListModel.class);
                                //jobAdapter = new JobListAdapter(FindJob.this, jobModel);
                                //jobModel.add(jobM);

                                //ds1=firebaseDatabase.getReference().child(user).child("job1");
                                //Log.d("user","exception"+firebaseDatabase+" "+databaseReference+" "+ds1);
                        /*  companynamem=dataSnapshot.child(user).child("Company_name").getValue().toString();
                        jobtitle=dataSnapshot.child(user).child("Job_Title").getValue().toString();
                        description=dataSnapshot.child(user).child("Job_Desc").getValue().toString();
                        timeofreporting=dataSnapshot.child(user).child("Job_Start_Time").getValue().toString();
                        end_time=dataSnapshot.child(user).child("Job_End_Time").getValue().toString();
                        booking_radius=dataSnapshot.child(user).child("Job_Booking_Radius").getValue().toString();
                        special=dataSnapshot.child(user).child("Job_Special").getValue().toString();
                        date=dataSnapshot.child(user).child("Job_Date").getValue().toString();
                        rupee=dataSnapshot.child(user).child("Job_Amount").getValue().toString();*/
                                //  jobModel.add(new JobListModel(companynamem,rupee,booking_radius,date,description,end_time,special,timeofreporting,jobtitle));
                                //jobAdapter.notifyDataSetChanged();


                                //JobListModel jobM = dataSnapshot.child(user).getValue(JobListModel.class);
                                //jobAdapter = new JobListAdapter(FindJob.this, jobModel);
                                //jobModel.add(jobM);
                                // ds1=firebaseDatabase.getReference().child(user).child("job1");
                                //Log.d("user","exception"+firebaseDatabase+" "+databaseReference+" "+ds1);
                        /* ds1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    JobListModel jobM = dataSnapshot.getValue(JobListModel.class);

                                    jobModel.add(jobM);


                                    jobAdapter = new JobListAdapter(FindJob.this, jobModel);
                                    // recyclerView.setAdapter(jobAdapter);
                                    jobAdapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    Log.d("MyAccountn","exception"+e);
                                }
                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError databaseError){

                                }

                        });*/
                            }
                        }
                        else{
                            Toast.makeText(HomeActivity.this, "data not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        };

        Calendar calendar=Calendar.getInstance();
        final String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

        final String test= sp.getString("selectedDate",currentDate);
        if(test.equals(currentDate))
        {
            urgent=0;
            avilable=0;
            applied=0;
            dateDisplay.setText("Current Date ( "+currentDate+" )");

            databaseReference=firebaseDatabase.getReference().child("Jobs");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user=ds.getKey();
                            ds1=firebaseDatabase.getInstance().getReference().child("Jobs").child(user);
                            // user=ds.getKey();
                            ds1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                    if (dataSnapshots.exists())
                                    {
                                        for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                        {
                                            String  jobs=ds1.getKey();

                                            date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();

                                            String arr1[] = currentDate.split(" ");
                                            String arr2[] = date.split("/");
                                            String arr3[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                                            if(arr1[0].equals(arr2[0]) && (arr1[1].equals(arr3[Integer.parseInt(arr2[1])-1])) && Integer.parseInt(arr1[2])==Integer.parseInt(arr2[2]))
                                                urgent++;
                                            if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]) || (Arrays.asList(arr3).indexOf(arr1[1])+1)<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                avilable++;
                                            if(Integer.parseInt(arr1[0])<Integer.parseInt(arr2[0]) || (Arrays.asList(arr3).indexOf(arr1[1])+1)<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                applied++;

                                            txt_10.setText(String.valueOf(urgent));
                                            txt_25.setText(String.valueOf(applied));
                                            txt_35.setText(String.valueOf(avilable));

                                          //  if(arr1[0].equals(arr2[0])&&arr1[2].equals(arr2[2])&& arr2[1].equals(arr3[Integer.parseInt(arr1[1])-1])) {
                                            //                                            id++;
                                            //                                            }
                                            //                                            else
                                            //                                              k++;

                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this, "data not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else {
            urgent=0;
            avilable=0;
            applied=0;
            dateDisplay.setText("Selected Date ( "+test+" )");
            databaseReference=firebaseDatabase.getReference().child("Jobs");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user=ds.getKey();
                            ds1=firebaseDatabase.getInstance().getReference().child("Jobs").child(user);

                            ds1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                    if (dataSnapshots.exists())
                                    {
                                        for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                        {
                                            String  jobs=ds1.getKey();

                                            date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                            String arr1[] = test.split("/");
                                            String arr2[] = date.split("/");
                                            if(arr1[0].equals(arr2[0]) &&   Integer.parseInt(arr1[1])== Integer.parseInt(arr2[1]) && Integer.parseInt(arr1[2])==Integer.parseInt(arr2[2]))
                                                urgent++;
                                            if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                avilable++;
                                            if(Integer.parseInt(arr1[0])<Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
                                                applied++;

                                            txt_10.setText(String.valueOf(urgent));
                                            txt_25.setText(String.valueOf(applied));
                                            txt_35.setText(String.valueOf(avilable));

                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this, "data not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


       /* mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
                int year = cal.get(android.icu.util.Calendar.YEAR);
                int month = cal.get(android.icu.util.Calendar.MONTH);
                int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });*/

       /* mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                dateJob = day + "/" + month + "/" + year;
                mDisplayDate.setText(dateJob);
             //   pickDate(dateJob);
            }
        };*/


       //et_date_j=findViewById(R.id.et_date_job);
      // dateJob=et_date_j.getText().toString();
       //btnAvaiable=findViewById(R.id.btnAvailable);





    }

   /* private  void pickDate(String dateJob) {
        String arr1[] = date.split("/");
        String arr2[] = dateJob.split("/");

        if(arr1[0].equals(arr2[0]))
            urgent++;
        if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
            avilable++;
        if(Integer.parseInt(arr1[0])<Integer.parseInt(arr2[0]) || Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]) || Integer.parseInt(arr1[2])<Integer.parseInt(arr2[2]))
            applied++;

        txt_10.setText(String.valueOf(urgent));
        txt_25.setText(String.valueOf(applied));
        txt_35.setText(String.valueOf(avilable));
    }*/

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
            case R.id.nav_home:
               // startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                break;

            case R.id.nav_find_job:
                startActivity(new Intent(HomeActivity.this, FindJob.class));
                break;

            case R.id.nav_urgent_job:
                startActivity(new Intent(HomeActivity.this, UrgentJobActivity.class));
                break;

            case R.id.nav_applied_jobs:
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
