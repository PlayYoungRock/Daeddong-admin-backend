package kr.co.daeddongadmin.board.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Board {
    private String seq;
    private String bbsId;
    private String openYn;
    private String typeEtc;
    private String title;
    private String contents;
    private String fileId;
    private String inputId;
    private String inputNm;
    private String inputDate;
    private String editId;
    private String editNm;
    private String editDate;
    private String hit;
    private String orders;

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
