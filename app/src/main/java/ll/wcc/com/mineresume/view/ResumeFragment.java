package ll.wcc.com.mineresume.view;

import ll.wcc.com.mineresume.R;
import ll.wcc.com.mineresume.controller.DataController;
import ll.wcc.com.mineresume.controller.IController;
import ll.wcc.com.mineresume.model.MineInfo;
import ll.wcc.com.mineresume.model.ResumeDao;
import ll.wcc.com.mineresume.model.ResumeInfo;
import ll.wcc.com.mineresume.model.ResumeKeys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by 123 on 2017/10/25.
 */

public class ResumeFragment extends Fragment implements IViewController{

    private ResumeInfo mResumeInfo;
    private IViewController mViewController;
    private IController mController;

    private static final String IS_OPEN_EDIT = "is_open_edit";

    private boolean mIsOpenEdit;



    public static ResumeFragment newInstance(boolean isOpenEdit) {
        ResumeFragment newFragment = new ResumeFragment();
        //MineInfo mineInfo = resumeInfo.getMineInfo();
        Bundle bundle = new Bundle();
        /*bundle.putLong(ResumeKeys.ID, resumeInfo.get_id());
        bundle.putString(ResumeKeys.NAME, mineInfo.getName());
        bundle.putBoolean(ResumeKeys.MARRAY, mineInfo.isMarray());
        bundle.putString(ResumeKeys.BIRTHDAY, mineInfo.getBirthday());
        bundle.putString(ResumeKeys.POLITICAL, mineInfo.getPolitical());
        bundle.putInt(ResumeKeys.SEX, mineInfo.getSex());
        bundle.putString(ResumeKeys.NATION, mineInfo.getNation());
        bundle.putString(ResumeKeys.DEGREE, mineInfo.getDegree());
        bundle.putString(ResumeKeys.PHONE, mineInfo.getPhone());
        bundle.putString(ResumeKeys.MAJOR, mineInfo.getMajor());
        bundle.putString(ResumeKeys.EMAIL, mineInfo.geteMail());
        bundle.putString(ResumeKeys.ADDRESS, mineInfo.getAddress());

        bundle.putString(ResumeKeys.EDUCATION_BACKGROUND, ResumeDao.unparseEduBackground(resumeInfo.getEduBackground()));
        bundle.putString(ResumeKeys.MAJOR_COURSE, resumeInfo.getMajorCourse());
        bundle.putString(ResumeKeys.PERSION_ABILITY, resumeInfo.getPersonalAbility());
        bundle.putString(ResumeKeys.COMPUTER_CAPABILITY, resumeInfo.getComputerCapability());
        bundle.putString(ResumeKeys.ENGLISH_LEVEL, resumeInfo.getEnglishLevel());
        bundle.putString(ResumeKeys.AWARDS, resumeInfo.getAwards());
        bundle.putString(ResumeKeys.SELF_ASSESSMENT, resumeInfo.getSelf_assessment());*/

        bundle.putBoolean(IS_OPEN_EDIT, isOpenEdit);

        newFragment.setArguments(bundle);
        //newFragment.mResumeInfo = resumeInfo;
        newFragment.mIsOpenEdit = isOpenEdit;
        newFragment.mResumeInfo = new ResumeInfo();
        newFragment.mResumeInfo.checkEmpty();
        return newFragment;
    }

    public void setResumeInfo(ResumeInfo resumeInfo) {
        if(resumeInfo != null)
            mResumeInfo = resumeInfo;
        setValues(mResumeInfo);
    }

