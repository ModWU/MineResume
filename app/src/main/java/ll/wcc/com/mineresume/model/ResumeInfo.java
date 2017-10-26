package ll.wcc.com.mineresume.model;

import java.util.HashMap;

/**
 * Created by 123 on 2017/10/25.
 */

public class ResumeInfo {
    private long _id = -1;
    private MineInfo mineInfo;
    private HashMap<String, String> eduBackground = new HashMap<String, String>();
    private String majorCourse;
    private String personalAbility;
    private String computerCapability;
    private String englishLevel;
    private String awards;
    private String self_assessment;


    public MineInfo getMineInfo() {
        return mineInfo;
    }

    public void copy(ResumeInfo resumeInfo) {
        this._id = resumeInfo._id;
        this.mineInfo.copy(resumeInfo.mineInfo);
        this.eduBackground = resumeInfo.eduBackground;
        this.majorCourse = resumeInfo.majorCourse;
        this.personalAbility = resumeInfo.personalAbility;
        this.computerCapability = resumeInfo.computerCapability;
        this.englishLevel = resumeInfo.englishLevel;
        this.awards = resumeInfo.awards;
        this.self_assessment = resumeInfo.self_assessment;
    }


    public void checkEmpty() {
        if(mineInfo == null) mineInfo = new MineInfo();
        mineInfo.checkEmpty();
        if(majorCourse == null) majorCourse = "--";
        if(personalAbility == null) personalAbility = "--";
        if(computerCapability == null) computerCapability = "--";
        if(englishLevel == null) englishLevel = "--";
        if(awards == null) awards = "--";
        if(self_assessment == null) self_assessment = "--";
    }

    public void setMineInfo(MineInfo mineInfo) {
        this.mineInfo = mineInfo;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public HashMap<String, String> getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(HashMap<String, String> eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getMajorCourse() {
        return majorCourse;
    }

    public void setMajorCourse(String majorCourse) {
        this.majorCourse = majorCourse;
    }

    public String getPersonalAbility() {
        return personalAbility;
    }

    public void setPersonalAbility(String personalAbility) {
        this.personalAbility = personalAbility;
    }

    public String getComputerCapability() {
        return computerCapability;
    }

    public void setComputerCapability(String computerCapability) {
        this.computerCapability = computerCapability;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(String englishLevel) {
        englishLevel = englishLevel;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getSelf_assessment() {
        return self_assessment;
    }

    public void setSelf_assessment(String self_assessment) {
        this.self_assessment = self_assessment;
    }
}
