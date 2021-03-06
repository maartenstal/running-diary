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
public class Run implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Clag(key=true,unique=true,onConflictPolicy=OnConflictPolicy.REPLACE)
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    /**
     * Time of the creation of the record
     */
    @Persistent
    private Date created;
    
    /**
     * Date of the start of the run
     */
    @Persistent
    private Date startDate;
    
    /**
     * Date of the end of the run
     */
    @Persistent
    private Date endDate;

    /**
     * year of the day of the startDate
     */
    @Persistent
    private Integer year;
    
    /**
     * day of the day of the startDate
     */
    @Persistent
    private Integer day;
    
    /**
     * month of the day of the startDate
     */
    @Persistent
    private Integer month;

    /**
     * hour of the day of the startDate
     */
    @Persistent
    private Long hour;

    @Persistent
    private Date modified;
    
    @Persistent
    private Long distance;

    /**
     * Time required for the run
     */
    @Persistent
    private Long time;
    
    @Persistent
    private String note;
    
    @Persistent
    private String shoes;
    
    @Persistent
    private Integer heartRate;
    
    @Persistent
    private Integer weight;
    
    @Persistent
    private Boolean share;
    
    @Persistent
    private Long speed;
    
    @Clag(userId=true,hidden=true)
    @Persistent
    private String userId;
    
    public Run(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDistance() {
        return distance;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

	public void setShoes(String shoes) {
		this.shoes = shoes;
	}

	public String getShoes() {
		return shoes;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}

	public Boolean getShare() {
		return share;
	}

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Long getHour() {
        return hour;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    
}
