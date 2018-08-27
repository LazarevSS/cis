package ru.sibintek.cis.model;

import java.io.Serializable;
import java.util.List;

public class CommonModel implements Serializable {
    private String id;
    private String is_name_short;
    private String is_name_short_str;
    private String ir_code;
    private String ir_name;
    private String object_type;
    private String is_owner;
    private List<String> obj_num_path;
    private String ir_owner;
    private String ir_num;
    private String is_type;
    private String name;
    private String is_name;
    private Integer is_num;
    private List<Integer> relation_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsNameShort() {
        return is_name_short;
    }

    public void setIsNameShort(String is_name_short) {
        this.is_name_short = is_name_short;
    }

    public String getIsNameShortStr() {
        return is_name_short_str;
    }

    public void setIsNameShortStr(String is_name_short_str) {
        this.is_name_short_str = is_name_short_str;
    }

    public String getIrCode() {
        return ir_code;
    }

    public void setIrCode(String ir_code) {
        this.ir_code = ir_code;
    }

    public String getIrName() {
        return ir_name;
    }

    public void setIrName(String ir_name) {
        this.ir_name = ir_name;
    }

    public String getObjectType() {
        return object_type;
    }

    public void setObjectType(String object_type) {
        this.object_type = object_type;
    }

    public String getIsOwner() {
        return is_owner;
    }

    public void setIsOwner(String is_owner) {
        this.is_owner = is_owner;
    }

    public List<String> getObjNumPath() {
        return obj_num_path;
    }

    public void setObjNumPath(List<String> obj_num_path) {
        this.obj_num_path = obj_num_path;
    }

    public String getIrOwner() {
        return ir_owner;
    }

    public void setIrOwner(String ir_owner) {
        this.ir_owner = ir_owner;
    }

    public String getIrNum() {
        return ir_num;
    }

    public void setIrNum(String ir_num) {
        this.ir_num = ir_num;
    }

    public String getIsType() {
        return is_type;
    }

    public void setIsType(String is_type) {
        this.is_type = is_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsName() {
        return is_name;
    }

    public void setIsName(String is_name) {
        this.is_name = is_name;
    }

    public int getIsNum() {
        return is_num;
    }

    public void setIsNum(Integer is_num) {
        this.is_num = is_num;
    }

    public List<Integer> getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(List<Integer> relation_id) {
        this.relation_id = relation_id;
    }
}
