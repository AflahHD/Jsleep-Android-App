package com.MuhammadNajihAflahJSleepKM.model;

public class Invoice {
    public enum RoomRating{
        NONE, BAD, NEUTRAL, GOOD
    }
    public enum PaymentStatus{
        FAILED, WAITING, SUCCESS
    }
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;
}
