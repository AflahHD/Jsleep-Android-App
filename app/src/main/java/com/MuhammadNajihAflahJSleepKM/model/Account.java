/**
 * Class yang berisi account
 */
package com.MuhammadNajihAflahJSleepKM.model;

import android.content.Context;

import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;

public class Account extends Serializable{
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter renter;

    BaseApiService mApiService;
    Context mContext;

    @Override
    public String toString(){
        return "Account{" +
                "balance=" + balance +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", renter=" + renter +
                '}';
    }
}