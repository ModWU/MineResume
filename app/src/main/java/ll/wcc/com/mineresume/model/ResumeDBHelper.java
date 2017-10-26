package ll.wcc.com.mineresume.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 123 on 2017/10/25.
 */

public class ResumeDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mineresume.db";
    public static final String TABLE_NAME = "resume";

    public ResumeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (" +
                ResumeKeys.ID + " integer primary key, " +
                ResumeKeys.NAME + " text, " +
                ResumeKeys.MARRAY + " integer, " +
                ResumeKeys.BIRTHDAY + " text, " +
                ResumeKeys.POLITICAL + " text, " +
                ResumeKeys.SEX + " integer, " +
                ResumeKeys.NATION + " text, " +
                ResumeKeys.DEGREE + " text, " +
                ResumeKeys.PHONE + " text, " +
                ResumeKeys.MAJOR + " text, " +
                ResumeKeys.EMAIL + " text, " +
                ResumeKeys.ADDRESS + " text, " +
                ResumeKeys.PICTURE_PATH + " text," +
                ResumeKeys.EDUCATION_BACKGROUND + "  text, " +
                ResumeKeys.MAJOR_COURSE + "  text, " +
                ResumeKeys.PERSION_ABILITY + "  text, " +
                ResumeKeys.COMPUTER_CAPABILITY + "  text, " +
                ResumeKeys.ENGLISH_LEVEL + "  text, " +
                ResumeKeys.AWARDS + "  text, " +
                ResumeKeys.SELF_ASSESSMENT + "  text" +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }


}
