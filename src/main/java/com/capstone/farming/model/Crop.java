package com.capstone.farming.model;

public class Crop {
    private int id; // 번호
    private String cntntsSj; // 품종명
    private String unbrngYear; // 육성년도
    private String unbrngInsttInfo; // 육성기관
    private String mainChartrInfo; // 주요특성
    private String imgFileLinkOriginal; // 이미지파일
    private String atchFileLink; // 정보파일

    public Crop(int id, String cntntsSj, String unbrngYear, String unbrngInsttInfo, String mainChartrInfo, String imgFileLinkOriginal, String atchFileLink) {
        this.id = id;
        this.cntntsSj = cntntsSj;
        this.unbrngYear = unbrngYear;
        this.unbrngInsttInfo = unbrngInsttInfo;
        this.mainChartrInfo = mainChartrInfo;
        this.imgFileLinkOriginal = imgFileLinkOriginal;
        this.atchFileLink = atchFileLink;
    }

    public int getId() {
        return id;
    }

    public String getCntntsSj() {
        return cntntsSj;
    }

    public String getUnbrngYear() {
        return unbrngYear;
    }

    public String getUnbrngInsttInfo() {
        return unbrngInsttInfo;
    }

    public String getMainChartrInfo() {
        return mainChartrInfo;
    }

    public String getImgFileLinkOriginal() {
        return imgFileLinkOriginal;
    }

    public String getAtchFileLink() {
        return atchFileLink;
    }
}
