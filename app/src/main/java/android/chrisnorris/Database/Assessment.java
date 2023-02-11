// Assessment.java - assessment object abstraction
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

@Entity(tableName = "assessments")
public class Assessment {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @ColumnInfo(name = "course_id")
    public int course_id;

    @ColumnInfo(name="assessment_title")
    private String mAssessment_Title = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessment_Title() {
        return mAssessment_Title;
    }

    public void setAssessment_Title(String mAssessment_Title) {
        this.mAssessment_Title = mAssessment_Title;
    }

    public String getEnd_Date() {
        return mEnd_Date;
    }

    public void setEnd_Date(String mEnd_Date) {
        this.mEnd_Date = mEnd_Date;
    }

    @ColumnInfo(name="end_date")
    private String mEnd_Date = "";

    public Assessment() {
        Date date = new Date();
        mEnd_Date = Utilities.dateAsString(date);
    }

    public Assessment(String title, String end_date) {
        mAssessment_Title = title;
        mEnd_Date = end_date;
    }
}
