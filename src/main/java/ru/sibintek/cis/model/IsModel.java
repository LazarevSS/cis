package ru.sibintek.cis.model;


import java.util.List;

public class IsModel {
    private int id;
    private String isNum;
    private String isName;
    private String isOwner;
    private List<IrModel> irs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsNum() {
        return isNum;
    }

    public void setIsNum(String isNum) {
        this.isNum = isNum;
    }

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public List<IrModel> getIrs() {
        return irs;
    }

    public void setIrs(List<IrModel> irs) {
        this.irs = irs;
    }
}
