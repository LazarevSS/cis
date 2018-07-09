package ru.sibintek.cis.model.dto;

public class FunctionAndRelatedJoin {
    private int irId;
    private int faId;
    private String faName;
    private int fuId;
    private String fuName;
    private int joinType;
    private int jFuId;
    private String jFuName;
    private int jFaId;
    private String jFaName;
    private int jIrId;

    public int getFaId() {
        return faId;
    }

    public void setFaId(int faId) {
        this.faId = faId;
    }

    public String getFaName() {
        return faName;
    }

    public void setFaName(String faFame) {
        this.faName = faFame;
    }

    public int getFuId() {
        return fuId;
    }

    public void setFuId(int fuId) {
        this.fuId = fuId;
    }

    public String getFuName() {
        return fuName;
    }

    public void setFuName(String fuName) {
        this.fuName = fuName;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public int getjFuId() {
        return jFuId;
    }

    public void setjFuId(int jFuId) {
        this.jFuId = jFuId;
    }

    public String getjFuName() {
        return jFuName;
    }

    public void setjFuName(String jFuName) {
        this.jFuName = jFuName;
    }

    public int getjFaId() {
        return jFaId;
    }

    public void setjFaId(int jFaId) {
        this.jFaId = jFaId;
    }

    public String getjFaName() {
        return jFaName;
    }

    public void setjFaName(String jFaName) {
        this.jFaName = jFaName;
    }

    public int getIrId() {
        return irId;
    }

    public void setIrId(int irId) {
        this.irId = irId;
    }

    public int getjIrId() {
        return jIrId;
    }

    public void setjIrId(int jIrId) {
        this.jIrId = jIrId;
    }
}
