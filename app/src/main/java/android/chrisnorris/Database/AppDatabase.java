// AppDatabase.java - application database abstraction
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {

    // c_ = constants
    private static final String c_DATABASE_NAME = "cn_schedulerApp.db";

    // cl_ = class wide variable
    private static AppDatabase cl_APP_DATABASE;

    // Singleton in order to fetch db instance
    public static AppDatabase getInstance(Context context) {
        if (cl_APP_DATABASE == null) {
            cl_APP_DATABASE = Room.databaseBuilder(context, AppDatabase.class,
                    c_DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            String dbPath = context.getDatabasePath(c_DATABASE_NAME).getAbsolutePath();
        }
        return cl_APP_DATABASE;
    }

    // data access objects (DAO) declarations
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
}
