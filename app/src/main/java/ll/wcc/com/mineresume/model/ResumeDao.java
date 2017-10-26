package ll.wcc.com.mineresume.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 123 on 2017/10/25.
 */

public class ResumeDao {

    private Context mContext;
    private ResumeDBHelper mResumeDBHelper;
    private SQLiteDatabase mDb;

    public ResumeDao(Context context) {
        this.mContext = context;
        mResumeDBHelper = new ResumeDBHelper(context);
    }

    public ArrayList<ResumeInfo> queryAllResumes() {
        ArrayList<ResumeInfo> allResumeList = new ArrayList<ResumeInfo>();
        mDb = mResumeDBHelper.getReadableDatabase();
        String querySql = "SELECT * FROM resume";
        Cursor cursor = mDb.rawQuery(querySql, null);
        while(cursor.moveToNext()) {
            ResumeInfo resumeInfo = new ResumeInfo();

            long _id = cursor.getLong(cursor.getColumnIndex(ResumeKeys.ID));

            String name = cursor.getString(cursor.getColumnIndex(ResumeKeys.NAME));
            int isMarray = cursor.getInt(cursor.getColumnIndex(ResumeKeys.MARRAY));
            String birthday = cursor.getString(cursor.getColumnIndex(ResumeKeys.BIRTHDAY));
            String political = cursor.getString(cursor.getColumnIndex(ResumeKeys.POLITICAL));
            int sex = cursor.getInt(cursor.getColumnIndex(ResumeKeys.SEX));
            String nation = cursor.getString(cursor.getColumnIndex(ResumeKeys.NATION));
            String degree = cursor.getString(cursor.getColumnIndex(ResumeKeys.DEGREE));
            String phone = cursor.getString(cursor.getColumnIndex(ResumeKeys.PHONE));
            String major = cursor.getString(cursor.getColumnIndex(ResumeKeys.MAJOR));
            String eMail = cursor.getString(cursor.getColumnIndex(ResumeKeys.EMAIL));
            String address = cursor.getString(cursor.getColumnIndex(ResumeKeys.ADDRESS));
            String picturePath = cursor.getString(cursor.getColumnIndex(ResumeKeys.PICTURE_PATH));
            MineInfo mineInfo = new MineInfo(name, isMarray != 0, birthday, political, sex, nation, degree, phone, major, eMail, address, picturePath);

            String eduBackground = cursor.getString(cursor.getColumnIndex(ResumeKeys.EDUCATION_BACKGROUND));
            HashMap<String, String> eduBackgroundMap = parseEduBackground(eduBackground);
            String majorCourse = cursor.getString(cursor.getColumnIndex(ResumeKeys.MAJOR_COURSE));
            String personalAbility = cursor.getString(cursor.getColumnIndex(ResumeKeys.PERSION_ABILITY));
            String computerCapability  = cursor.getString(cursor.getColumnIndex(ResumeKeys.COMPUTER_CAPABILITY));
            String englishLevel  = cursor.getString(cursor.getColumnIndex(ResumeKeys.ENGLISH_LEVEL));
            String awards = cursor.getString(cursor.getColumnIndex(ResumeKeys.AWARDS));
            String self_assessment = cursor.getString(cursor.getColumnIndex(ResumeKeys.SELF_ASSESSMENT));

            resumeInfo.set_id(_id);
            resumeInfo.setAwards(awards);
            resumeInfo.setComputerCapability(computerCapability);
            resumeInfo.setEduBackground(eduBackgroundMap);
            resumeInfo.setEnglishLevel(englishLevel);
            resumeInfo.setMajorCourse(majorCourse);
            resumeInfo.setPersonalAbility(personalAbility);
            resumeInfo.setSelf_assessment(self_assessment);
            resumeInfo.setMineInfo(mineInfo);

            allResumeList.add(resumeInfo);
        }

        cursor.close();
        mDb.close();

        return allResumeList;
    }

    public long insertResume(ResumeInfo resumeInfo) {
        if(resumeInfo == null) return -1;
        mDb = mResumeDBHelper.getWritableDatabase();

        MineInfo mineInfo = resumeInfo.getMineInfo();

        ContentValues cv = new ContentValues();
        cv.put(ResumeKeys.NAME, mineInfo.getName());
        cv.put(ResumeKeys.MARRAY, mineInfo.isMarray() ? 1 : 0);
        cv.put(ResumeKeys.BIRTHDAY, mineInfo.getBirthday());
        cv.put(ResumeKeys.POLITICAL, mineInfo.getPolitical());
        cv.put(ResumeKeys.SEX, mineInfo.getSex());
        cv.put(ResumeKeys.NATION, mineInfo.getNation());
        cv.put(ResumeKeys.DEGREE, mineInfo.getDegree());
        cv.put(ResumeKeys.PHONE, mineInfo.getPhone());
        cv.put(ResumeKeys.MAJOR, mineInfo.getMajor());
        cv.put(ResumeKeys.EMAIL, mineInfo.geteMail());
        cv.put(ResumeKeys.ADDRESS, mineInfo.getAddress());
        cv.put(ResumeKeys.PICTURE_PATH, mineInfo.getPicturePath());
        cv.put(ResumeKeys.EDUCATION_BACKGROUND, unparseEduBackground(resumeInfo.getEduBackground()));
        cv.put(ResumeKeys.MAJOR_COURSE, resumeInfo.getMajorCourse());
        cv.put(ResumeKeys.PERSION_ABILITY, resumeInfo.getPersonalAbility());
        cv.put(ResumeKeys.COMPUTER_CAPABILITY, resumeInfo.getComputerCapability());
        cv.put(ResumeKeys.ENGLISH_LEVEL, resumeInfo.getEnglishLevel());
        cv.put(ResumeKeys.AWARDS, resumeInfo.getAwards());
        cv.put(ResumeKeys.SELF_ASSESSMENT, resumeInfo.getSelf_assessment());
        long _id = mDb.insert("resume", null, cv);
        mDb.close();
        return _id;
    }

