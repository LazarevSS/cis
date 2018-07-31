package ru.sibintek.cis.model;

import java.io.Serializable;
import java.util.List;

public class IrModel implements Serializable {
    private int id;
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
    private int is_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIs_name_short() {
        return is_name_short;
    }

    public void setIs_name_short(String is_name_short) {
        this.is_name_short = is_name_short;
    }

    public String getIs_name_short_str() {
        return is_name_short_str;
    }

    public void setIs_name_short_str(String is_name_short_str) {
        this.is_name_short_str = is_name_short_str;
    }

    public String getIr_code() {
        return ir_code;
    }

    public void setIr_code(String ir_code) {
        this.ir_code = ir_code;
    }

    public String getIr_name() {
        return ir_name;
    }

    public void setIr_name(String ir_name) {
        this.ir_name = ir_name;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(String is_owner) {
        this.is_owner = is_owner;
    }

    public List<String> getObj_num_path() {
        return obj_num_path;
    }

    public void setObj_num_path(List<String> obj_num_path) {
        this.obj_num_path = obj_num_path;
    }

    public String getIr_owner() {
        return ir_owner;
    }

    public void setIr_owner(String ir_owner) {
        this.ir_owner = ir_owner;
    }

    public String getIr_num() {
        return ir_num;
    }

    public void setIr_num(String ir_num) {
        this.ir_num = ir_num;
    }

    public String getIs_type() {
        return is_type;
    }

    public void setIs_type(String is_type) {
        this.is_type = is_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_name() {
        return is_name;
    }

    public void setIs_name(String is_name) {
        this.is_name = is_name;
    }

    public int getIs_num() {
        return is_num;
    }

    public void setIs_num(int is_num) {
        this.is_num = is_num;
    }
}
