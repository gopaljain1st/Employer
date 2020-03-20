package com.example.employeetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FindJob extends AppCompatActivity {

    RecyclerView recyclerView;
    JobListAdapter jobAdapter;
   List<JobListModel> jobModel;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,ds1,databseUrgent;
    int s;
    int id=0,k=0,ur,total,av;
    String user;
    String jobtitle,companynamem,description,timeofreporting,duratin,date,rupee,booking_radius,end_time,special,employee_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_job);

        recyclerView=findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        jobModel=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databseUrgent=firebaseDatabase.getReference().child("UrgentJobs");

        databaseReference=firebaseDatabase.getReference().child("Jobs");
       // jobAdapter = new JobListAdapter(FindJob.this, jobModel);
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
                                         s++;
                                         // s=(int)dataSnapshot.getChildrenCount();

                                         jobtitle=dataSnapshots.child(jobs).child("Job_Title").getValue().toString();
                                         companynamem=dataSnapshots.child(jobs).child("Company_name").getValue().toString();
                                         description=dataSnapshots.child(jobs).child("Job_Desc").getValue().toString();
                                         rupee=dataSnapshots.child(jobs).child("Job_Amount").getValue().toString();
                                         date=dataSnapshots.child(jobs).child("Job_Date").getValue().toString();
                                         //employee_Id=dataSnapshot.child(jobs).child("UserId").getValue().toString();
                                        Calendar calendar=Calendar.getInstance();
                                        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

                                        //Log.d("current","urgent job="+currentDate);
                                        //Log.d("date","urgent job="+date);
                                       // date1= sc.next()==date;
                                        //date2 = sc.next();
                                       /* String arr1[] = date.split("/");
                                        String arr2[] = currentDate.split("-");
                                        String arr3[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                                        if(arr1[0].equals(arr2[0])&&arr1[2].equals(arr2[2])&& arr2[1].equals(arr3[Integer.parseInt(arr1[1])-1]))
                                          id++;
                                        else
                                            k++;*/

                                         timeofreporting=dataSnapshots.child(jobs).child("Job_Start_Time").getValue().toString();
                                         end_time=dataSnapshots.child(jobs).child("Job_End_Time").getValue().toString();
                                         booking_radius=dataSnapshots.child(jobs).child("Job_Booking_Radius").getValue().toString();
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
                                        jobModel.add(new JobListModel(companynamem,rupee,booking_radius,date,description,end_time,special,timeofreporting,jobtitle,employee_Id));
                                       // JobListModel jobM = dataSnapshot.getValue(JobListModel.class);
                                        jobAdapter = new JobListAdapter(FindJob.this, jobModel);
                                        recyclerView.setAdapter(jobAdapter);

                                        //jobModel.add(jobM);
                                    }
                                   // UrgentJobs urgentJobs=new UrgentJobs();
                                    //urgentJobs.setUrgentJob(id);
                                    //ur=id;
                                    //total=s;
                                    //av=k;
                                   Log.d("id","Urgent jobs=="+ur);
                                    //Log.d("k","Applied jobs jobs=="+k);
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
                    Toast.makeText(FindJob.this, "data not exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Log.d("id","Urgent jobs=="+ur);
        Log.d("k","Applied jobs jobs=="+total);
        Log.d("jobs","Avaliable Jobs"+av);


//        recyclerView.setAdapter(jobAdapter);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Find Job");
    }
}
