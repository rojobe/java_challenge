package com.java.challenge.java_challenge.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PHONES")
public class Phone {

    @Id
    @Column(name = "ID")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();

    @Column(name = "NUMBER")
    private long number;

    @Column(name = "CITY_CODE")
    private int cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


}

