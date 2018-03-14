package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Business implements Serializable {

    public String businessID;
    public  String number;
    public  String name;
    public String primBusiness;
    public String address;
    public String province;

    public Business() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Business(String bID, String number, String name, String primBusiness, String address, String province) {
        this.businessID = bID;
        this.number = number;
        this.name = name;
        this.primBusiness = primBusiness;
        this.address = address;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("number", number);
        result.put("name", name);
        result.put("primBusiness", primBusiness);
        if(address.length() == 0){
            result.put("address", address);
        }
        if(province.length() == 0){
            result.put("province", province);
        }
        return result;
    }


}
