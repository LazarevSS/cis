package ru.sibintek.cis.model;


import java.util.List;

public class FunctionModel {
    private int id;
    private String functionNum;
    private String functionName;
    private String functionParentId;
    private List<String> isIds;
    private List<String> irIds;
    private List<String> faIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFunctionNum() {
        return functionNum;
    }

    public void setFunctionNum(String functionNum) {
        this.functionNum = functionNum;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionParentId() {
        return functionParentId;
    }

    public void setFunctionParentId(String functionParentId) {
        this.functionParentId = functionParentId;
    }

    public List<String> getIsIds() {
        return isIds;
    }

    public void setIsIds(List<String> isIds) {
        this.isIds = isIds;
    }

    public List<String> getIrIds() {
        return irIds;
    }

    public void setIrIds(List<String> irIds) {
        this.irIds = irIds;
    }

    public List<String> getFaIds() {
        return faIds;
    }

    public void setFaIds(List<String> faIds) {
        this.faIds = faIds;
    }
}

