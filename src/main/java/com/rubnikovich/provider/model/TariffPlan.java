package com.rubnikovich.provider.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name= "tariff_plans")
public class TariffPlan {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "traffic_limit")
    private BigDecimal trafficLimit;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "is_archived")
    private boolean isArchived;

    public TariffPlan(){
    }

    public TariffPlan(int id, String planName, BigDecimal price,
                      BigDecimal trafficLimit, BigDecimal discount,
                      boolean isArchived) {
        this.id = id;
        this.planName = planName;
        this.price = price;
        this.trafficLimit = trafficLimit;
        this.discount = discount;
        this.isArchived = isArchived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTrafficLimit() {
        return trafficLimit;
    }

    public void setTrafficLimit(BigDecimal trafficLimit) {
        this.trafficLimit = trafficLimit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
