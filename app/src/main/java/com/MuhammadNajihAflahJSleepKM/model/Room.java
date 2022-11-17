package com.MuhammadNajihAflahJSleepKM.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    public int size;
    public String name;
    public Facility facility;
    public Price price;
    public String address;
    public BedType bedType;
    public City city;
    public int accountId;
    ArrayList<Date> booked = new ArrayList<Date>();
}
