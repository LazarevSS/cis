package ru.sibintek.cis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PM_FUNCTION", schema = "HR")
public class PmFunctionEntity {
    private int id;
    private String functionNum;
    private String functionName;

    @Id
    @SequenceGenerator( name = "pm_id_seq", sequenceName = "PM_ID_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "pm_id_seq")
    @Column(name = "ID", nullable = false, precision = 0)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FUNCTION_NUM", nullable = true, length = 20)
    public String getFunctionNum() {
        return functionNum;
    }

    public void setFunctionNum(String functionNum) {
        this.functionNum = functionNum;
    }

    @Basic
    @Column(name = "FUNCTION_NAME", nullable = true, length = 200)
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmFunctionEntity that = (PmFunctionEntity) o;
        return id == that.id &&
                Objects.equals(functionNum, that.functionNum) &&
                Objects.equals(functionName, that.functionName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, functionNum, functionName);
    }
}
