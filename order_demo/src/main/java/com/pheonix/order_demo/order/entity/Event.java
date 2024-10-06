package com.pheonix.order_demo.order.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "event")
    private List<Coupon> coupons = new ArrayList<>();

    private Long winnerMemberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Long getWinnerMemberId() {
        return winnerMemberId;
    }

    public void setWinnerMemberId(Long winnerMemberId) {
        this.winnerMemberId = winnerMemberId;
    }

    public void winner(Long winnerMemberId) {
        this.setWinnerMemberId(winnerMemberId);
    }

    public boolean nonEmptyUser() {
        return winnerMemberId != null;
    }
}
