package ll.wcc.com.mineresume.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ll.wcc.com.mineresume.R;
import ll.wcc.com.mineresume.model.MineInfo;
import ll.wcc.com.mineresume.model.ResumeInfo;

/**
 * Created by 123 on 2017/10/25.
 */

public class ViewController implements  IViewController {

    private View rootView;

    private IViewModify mViewModify;

    EditText nameEt, marrayEt, birthdayEt, politicalEt, sexEt, nationEt, degreeEt, phoneEt, majorEt, emailEt, addressEt,
            majorCourseEt, persionAbilityEt, computerCapabilityEt, englishLevelEt, awardsEt, self_assessmentEt;

    List<EditText> allEditTexts = new ArrayList<EditText>();

    ListView educationListView;

    LinearLayout bottomBtnLay;
    Button btnCancel, btnSave;

    TextView idTv;

    public ViewController(View rootView, IViewModify viewModify) {
       this.rootView = rootView;
        mViewModify = viewModify;
    }

    @Override
    public void init(ResumeInfo resumeInfo, boolean isOpenEdit) {
        idTv = (TextView) rootView.findViewById(R.id.id_id);
        nameEt = (EditText) rootView.findViewById(R.id.id_name);
        marrayEt = (EditText) rootView.findViewById(R.id.id_marray);
        birthdayEt = (EditText) rootView.findViewById(R.id.id_birthday);
        sexEt = (EditText) rootView.findViewById(R.id.id_sex);
        politicalEt = (EditText) rootView.findViewById(R.id.id_political);
        nationEt = (EditText) rootView.findViewById(R.id.id_nation);
        degreeEt = (EditText) rootView.findViewById(R.id.id_degree);
        phoneEt = (EditText) rootView.findViewById(R.id.id_phone);

        majorEt = (EditText) rootView.findViewById(R.id.id_major);
        emailEt = (EditText) rootView.findViewById(R.id.id_email);
        addressEt = (EditText) rootView.findViewById(R.id.id_address);
        majorCourseEt = (EditText) rootView.findViewById(R.id.id_majorCourse);
        persionAbilityEt = (EditText) rootView.findViewById(R.id.id_persionAbility);
        computerCapabilityEt = (EditText) rootView.findViewById(R.id.id_computerCapability);
        englishLevelEt = (EditText) rootView.findViewById(R.id.englishLevel);

        awardsEt = (EditText) rootView.findViewById(R.id.id_awards);
        self_assessmentEt = (EditText) rootView.findViewById(R.id.id_self_assessment);

        allEditTexts.clear();
        allEditTexts.addAll(Arrays.asList(nameEt, marrayEt, birthdayEt, politicalEt, sexEt, nationEt, degreeEt, phoneEt, majorEt, emailEt, addressEt,
                majorCourseEt, persionAbilityEt, computerCapabilityEt, englishLevelEt, awardsEt, self_assessmentEt));

        bottomBtnLay = (LinearLayout) rootView.findViewById(R.id.id_bottomBtn_lay);
        btnCancel = (Button) rootView.findViewById(R.id.id_cancel);
        btnSave = (Button) rootView.findViewById(R.id.id_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeEdit();
                if(mViewModify != null)
                    mViewModify.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeEdit();
                if(mViewModify != null)
                    mViewModify.save();
            }
        });

        setValues(resumeInfo);

        Log.i("INFO", "ViewConfroller-->init->isOpenEdit: " + isOpenEdit);

        if(isOpenEdit) {
            openEdit();
        } else {
            closeEdit();
        }


    }

    @Override
    public void openEdit() {
        bottomBtnLay.setVisibility(View.VISIBLE);
        for(EditText perEt : allEditTexts) {
            perEt.setEnabled(true);
        }
    }

    @Override
    public void closeEdit() {
        bottomBtnLay.setVisibility(View.GONE);
        for(EditText perEt : allEditTexts) {
            perEt.setEnabled(false);
        }
    }

    @Override
    public void restroyEdit(ResumeInfo resumeInfo) {
        bottomBtnLay.setVisibility(View.GONE);
        setValues(resumeInfo);
    }


    @Override
    public void setValues(ResumeInfo resumeInfo) {

        Log.i("INFO", "ViewController->setValues->name: " + resumeInfo.getMineInfo().getName());

        if(resumeInfo == null) return;
        resumeInfo.checkEmpty();
        MineInfo mineInfo = resumeInfo.getMineInfo();
        HashMap<String, String> educationBackMap = resumeInfo.getEduBackground();

        idTv.setText(resumeInfo.get_id() + "");
        nameEt.setText(mineInfo.getName());
        marrayEt.setText(getMarrayText(mineInfo.isMarray()));
        birthdayEt.setText(mineInfo.getBirthday());
        politicalEt.setText(mineInfo.getPolitical());
        sexEt.setText(getSexText(mineInfo.getSex()));
        nationEt.setText(mineInfo.getNation());
        degreeEt.setText(mineInfo.getDegree());
        phoneEt.setText(mineInfo.getPhone());
        majorEt.setText(mineInfo.getMajor());
        emailEt.setText(mineInfo.geteMail());
        addressEt.setText(mineInfo.getAddress());

        //暂时忽略：图片和教育背景

        majorCourseEt.setText(resumeInfo.getMajorCourse());
        persionAbilityEt.setText(resumeInfo.getPersonalAbility());
        computerCapabilityEt.setText(resumeInfo.getComputerCapability());
        englishLevelEt.setText(resumeInfo.getEnglishLevel());
        awardsEt.setText(resumeInfo.getAwards());
        self_assessmentEt.setText(resumeInfo.getSelf_assessment());

    }

    @Override
    public ResumeInfo getValues() {
        ResumeInfo resumeInfo = new ResumeInfo();

        long id = Long.valueOf(idTv.getText().toString());
        Log.i("INFO", "getValues->id: " +id);
        String name = nameEt.getText().toString();
        boolean marray = getMarrayBoolean(marrayEt.getText().toString());
        String birthday = birthdayEt.getText().toString();
        String political = politicalEt.getText().toString();
        int sex = getSexInt(sexEt.getText().toString());
        String nation = nationEt.getText().toString();
        String degree = degreeEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String major = majorEt.getText().toString();
        String email = emailEt.getText().toString();
        String address = addressEt.getText().toString();
        String picturePath = "";

        MineInfo mineInfo = new MineInfo(name, marray, birthday, political, sex, nation, degree, phone, major, email, address, picturePath);

        String majorCourse = majorCourseEt.getText().toString();
        String persionAbility = persionAbilityEt.getText().toString();
        String computerCapability = computerCapabilityEt.getText().toString();
        String englishLevel = englishLevelEt.getText().toString();
        String awards =  awardsEt.getText().toString();
        String self_assessment = self_assessmentEt.getText().toString();

        //暂时忽略：图片和教育背景
        resumeInfo.set_id(id);
        resumeInfo.setMineInfo(mineInfo);
        resumeInfo.setMajorCourse(majorCourse);
        resumeInfo.setPersonalAbility(persionAbility);
        resumeInfo.setComputerCapability(computerCapability);
        resumeInfo.setEnglishLevel(englishLevel);
        resumeInfo.setAwards(awards);
        resumeInfo.setSelf_assessment(self_assessment);

        return resumeInfo;
    }

    public String getMarrayText(boolean isMarray) {
        if(isMarray) {
            return "已婚";
        }

        return "未婚";
    }

    public String getSexText(int sex) {
        if(sex == 0) {
            return "男";
        }
        return "女";
    }

    public boolean getMarrayBoolean(String text) {
        if("已婚".equals(text)) {
            return true;
        }
        return false;
    }

    public int getSexInt(String text) {

        if("男".equals(text)) {
            return 0;
        }
        return 1;

    }

}
