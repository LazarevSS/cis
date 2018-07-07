package ru.sibintek.cis.model;


public class PmFuncAreaEntity {
    private int id;

    private String scenarioName;

    private String funcAreaNum;

    private int fkPmId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getFuncAreaNum() {
        return funcAreaNum;
    }

    public void setFuncAreaNum(String funcareaNum) {
        this.funcAreaNum = funcareaNum;
    }

    public int getFkPmId() {
        return fkPmId;
    }

    public void setFkPmId(int fkPmId) {
        this.fkPmId = fkPmId;
    }
}
