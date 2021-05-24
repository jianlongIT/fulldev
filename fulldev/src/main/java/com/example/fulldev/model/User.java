package com.example.fulldev.model;

import com.example.fulldev.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String openid;

    private String nickname;

    private String email;

    private String mobile;

    private String password;

    private Long unifyUid;

//    private String group;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "UserCoupon",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "couponId"))
//    private List<Coupon> couponList;


    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "user", fetch = FetchType.LAZY)

//    @OneToMany
//    @JoinColumn(name="userId")
//    private List<Order> orders = new ArrayList<>();


//    private String

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUnifyUid() {
        return unifyUid;
    }

    public void setUnifyUid(Long unifyUid) {
        this.unifyUid = unifyUid;
    }

    public Map<String, Object> getWxProfile() {
        return wxProfile;
    }

    public void setWxProfile(Map<String, Object> wxProfile) {
        this.wxProfile = wxProfile;
    }

    public User(String openid, String nickname, String email, String mobile, String password, Long unifyUid, Map<String, Object> wxProfile) {
        this.openid = openid;
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.unifyUid = unifyUid;
        this.wxProfile = wxProfile;
    }

    public User() {
    }
}
