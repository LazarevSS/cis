package ru.sibintek.cis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PM_FUNCAREA", schema = "HR")
public class PmFuncareaEntity {
    private long id;
    private String scenarioName;
    private String funcareaNum;

    @Id
    @SequenceGenerator( name = "pm_id_seq", sequenceName = "PM_ID_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "pm_id_seq")
    @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SCENARIO_NAME", nullable = true, length = 250)
    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    @Basic
    @Column(name = "FUNCAREA_NUM", nullable = true, length = 20)
    public String getFuncareaNum() {
        return funcareaNum;
    }

    public void setFuncareaNum(String funcareaNum) {
        this.funcareaNum = funcareaNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmFuncareaEntity that = (PmFuncareaEntity) o;
        return id == that.id &&
                Objects.equals(scenarioName, that.scenarioName) &&
                Objects.equals(funcareaNum, that.funcareaNum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, scenarioName, funcareaNum);
    }
}
