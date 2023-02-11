// Course.java - course object abstraction
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris.Database;

import android.chrisnorris.Utilities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    @ColumnInfo(name = "term_id")
    public int term_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_Name() {
        return mCourse_Name;
    }

    public void setCourse_Name(String mCourse_Name) {
        this.mCourse_Name = mCourse_Name;
    }

    public String getStart_Date() {
        return mStart_Date;
    }

    public void setStart_Date(String mStart_Date) {
        this.mStart_Date = mStart_Date;
    }

    public String getEnd_Date() {
        return mEnd_Date;
    }

    public void setEnd_Date(String mEnd_Date) {
        this.mEnd_Date = mEnd_Date;
    }

    public String getInstructor() {
        return mInstructor;
    }

    public void setInstructor(String mInstructor) {
        this.mInstructor = mInstructor;
    }

    public String getInstructor_Email() {
        return mInstructor_Email;
    }

    public void setInstructor_Email(String mInstructor_Email) {
        this.mInstructor_Email = mInstructor_Email;
    }

    public String getInstructor_Phone() {
        return mInstructor_Phone;
    }

    public void setInstructor_Phone(String mInstructor_Phone) {
        this.mInstructor_Phone = mInstructor_Phone;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    @ColumnInfo(name = "course_name")
    private String mCourse_Name = "";

    @ColumnInfo(name = "start_date")
    private String mStart_Date = "";

    @ColumnInfo(name = "end_date")
    private String mEnd_Date = "";

    @ColumnInfo(name = "instructor")
    private String mInstructor = "";

    @ColumnInfo(name = "instructor_email")
    private String mInstructor_Email = "";

    @ColumnInfo(name = "instructor_phone")
    private String mInstructor_Phone = "";

    @ColumnInfo(name = "note")
    private String mNote = "";

    @ColumnInfo(name = "status")
    private String mStatus = "";

    public Course() {
        Date date = new Date();
        mStart_Date = Utilities.dateAsString(date);
    }

    public Course(String course_name, String start_date, String end_date, String instructor, String instructor_email, String instructor_phone, String note) {
        mCourse_Name = course_name;
        mStart_Date = start_date;
        mEnd_Date = end_date;
        mInstructor = instructor;
        mInstructor_Email = instructor_email;
        mInstructor_Phone = instructor_phone;
        mNote = note;
    }

    public String asString() {
        return
                "Name: " + mCourse_Name +
                "\nStart Date: " + mStart_Date +
                "\nEnd Date: " + mEnd_Date +
                "\nInstructor: " + mInstructor +
                "\nInstructor Email: " + mInstructor_Email +
                "\nInstructor Phone: " + mInstructor_Phone +
                "\nStatus :" + mStatus;
    }
}
