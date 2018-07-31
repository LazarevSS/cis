package ru.sibintek.cis.model;




import java.io.Serializable;
import java.util.List;

public class IsModel implements Serializable {
    private int id;
    private String isNum;
    private String isName;
    private String isOwner;
    private List<CommonModel> commonModels;
    private List<FunctionModel> joinFunctions;

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

    public List<CommonModel> getCommonModels() {
        return commonModels;
    }

    public void setCommonModels(List<CommonModel> commonModels) {
        this.commonModels = commonModels;
    }

    public List<FunctionModel> getJoinFunctions() {
        return joinFunctions;
    }

    public void setJoinFunction(List<FunctionModel> joinFunctions) {
        this.joinFunctions = joinFunctions;
    }
}
