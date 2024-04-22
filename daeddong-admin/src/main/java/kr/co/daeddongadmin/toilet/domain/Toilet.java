package kr.co.daeddongadmin.toilet.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Toilet {
    private int seq = 0;
    private String name ="";
    private double latitude = 0;
    private double longitude = 0;
    private String address ="";
    private String si ="";
    private String gungu ="";
    private String floor ="";
    private String openTime ="";
    private String closeTime ="";
    private String regDt ="";
    private String modDt ="";
    private String manageAgency ="";
    private String maNum ="";
    private String toiletType ="";
    private String countMan ="";
    private String countWomen ="";
    private String babyYn ="";
    private String unusualYn ="";
    private String cctvYn ="";
    private String alarmYn ="";
    private String pwdYn ="";
    private String pwd ="";
    private String etc ="";
    private String regId ="";
    private String modId ="";
    private String openYn ="";
}
