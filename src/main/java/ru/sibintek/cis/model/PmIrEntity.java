package ru.sibintek.cis.model;

public class PmIrEntity {
    private int id;
    private String scenarioNum;
    private String scenarioType;
    private String irNum;
    private String irName;
    private String irOwner;
    private String instantion;
    private String softwareVersion;
    private int isId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScenarioNum() {
        return scenarioNum;
    }

    public void setScenarioNum(String scenarioNum) {
        this.scenarioNum = scenarioNum;
    }

    public String getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(String scenarioType) {
        this.scenarioType = scenarioType;
    }

    public String getIrNum() {
        return irNum;
    }

    public void setIrNum(String irNum) {
        this.irNum = irNum;
    }

    public String getIrName() {
        return irName;
    }

    public void setIrName(String irName) {
        this.irName = irName;
    }

    public String getIrOwner() {
        return irOwner;
    }

    public void setIrOwner(String irOwner) {
        this.irOwner = irOwner;
    }

    public String getInstantion() {
        return instantion;
    }

    public void setInstantion(String instantion) {
        this.instantion = instantion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public int getIsId() {
        return this.isId;
    }

    public void setIsId(int isId) {
        this.isId = isId;
    }
}
