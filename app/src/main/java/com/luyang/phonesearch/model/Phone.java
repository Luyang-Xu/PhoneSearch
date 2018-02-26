package com.luyang.phonesearch.model;

/**
 * Created by luyang on 2018/1/13.
 */

public class Phone {

    private String phoneNumber;

    private String province;

    private String carrier;

    private String localCarrier;


    public Phone() {
    }

    public Phone(String phoneNumber, String province, String carrier, String localCarrier) {
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.carrier = carrier;
        this.localCarrier = localCarrier;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setLocalCarrier(String localCarrier) {
        this.localCarrier = localCarrier;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getLocalCarrier() {
        return localCarrier;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", province='" + province + '\'' +
                ", carrier='" + carrier + '\'' +
                ", localCarrier='" + localCarrier + '\'' +
                '}';
    }
}
