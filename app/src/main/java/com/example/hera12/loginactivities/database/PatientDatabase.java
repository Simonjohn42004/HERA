package com.example.hera12.loginactivities.database;

public class PatientDatabase {
    private String isPregnant;
    private String numberOfAbortions;
    private String FSH;
    private String LH;
    private String FSH_LH;
    private String AMH;
    private String TSH;
    private String Beta1;
    private String Beta2;
    private String waist;
    private String waist_hip_ratio;
    private String hairGrowth;
    private String skinDarkening;
    private String pimples;
    private String leftFollicle;
    private String rightFollicle;
    private String averageLeftFollicleSize;
    private String averageRightFollicleSize;
    private String hasTakenSurvey;

    // Default constructor required for Firestore
    public PatientDatabase() {
    }

    // Constructor with parameters
    public PatientDatabase(
            String isPregnant,
            String numberOfAbortions,
            String FSH,
            String LH,
            String FSH_LH,
            String AMH,
            String TSH,
            String Beta1,
            String Beta2,
            String waist,
            String waist_hip_ratio,
            String hairGrowth,
            String skinDarkening,
            String pimples,
            String leftFollicle,
            String rightFollicle,
            String averageLeftFollicleSize,
            String averageRightFollicleSize,
            String hasTakenSurvey
    ) {
        this.isPregnant = isPregnant;
        this.numberOfAbortions = numberOfAbortions;
        this.FSH = FSH;
        this.LH = LH;
        this.FSH_LH = FSH_LH;
        this.AMH = AMH;
        this.TSH = TSH;
        this.Beta1 = Beta1;
        this.Beta2 = Beta2;
        this.waist = waist;
        this.waist_hip_ratio = waist_hip_ratio;
        this.hairGrowth = hairGrowth;
        this.skinDarkening = skinDarkening;
        this.pimples = pimples;
        this.leftFollicle = leftFollicle;
        this.rightFollicle = rightFollicle;
        this.averageLeftFollicleSize = averageLeftFollicleSize;
        this.averageRightFollicleSize = averageRightFollicleSize;
        this.hasTakenSurvey = hasTakenSurvey;
    }

    // Getters and Setters
    public String getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(String isPregnant) {
        this.isPregnant = isPregnant;
    }

    public String getNumberOfAbortions() {
        return numberOfAbortions;
    }

    public void setNumberOfAbortions(String numberOfAbortions) {
        this.numberOfAbortions = numberOfAbortions;
    }

    public String getFSH() {
        return FSH;
    }

    public void setFSH(String FSH) {
        this.FSH = FSH;
    }

    public String getLH() {
        return LH;
    }

    public void setLH(String LH) {
        this.LH = LH;
    }

    public String getFSH_LH() {
        return FSH_LH;
    }

    public void setFSH_LH(String FSH_LH) {
        this.FSH_LH = FSH_LH;
    }

    public String getAMH() {
        return AMH;
    }

    public void setAMH(String AMH) {
        this.AMH = AMH;
    }

    public String getTSH() {
        return TSH;
    }

    public void setTSH(String TSH) {
        this.TSH = TSH;
    }

    public String getBeta1() {
        return Beta1;
    }

    public void setBeta1(String Beta1) {
        this.Beta1 = Beta1;
    }

    public String getBeta2() {
        return Beta2;
    }

    public void setBeta2(String Beta2) {
        this.Beta2 = Beta2;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getWaist_hip_ratio() {
        return waist_hip_ratio;
    }

    public void setWaist_hip_ratio(String waist_hip_ratio) {
        this.waist_hip_ratio = waist_hip_ratio;
    }

    public String getHairGrowth() {
        return hairGrowth;
    }

    public void setHairGrowth(String hairGrowth) {
        this.hairGrowth = hairGrowth;
    }

    public String getSkinDarkening() {
        return skinDarkening;
    }

    public void setSkinDarkening(String skinDarkening) {
        this.skinDarkening = skinDarkening;
    }

    public String getPimples() {
        return pimples;
    }

    public void setPimples(String pimples) {
        this.pimples = pimples;
    }

    public String getLeftFollicle() {
        return leftFollicle;
    }

    public void setLeftFollicle(String leftFollicle) {
        this.leftFollicle = leftFollicle;
    }

    public String getRightFollicle() {
        return rightFollicle;
    }

    public void setRightFollicle(String rightFollicle) {
        this.rightFollicle = rightFollicle;
    }

    public String getAverageLeftFollicleSize() {
        return averageLeftFollicleSize;
    }

    public void setAverageLeftFollicleSize(String averageLeftFollicleSize) {
        this.averageLeftFollicleSize = averageLeftFollicleSize;
    }

    public String getAverageRightFollicleSize() {
        return averageRightFollicleSize;
    }

    public void setAverageRightFollicleSize(String averageRightFollicleSize) {
        this.averageRightFollicleSize = averageRightFollicleSize;
    }

    public String getHasTakenSurvey() {
        return hasTakenSurvey;
    }

    public void setHasTakenSurvey(String hasTakenSurvey) {
        this.hasTakenSurvey = hasTakenSurvey;
    }

    @Override
    public String toString() {
        return "PatientDatabase{" +
                "isPregnant='" + isPregnant + '\'' +
                ", numberOfAbortions='" + numberOfAbortions + '\'' +
                ", FSH='" + FSH + '\'' +
                ", LH='" + LH + '\'' +
                ", FSH_LH='" + FSH_LH + '\'' +
                ", AMH='" + AMH + '\'' +
                ", TSH='" + TSH + '\'' +
                ", Beta1='" + Beta1 + '\'' +
                ", Beta2='" + Beta2 + '\'' +
                ", waist='" + waist + '\'' +
                ", waist_hip_ratio='" + waist_hip_ratio + '\'' +
                ", hairGrowth='" + hairGrowth + '\'' +
                ", skinDarkening='" + skinDarkening + '\'' +
                ", pimples='" + pimples + '\'' +
                ", leftFollicle='" + leftFollicle + '\'' +
                ", rightFollicle='" + rightFollicle + '\'' +
                ", averageLeftFollicleSize='" + averageLeftFollicleSize + '\'' +
                ", averageRightFollicleSize='" + averageRightFollicleSize + '\'' +
                ", hasTakenSurvey='" + hasTakenSurvey + '\'' +
                '}';
    }
}
