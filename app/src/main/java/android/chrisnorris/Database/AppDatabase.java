package android.chrisnorris.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "cn_schedulerApp.db";

    private static AppDatabase mAppDatabase;

    // Singleton
    public static AppDatabase getInstance(Context context) {
        if (mAppDatabase == null) {
            mAppDatabase = Room.databaseBuilder(context, AppDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            String dbPath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
            Log.d("DBLOCATION==>", dbPath);
        }
        return mAppDatabase;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
}
