package android.chrisnorris.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TermDatabase extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "terms.db";
        private static final int VERSION = 1;

        public TermDatabase(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        private static final class TermTable {
            private static final String TABLE = "term";
            private static final String COL_ID = "_id";
            private static final String COL_NAME = "termName";
            private static final String COL_START = "startDate";
            private static final String COL_END = "endDate";
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TermTable.TABLE + " (" +
                    TermTable.COL_ID + " integer primary key autoincrement, " +
                    TermTable.COL_NAME + " text, " +
                    TermTable.COL_START + " text, " +
                    TermTable.COL_END + " float)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("drop table if exists " + TermTable.TABLE);
            onCreate(db);
        }
}
