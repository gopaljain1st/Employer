package com.example.employeetesting.model;

import android.widget.EditText;

public class EmployeeData {

    String et_f_name, et_l_name, et_phone_number,
            et_password,  et_account_number, et_address,
            et_email,et_paytm_number,et_tez_number,et_IFSC_CODE,et_bank_name;

    public EmployeeData() {
    }

    public EmployeeData(String et_f_name, String et_l_name, String et_phone_number, String et_password, String et_account_number, String et_address, String et_email, String et_paytm_number, String et_tez_number, String et_IFSC_CODE, String et_bank_name) {
        this.et_f_name = et_f_name;
        this.et_l_name = et_l_name;
        this.et_phone_number = et_phone_number;
        this.et_password = et_password;
        this.et_account_number = et_account_number;
        this.et_address = et_address;
        this.et_email = et_email;
        this.et_paytm_number = et_paytm_number;
        this.et_tez_number = et_tez_number;
        this.et_IFSC_CODE = et_IFSC_CODE;
        this.et_bank_name = et_bank_name;
    }

    public String getEt_f_name() {
        return et_f_name;
    }

    public void setEt_f_name(String et_f_name) {
        this.et_f_name = et_f_name;
    }

    public String getEt_l_name() {
        return et_l_name;
    }

    public void setEt_l_name(String et_l_name) {
        this.et_l_name = et_l_name;
    }

    public String getEt_phone_number() {
        return et_phone_number;
    }

    public void setEt_phone_number(String et_phone_number) {
        this.et_phone_number = et_phone_number;
    }

    public String getEt_password() {
        return et_password;
    }

    public void setEt_password(String et_password) {
        this.et_password = et_password;
    }

    public String getEt_account_number() {
        return et_account_number;
    }

    public void setEt_account_number(String et_account_number) {
        this.et_account_number = et_account_number;
    }

    public String getEt_address() {
        return et_address;
    }

    public void setEt_address(String et_address) {
        this.et_address = et_address;
    }

    public String getEt_email() {
        return et_email;
    }

    public void setEt_email(String et_email) {
        this.et_email = et_email;
    }

    public String getEt_paytm_number() {
        return et_paytm_number;
    }

    public void setEt_paytm_number(String et_paytm_number) {
        this.et_paytm_number = et_paytm_number;
    }

    public String getEt_tez_number() {
        return et_tez_number;
    }

    public void setEt_tez_number(String et_tez_number) {
        this.et_tez_number = et_tez_number;
    }

    public String getEt_IFSC_CODE() {
        return et_IFSC_CODE;
    }

    public void setEt_IFSC_CODE(String et_IFSC_CODE) {
        this.et_IFSC_CODE = et_IFSC_CODE;
    }

    public String getEt_bank_name() {
        return et_bank_name;
    }

    public void setEt_bank_name(String et_bank_name) {
        this.et_bank_name = et_bank_name;
    }
}
