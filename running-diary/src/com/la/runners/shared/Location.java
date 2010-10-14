package com.la.runners.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.annotation.Clag;
import novoda.clag.model.MetaEntity.OnConflictPolicy;

@PersistenceCapable
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Clag(key=true,unique=true,onConflictPolicy=OnConflictPolicy.REPLACE)
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Clag(userId=true,hidden=true)
    @Persistent
    private String userId;
    
    @Persistent
    private Boolean start;
    
    @Persistent
    private Integer latitude;
    
    @Persistent
    private Integer longitude;
    
    @Persistent
    private Float accuracy;
    
    @Persistent
    private Float altitude;
    
    @Persistent
    private Date time;
    
    @Persistent
    private Float speed;
    
    @Persistent
    private Float distance;

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getDistance() {
        return distance;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public Boolean getStart() {
        return start;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
    
    
}