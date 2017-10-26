package ll.wcc.com.mineresume.controller;

import android.content.Context;

import java.util.List;

import ll.wcc.com.mineresume.model.ResumeDao;
import ll.wcc.com.mineresume.model.ResumeInfo;

/**
 * Created by 123 on 2017/10/25.
 */

public class DataController implements IController {

    private static volatile  DataController instance;

    private ResumeDao mResumeDao;

    private Context mContext;

    private DataController(Context context) {
        mResumeDao = new ResumeDao(context);
    }

    private void resetResumeDao(Context context) {
        mResumeDao = new ResumeDao(context);
    }

    public static DataController getInstance(Context context) {
        if(instance == null)
            synchronized (DataController.class) {
                if(instance == null)
                    instance = new DataController(context);
            }

        if(instance.mContext != null && instance.mContext != context) {
            instance.mContext = context;
            instance.resetResumeDao(context);
        }
        return instance;
    }


    @Override
    public long getCount() {
        return mResumeDao.getCount();
    }

    @Override
    public List<ResumeInfo> getAllResumes() {
        return mResumeDao.queryAllResumes();
    }

    @Override
    public long insertResume(ResumeInfo resumeInfo) {
        return mResumeDao.insertResume(resumeInfo);
    }

    @Override
    public long alertResume(ResumeInfo resumeInfo) {
        return mResumeDao.alertResume(resumeInfo);
    }

    @Override
    public long deleteResume(long id) {
        return mResumeDao.deleteResume(id);
    }
}
