package kr.co.daeddongadmin.toilet.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Toilet {
    private int seq;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String si;
    private String gungu;
    private String floor;
    private String openTime;
    private String closeTime;
    private String regDt;
    private String modDt;
    private String manageAgency;
    private String maNum;
    private String toiletType;
    private String countMan;
    private String countWomen;
    private String babyYn;
    private String unusualYn;
    private String cctvYn;
    private String alarmYn;
    private String pwdYn;
    private String pwd;
    private String etc;
    private String regId;
    private String modId;
    private String openYn;

//    @Builder
//    public Toilet(String seq,
//                  String name,
//                  String latitiude,
//                  String longitude,
//                  String address,
//                  String si,
//                  String gungu,
//                  String floor,
//                  String openTime,
//                  String closeTime,
//                  String regDt,
//                  String modDt,
//                  String manageAgency,
//                  String maNum,
//                  String tolietType,
//                  String countMan,
//                  String countWomen,
//                  String babyYn,
//                  String unusualYn,
//                  String cctvYn,
//                  String alarmYn,
//                  String pwdYn,
//                  String pwd,
//                  String etc,
//                  String regId,
//                  String modId,
//                  String openYn){
//        this.seq = seq;
//        this.name = name;
//        this.latitiude = latitiude;
//        this.longitude = longitude;
//        this.address = address;
//        this.si = si;
//        this.gungu = gungu;
//        this.floor = floor;
//        this.openTime = openTime;
//        this.closeTime = closeTime;
//        this.regDt = regDt;
//        this.modDt = modDt;
//        this.manageAgency = manageAgency;
//        this.maNum = maNum;
//        this.tolietType = tolietType;
//        this.countMan = countMan;
//        this.countWomen = countWomen;
//        this.babyYn = babyYn;
//        this.unusualYn = unusualYn;
//        this.cctvYn = cctvYn;
//        this.alarmYn = alarmYn;
//        this.pwdYn = pwdYn;
//        this.pwd = pwd;
//        this.etc = etc;
//        this.regId = regId;
//        this.modId = modId;
//        this.openYn = openYn;
//    }
}
