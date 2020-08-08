package com.capstone.farming.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkingPlan {
    int cntntsNo;
    String fileDownUrlInfo;
    String fileName;

    public WorkingPlan(int cntntsNo, String fileDownUrlInfo, String fileName){
        this.cntntsNo = cntntsNo;
        this.fileDownUrlInfo = fileDownUrlInfo;
        this.fileName = fileName;
    }
}
