package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UrgentJobActivity extends AppCompatActivity {

    RecyclerView View;
    JobListAdapter jobAdapter;
    List<JobListModel> jobModel;
    SharedPreferences sp=null;
    TextView tex;
    int id=0,k=0,s=0;
    String date,user;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,ds1,databseUrgent;
    String jobtitle,companynamem,description,timeofreporting,rupee,booking_radius,end_time,special,employee_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_job);
        View=findViewById(R.id.recycle_urgent);
        View.setHasFixedSize(true);
        View.setLayoutManager(new LinearLayoutManager((this)));
        jobModel=new ArrayList<>();


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("UrgentJob");

        tex=findViewById(R.id.txtUrgent);
        //UrgentJobs urgentJobs=new UrgentJobs();
        //tex.setText(urgentJobs.getUrgentJob());

        sp=getSharedPreferences("date",MODE_PRIVATE);
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

        //Log.d("current","urgent job="+currentDate);
        //Log.d("date","urgent job="+date);
        // date1= sc.next()==date;
        //date2 = sc.next();
        final String test=sp.getString("selectedDate",currentDate);

             if(test.equals(currentDate))
             {
                 firebaseDatabase= FirebaseDatabase.getInstance();
                 databaseReference=firebaseDatabase.getReference().child("Jobs");

                 databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                         if (dataSnapshot.exists()) {
                             for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                 user=ds.getKey();
                                 ds1=firebaseDatabase.getInstance().getReference().child("Jobs").child(user);
                                 // user=ds.getKey();
                                 ds1.addListenerForSingleValueEvent(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                         if (dataSnapshots.exists())
                                         {
                                             for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                             {
                                                 String  jobs=ds1.getKey();
                                                 // s=(int)dataSnapshot.getChildrenCount();
                                                 date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                                 //Log.d("current","urgent job="+currentDate);
                                                 //Log.d("date","urgent job="+date);
                                                 // date1= sc.next()==date;
                                                 //date2 = sc.next();
                                                 String arr1[] = test.split(" ");
                                                 String arr2[] = date.split("/");
                                                 String arr3[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                                                 if(arr1[0].equals(arr2[0]) && (arr1[1].equals(arr3[Integer.parseInt(arr2[1])-1])) && Integer.parseInt(arr1[2])==Integer.parseInt(arr2[2]))
                                                 {
                                                     id++;
                                                     jobtitle=dataSnapshots.child(jobs).child("Job_Title").getValue().toString();
                                                     companynamem=dataSnapshots.child(jobs).child("Company_name").getValue().toString();
                                                     description=dataSnapshots.child(jobs).child("Job_Desc").getValue().toString();
                                                     rupee=dataSnapshots.child(jobs).child("Job_Amount").getValue().toString();
                                                     date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                                     timeofreporting=dataSnapshots.child(jobs).child("Job_Start_Time").getValue().toString();
                                                     end_time=dataSnapshots.child(jobs).child("Job_End_Time").getValue().toString();
                                                     booking_radius=dataSnapshots.child(jobs).child("Job_Booking_Radius").getValue().toString();
                                                     //  employee_Id=dataSnapshot.child(jobs).child("UserId").getValue().toString();
                                                     jobModel.add(new JobListModel(companynamem,rupee,booking_radius,date,description,end_time,special,timeofreporting,jobtitle,employee_Id));
                                                     // JobListModel jobM = dataSnapshot.getValue(JobListModel.class);
                                                     jobAdapter = new JobListAdapter(UrgentJobActivity.this, jobModel);
                                                     View.setAdapter(jobAdapter);

                                                 }

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

// Log.d("id","Urgent jobs=="+id);
////                                    tex.setText(String.valueOf(id));
////                                    Log.d("k","Applied jobs jobs=="+k);
////                                    Log.d("jobs","Avaliable Jobs"+s);
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
                             Toast.makeText(UrgentJobActivity.this, "data not exist", Toast.LENGTH_SHORT).show();
                         }
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {
                     }
                 });

             }
             else
             {
                 firebaseDatabase= FirebaseDatabase.getInstance();
                 databaseReference=firebaseDatabase.getReference().child("Jobs");

                 databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                         if (dataSnapshot.exists()) {
                             for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                 user=ds.getKey();
                                 ds1=firebaseDatabase.getInstance().getReference().child("Jobs").child(user);
                                 // user=ds.getKey();
                                 ds1.addListenerForSingleValueEvent(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                         if (dataSnapshots.exists())
                                         {
                                             for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                             {
                                                 String  jobs=ds1.getKey();
                                                 // s=(int)dataSnapshot.getChildrenCount();
                                                 date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                                 //Log.d("current","urgent job="+currentDate);
                                                 //Log.d("date","urgent job="+date);
                                                 // date1= sc.next()==date;
                                                 //date2 = sc.next();

                                                 String arr1[] = test.split("/");
                                                 String arr2[] = date.split("/");
                                                 if(arr1[0].equals(arr2[0]) &&   Integer.parseInt(arr1[1])== Integer.parseInt(arr2[1]) && Integer.parseInt(arr1[2])==Integer.parseInt(arr2[2]))
                                                 {
                                                     id++;
                                                     jobtitle=dataSnapshots.child(jobs).child("Job_Title").getValue().toString();
                                                     companynamem=dataSnapshots.child(jobs).child("Company_name").getValue().toString();
                                                     description=dataSnapshots.child(jobs).child("Job_Desc").getValue().toString();
                                                     rupee=dataSnapshots.child(jobs).child("Job_Amount").getValue().toString();
                                                     date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                                     timeofreporting=dataSnapshots.child(jobs).child("Job_Start_Time").getValue().toString();
                                                     end_time=dataSnapshots.child(jobs).child("Job_End_Time").getValue().toString();
                                                     booking_radius=dataSnapshots.child(jobs).child("Job_Booking_Radius").getValue().toString();
                                                     //  employee_Id=dataSnapshot.child(jobs).child("UserId").getValue().toString();
                                                     jobModel.add(new JobListModel(companynamem,rupee,booking_radius,date,description,end_time,special,timeofreporting,jobtitle,employee_Id));
                                                     // JobListModel jobM = dataSnapshot.getValue(JobListModel.class);
                                                     jobAdapter = new JobListAdapter(UrgentJobActivity.this, jobModel);
                                                     View.setAdapter(jobAdapter);

                                                 }

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

// Log.d("id","Urgent jobs=="+id);
////                                    tex.setText(String.valueOf(id));
////                                    Log.d("k","Applied jobs jobs=="+k);
////                                    Log.d("jobs","Avaliable Jobs"+s);
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
                             Toast.makeText(UrgentJobActivity.this, "data not exist", Toast.LENGTH_SHORT).show();
                         }
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {
                     }
                 });
             }




























    }
}
