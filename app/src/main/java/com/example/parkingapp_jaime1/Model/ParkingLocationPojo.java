package com.example.parkingapp_jaime1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingLocationPojo {


    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lng")
    @Expose
    public String lng;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cost_per_minute")
    @Expose
    public String costPerMinute;
    @SerializedName("max_reserve_time_mins")
    @Expose
    public int maxReserveTimeMins;
    @SerializedName("min_reserve_time_mins")
    @Expose
    public int minReserveTimeMins;
    @SerializedName("is_reserved")
    @Expose
    public boolean isReserved;
    @SerializedName("reserved_until")
    @Expose
    public String reservedUntil;
}
