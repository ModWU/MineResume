package ll.wcc.com.mineresume.model;

/**
 * Created by 123 on 2017/10/25.
 */

public class MineInfo {
    private String name;
    private boolean isMarray;
    private String birthday;
    private String political;
    private int sex;
    private String nation;
    private String degree;
    private String phone;
    private String major;
    private String eMail;
    private String address;
    private String picturePath;

    public MineInfo() {};

    public MineInfo(String name, boolean isMarray, String birthday, String political, int sex, String nation, String degree, String phone, String major, String eMail, String address, String picturePath) {
        this.name = name;
        this.isMarray = isMarray;
        this.birthday = birthday;
        this.political = political;
        this.sex = sex; //0:男   other:女
        this.nation = nation;
        this.degree = degree;
        this.phone = phone;
        this.major = major;
        this.eMail = eMail;
        this.address = address;
        this.picturePath = picturePath;
    }

    public void copy(MineInfo resumeInfo) {
        this.name = resumeInfo.name;
        this.isMarray = resumeInfo.isMarray();
        this.birthday = resumeInfo.birthday;
        this.political = resumeInfo.political;
        this.sex = resumeInfo.sex; //0:男   other:女
        this.nation = resumeInfo.nation;
        this.degree = resumeInfo.degree;
        this.phone = resumeInfo.phone;
        this.major = resumeInfo.major;
        this.eMail = resumeInfo.eMail;
        this.address = resumeInfo.address;
        this.picturePath = resumeInfo.picturePath;
    }

    public void checkEmpty() {
        if(name == null) name = "--";
        if(birthday == null) birthday = "--";
        if(political == null) political = "--";
        if(nation == null) nation = "--";
        if(degree == null) degree = "--";
        if(phone == null) phone = "--";
        if(major == null) major = "--";
        if(eMail == null) eMail = "--";
        if(address == null) address = "--";
        if(picturePath == null) picturePath = "--";
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMarray() {
        return isMarray;
    }

    public void setMarray(boolean marray) {
        isMarray = marray;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sax) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
