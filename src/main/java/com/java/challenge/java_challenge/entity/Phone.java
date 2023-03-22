package com.java.challenge.java_challenge.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    @Column(name = "USER_ID")
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}

