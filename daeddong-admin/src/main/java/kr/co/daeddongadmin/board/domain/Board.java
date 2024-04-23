package kr.co.daeddongadmin.board.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Board {
    private String seq = "";
    private String bbsId = "";
    private String openYn = "";
    private String title = "";
    private String contents = "";
    private String fileId = "";
    private String inputNm = "";
    private String inputDate = "";
    private String editNm = "";
    private String editDate = "";
    private String hit = "";
    private String orders = "";

}