    public long deleteResume(long id) {
        mDb = mResumeDBHelper.getWritableDatabase();
        int _id = mDb.delete("resume", "_id=?", new String[]{id + ""});
        mDb.close();
        return _id;
    }

    public long alertResume(ResumeInfo resumeInfo) {
        if(resumeInfo == null) return -1;
        mDb = mResumeDBHelper.getWritableDatabase();

        MineInfo mineInfo = resumeInfo.getMineInfo();

        ContentValues cv = new ContentValues();
        cv.put(ResumeKeys.NAME, mineInfo.getName());
        cv.put(ResumeKeys.MARRAY, mineInfo.isMarray() ? 1 : 0);
        cv.put(ResumeKeys.BIRTHDAY, mineInfo.getBirthday());
        cv.put(ResumeKeys.POLITICAL, mineInfo.getPolitical());
        cv.put(ResumeKeys.SEX, mineInfo.getSex());
        cv.put(ResumeKeys.NATION, mineInfo.getNation());
        cv.put(ResumeKeys.DEGREE, mineInfo.getDegree());
        cv.put(ResumeKeys.PHONE, mineInfo.getPhone());
        cv.put(ResumeKeys.MAJOR, mineInfo.getMajor());
        cv.put(ResumeKeys.EMAIL, mineInfo.geteMail());
        cv.put(ResumeKeys.ADDRESS, mineInfo.getAddress());
        cv.put(ResumeKeys.PICTURE_PATH, mineInfo.getPicturePath());
        cv.put(ResumeKeys.EDUCATION_BACKGROUND, unparseEduBackground(resumeInfo.getEduBackground()));
        cv.put(ResumeKeys.MAJOR_COURSE, resumeInfo.getMajorCourse());
        cv.put(ResumeKeys.PERSION_ABILITY, resumeInfo.getPersonalAbility());
        cv.put(ResumeKeys.COMPUTER_CAPABILITY, resumeInfo.getComputerCapability());
        cv.put(ResumeKeys.ENGLISH_LEVEL, resumeInfo.getEnglishLevel());
        cv.put(ResumeKeys.AWARDS, resumeInfo.getAwards());
        cv.put(ResumeKeys.SELF_ASSESSMENT, resumeInfo.getSelf_assessment());
        long _id = mDb.update("resume", cv, "_id=?", new String[]{resumeInfo.get_id() + ""});
        mDb.close();
        return _id;
    }

    public long getCount() {
        long count = 0;
        mDb = mResumeDBHelper.getReadableDatabase();
        String querySql = "SELECT * FROM resume";
        Cursor cursor = mDb.rawQuery(querySql, null);
        count = cursor.getCount();
        cursor.close();
        mDb.close();
        return count;
    }

    public static HashMap<String, String> parseEduBackground(String eduBackground) {
        HashMap<String, String> eduBackgroundMap = new HashMap<String, String>();
        if(eduBackground != null) {
            String[] allEduBackgrounds = eduBackground.split(File.pathSeparator);
            Log.i("INTO", "parseEduBackground->size: " + allEduBackgrounds.length);
            for(String perEduBackground : allEduBackgrounds) {
                Log.i("INTO", "parseEduBackground->perEduBackground: " + perEduBackground);
                String key_value[] = perEduBackground.split("|");
                if(key_value.length > 1)
                    eduBackgroundMap.put(key_value[0], key_value[1]);
            }
        }
        return eduBackgroundMap;
    }

    public static String unparseEduBackground(HashMap<String, String> eduBackgroundMap) {
        if(eduBackgroundMap == null) return "";
        StringBuffer buffer = new StringBuffer();

        Set<Map.Entry<String, String>> entrySet = eduBackgroundMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> perEduEntry = iterator.next();
            buffer.append(perEduEntry.getKey() + "|" + perEduEntry.getValue());
            buffer.append(File.pathSeparator);
        }

        if(buffer.length() >= 1)
            buffer.deleteCharAt(buffer.length() - 1);

        return buffer.toString();
    }

}
