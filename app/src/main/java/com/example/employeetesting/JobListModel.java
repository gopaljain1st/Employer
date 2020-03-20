package com.example.employeetesting;

public class JobListModel {

     String Company_name,Job_Amount,Job_Booking_Radius,Job_Date,Job_Desc,Job_End_Time,Job_Special,Job_Start_Time,JobTitle,UserId;

    public JobListModel() {
    }

    public JobListModel(String company_name, String job_Amount, String job_Booking_Radius, String job_Date, String job_Desc, String job_End_Time, String job_Special, String job_Start_Time, String jobTitle, String userId) {
        Company_name = company_name;
        Job_Amount = job_Amount;
        Job_Booking_Radius = job_Booking_Radius;
        Job_Date = job_Date;
        Job_Desc = job_Desc;
        Job_End_Time = job_End_Time;
        Job_Special = job_Special;
        Job_Start_Time = job_Start_Time;
        JobTitle = jobTitle;
        UserId = userId;
    }

    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String company_name) {
        Company_name = company_name;
    }

    public String getJob_Amount() {
        return Job_Amount;
    }

    public void setJob_Amount(String job_Amount) {
        Job_Amount = job_Amount;
    }

    public String getJob_Booking_Radius() {
        return Job_Booking_Radius;
    }

    public void setJob_Booking_Radius(String job_Booking_Radius) {
        Job_Booking_Radius = job_Booking_Radius;
    }

    public String getJob_Date() {
        return Job_Date;
    }

    public void setJob_Date(String job_Date) {
        Job_Date = job_Date;
    }

    public String getJob_Desc() {
        return Job_Desc;
    }

    public void setJob_Desc(String job_Desc) {
        Job_Desc = job_Desc;
    }

    public String getJob_End_Time() {
        return Job_End_Time;
    }

    public void setJob_End_Time(String job_End_Time) {
        Job_End_Time = job_End_Time;
    }

    public String getJob_Special() {
        return Job_Special;
    }

    public void setJob_Special(String job_Special) {
        Job_Special = job_Special;
    }

    public String getJob_Start_Time() {
        return Job_Start_Time;
    }

    public void setJob_Start_Time(String job_Start_Time) {
        Job_Start_Time = job_Start_Time;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
