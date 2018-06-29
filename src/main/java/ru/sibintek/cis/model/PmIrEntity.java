package ru.sibintek.cis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PM_IR", schema = "HR")
public class PmIrEntity {
    private long id;
    private String scenarioNum;
    private String scenarioType;
    private String irNum;
    private String irName;
    private String irOwner;
    private String instantion;
    private String softwareVersion;

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
    @Column(name = "SCENARIO_NUM", nullable = true, length = 20)
    public String getScenarioNum() {
        return scenarioNum;
    }

    public void setScenarioNum(String scenarioNum) {
        this.scenarioNum = scenarioNum;
    }

    @Basic
    @Column(name = "SCENARIO_TYPE", nullable = true, length = 50)
    public String getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(String scenarioType) {
        this.scenarioType = scenarioType;
    }

    @Basic
    @Column(name = "IR_NUM", nullable = true, length = 20)
    public String getIrNum() {
        return irNum;
    }

    public void setIrNum(String irNum) {
        this.irNum = irNum;
    }

    @Basic
    @Column(name = "IR_NAME", nullable = true, length = 100)
    public String getIrName() {
        return irName;
    }

    public void setIrName(String irName) {
        this.irName = irName;
    }

    @Basic
    @Column(name = "IR_OWNER", nullable = true, length = 50)
    public String getIrOwner() {
        return irOwner;
    }

    public void setIrOwner(String irOwner) {
        this.irOwner = irOwner;
    }

    @Basic
    @Column(name = "INSTANTION", nullable = true, length = 50)
    public String getInstantion() {
        return instantion;
    }

    public void setInstantion(String instantion) {
        this.instantion = instantion;
    }

    @Basic
    @Column(name = "SOFTWARE_VERSION", nullable = true, length = 20)
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmIrEntity that = (PmIrEntity) o;
        return id == that.id &&
                Objects.equals(scenarioNum, that.scenarioNum) &&
                Objects.equals(scenarioType, that.scenarioType) &&
                Objects.equals(irNum, that.irNum) &&
                Objects.equals(irName, that.irName) &&
                Objects.equals(irOwner, that.irOwner) &&
                Objects.equals(instantion, that.instantion) &&
                Objects.equals(softwareVersion, that.softwareVersion);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, scenarioNum, scenarioType, irNum, irName, irOwner, instantion, softwareVersion);
    }
}