    /*private ResumeInfo obtainResume(Bundle bundle) {

        ResumeInfo resumeInfo = new ResumeInfo();

        long id = bundle.getLong(ResumeKeys.ID);
        String name = bundle.getString(ResumeKeys.NAME);
        boolean marray = bundle.getBoolean(ResumeKeys.MARRAY);
        String birthday = bundle.getString(ResumeKeys.BIRTHDAY);
        String political = bundle.getString(ResumeKeys.POLITICAL);
        int sex = bundle.getInt(ResumeKeys.SEX);
        String nation = bundle.getString(ResumeKeys.NATION);
        String degree = bundle.getString(ResumeKeys.DEGREE);
        String phone = bundle.getString(ResumeKeys.PHONE);
        String major = bundle.getString(ResumeKeys.MAJOR);
        String email = bundle.getString(ResumeKeys.EMAIL);
        String address = bundle.getString(ResumeKeys.ADDRESS);
        String picturePath = bundle.getString(ResumeKeys.PICTURE_PATH);
        MineInfo mineInfo = new MineInfo(name, marray, birthday, political, sex, nation, degree, phone, major, email, address, picturePath);

        String education_background = bundle.getString(ResumeKeys.EDUCATION_BACKGROUND);

        HashMap<String, String> educationBackMap = ResumeDao.parseEduBackground(education_background);

        String major_course = bundle.getString(ResumeKeys.MAJOR_COURSE);
        String persion_ability = bundle.getString(ResumeKeys.PERSION_ABILITY);
        String computer_capability = bundle.getString(ResumeKeys.COMPUTER_CAPABILITY);
        String english_level = bundle.getString(ResumeKeys.ENGLISH_LEVEL);
        String awards = bundle.getString(ResumeKeys.AWARDS);
        String self_assessment = bundle.getString(ResumeKeys.SELF_ASSESSMENT);

        resumeInfo.set_id(id);
        resumeInfo.setAwards(awards);
        resumeInfo.setComputerCapability(computer_capability);
        resumeInfo.setEduBackground(educationBackMap);
        resumeInfo.setEnglishLevel(english_level);
        resumeInfo.setMajorCourse(major_course);
        resumeInfo.setPersonalAbility(persion_ability);
        resumeInfo.setSelf_assessment(self_assessment);
        resumeInfo.setMineInfo(mineInfo);

        return resumeInfo;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = DataController.getInstance(getContext());
        Bundle args = getArguments();
        if(args != null) {
           // mResumeInfo = obtainResume(args);
            mIsOpenEdit = args.getBoolean(IS_OPEN_EDIT);
        }


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.resume_page, null);
        mViewController = new ViewController(view, (IViewModify) getActivity());
        init(mResumeInfo, mIsOpenEdit);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void init(ResumeInfo resumeInfo, boolean isOpenEdit) {
        if(mViewController != null)
            mViewController.init(resumeInfo, isOpenEdit);
    }

    @Override
    public void setValues(ResumeInfo resumeInfo) {
        mResumeInfo = resumeInfo;
        Log.i("INFO", "ResumeFragment->setValues->mViewController->" + mViewController);
        if(mViewController != null)
            mViewController.setValues(mResumeInfo);
    }

    @Override
    public ResumeInfo getValues() {
        if(mViewController != null)
            return mViewController.getValues();
        return mResumeInfo;
    }

    @Override
    public void openEdit() {
        if(mViewController != null)
            mViewController.openEdit();
    }

    @Override
    public void closeEdit() {
        if(mViewController != null)
            mViewController.closeEdit();
    }

    @Override
    public void restroyEdit(ResumeInfo resumeInfo) {
        if(mViewController != null)
            mViewController.restroyEdit(resumeInfo);
    }

    public ResumeInfo insert() {
        mResumeInfo = getValues();
        mResumeInfo.checkEmpty();
        long id = mController.insertResume(mResumeInfo);
        Log.i("INFO", "resumeFragment->insert id: " + id);
        mResumeInfo.set_id(id);
        setValues(mResumeInfo);

        clearEditStatus();
        return mResumeInfo;
    }

    public ResumeInfo alert() {
        mResumeInfo = getValues();
        mResumeInfo.checkEmpty();
        Log.i("INFO", "resumeFragment->alert->persionAbility: " + mResumeInfo.getPersonalAbility());
        mController.alertResume(mResumeInfo);

        clearEditStatus();
        return mResumeInfo;
    }

    public void delete(long id) {
        Log.i("INFO", "delete->id: " + id);
        long effect = mController.deleteResume(mResumeInfo.get_id());
        Log.i("INFO", "delete->effect: " + effect);
        clearEditStatus();
    }

    private void clearEditStatus() {
        mIsOpenEdit = false;
        Bundle bundle = getArguments();
        if(bundle != null) {
            bundle.putBoolean(IS_OPEN_EDIT, mIsOpenEdit);
        }
    }

    public void restroyEditText() {
        restroyEdit(mResumeInfo);
    }





}
