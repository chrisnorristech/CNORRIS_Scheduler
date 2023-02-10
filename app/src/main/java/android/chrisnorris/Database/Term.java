package android.chrisnorris.Database;

import android.chrisnorris.Utilities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "terms")
public class Term {
    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public String getTerm_Name() {
        return mTerm_Name;
    }

    public void setTerm_Name(String mTerm_Name) {
        this.mTerm_Name = mTerm_Name;
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

    @ColumnInfo(name = "term_name")
    private String mTerm_Name = "";

    @ColumnInfo(name = "start_date")
    private String mStart_Date = "";

    @ColumnInfo(name = "end_date")
    private String mEnd_Date = "";

    public Term() {
        Date date = new Date();
        mStart_Date = Utilities.dateAsString(date);
    }

    public Term(String term_name, String start_date, String end_date) {
        mTerm_Name = term_name;
        mStart_Date = start_date;
        mEnd_Date = end_date;
    }

    public String asString() {
    return "Term Name: " + mTerm_Name + "\nStart Date:" + mStart_Date + "\nEnd Date:" + mEnd_Date;
    }
}
