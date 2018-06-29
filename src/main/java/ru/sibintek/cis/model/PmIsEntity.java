package ru.sibintek.cis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PM_IS", schema = "HR")
public class PmIsEntity {
    private long id;
    private String isNum;
    private String isName;
    private String isOwner;

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
    @Column(name = "IS_NUM", nullable = true, length = 20)
    public String getIsNum() {
        return isNum;
    }

    public void setIsNum(String isNum) {
        this.isNum = isNum;
    }

    @Basic
    @Column(name = "IS_NAME", nullable = true, length = 100)
    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    @Basic
    @Column(name = "IS_OWNER", nullable = true, length = 50)
    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmIsEntity that = (PmIsEntity) o;
        return id == that.id &&
                Objects.equals(isNum, that.isNum) &&
                Objects.equals(isName, that.isName) &&
                Objects.equals(isOwner, that.isOwner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, isNum, isName, isOwner);
    }
}
