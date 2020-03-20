package com.example.employeetesting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobListViewHolder>{

    Context mCtx;
    List<JobListModel> jobListModelList;

    public JobListAdapter(Context mCtx, List<JobListModel> jobListModelList) {
        this.mCtx = mCtx;
        this.jobListModelList = jobListModelList;
    }

    @NonNull
    @Override
    public JobListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_active_job_list_view, parent, false);
        JobListViewHolder jobListViewHolder = new JobListViewHolder(view);
        return jobListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobListViewHolder holder, int position) {

        JobListModel jobModel=jobListModelList.get(position);

        holder.txtCompantName.setText(jobModel.getCompany_name());
      //  holder.txtJobspecial.setText(jobModel.getJob_Special());
        holder.txtJobStartTime.setText(jobModel.getJob_Start_Time());
        holder.txtJobEndTime.setText(jobModel.getJob_End_Time());
        holder.txtJobDate.setText(jobModel.getJob_Date());
        holder.txtBookingRadius.setText(jobModel.getJob_Booking_Radius());
        holder.txtAmount.setText(jobModel.getJob_Amount());
        holder.txtJobTitle.setText(jobModel.getJobTitle());
        holder.txtJobDesc.setText(jobModel.getJob_Desc());
        holder.txtEmployeeId.setText(jobModel.getUserId());

        holder.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(mCtx,ApplyForJob.class);
                      mCtx.startActivity(in);
            }
        });


    }

    @Override
    public int getItemCount() {
        return jobListModelList.size();
    }


    public class JobListViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompantName,txtJobTitle,txtAmount,txtBookingRadius,txtJobDate,txtJobDesc,txtJobEndTime,txtJobspecial,txtJobStartTime,txtEmployeeId;

        Button btnApply;
        public JobListViewHolder(@NonNull View itemView) {
            super(itemView);

            btnApply=itemView.findViewById(R.id.btnApply);
            txtCompantName=itemView.findViewById(R.id.tv_compId2);
            txtAmount=itemView.findViewById(R.id.tv_amount);
            txtJobTitle=itemView.findViewById(R.id.tv_title);
            txtBookingRadius=itemView.findViewById(R.id.tv_job_booking_radius);
            txtJobDesc=itemView.findViewById(R.id.tv_desc);
            txtJobDate=itemView.findViewById(R.id.tv_date);
            txtJobEndTime=itemView.findViewById(R.id.tv_startTimeId);
            txtJobStartTime=itemView.findViewById(R.id.tv_totalTimeId);
            txtJobspecial=itemView.findViewById(R.id.tv_bleck_pant);
            txtEmployeeId=itemView.findViewById(R.id.tv_employer_userId);
        }

    }
}
