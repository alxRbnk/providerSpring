package com.rubnikovich.provider.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @Column(name = "operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @ManyToOne
    @JoinColumn(name = "user_operation_id", referencedColumnName = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "tariff_plan_id", referencedColumnName = "plan_id")
    private TariffPlan plan;

    public Operation() {
    }

    public Operation(int id, User owner, TariffPlan plan,
                     Date startTime, Date endTime, BigDecimal finalPrice) {
        this.id = id;
        this.owner = owner;
        this.plan = plan;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finalPrice = finalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public TariffPlan getPlan() {
        return plan;
    }

    public void setPlan(TariffPlan plan) {
        this.plan = plan;
    }
}
