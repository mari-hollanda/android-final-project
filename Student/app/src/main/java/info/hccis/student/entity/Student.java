package info.hccis.student.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "student")
public class Student implements Serializable {

    //The names of the variables might not match what is in the DB. This is for backwards compatibility (report).
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fName;
    private String lName;
    private String pNum;
    private String eMail;
    private Integer prog;

    public Student() {
    }

    public Student(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getPNum() {
        return pNum;
    }

    public void setPNum(String pNum) {
        this.pNum = pNum;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public Integer getProg() {
        return prog;
    }

    public void setProg(Integer prog) {
        this.prog = prog;
    }


}
