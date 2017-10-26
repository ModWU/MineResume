package ll.wcc.com.mineresume.view;

import ll.wcc.com.mineresume.model.ResumeInfo;

/**
 * Created by 123 on 2017/10/26.
 */

public interface IViewController {
    void init(ResumeInfo resumeInfo, boolean isOpenEdit);
    void setValues(ResumeInfo resumeInfo);
    ResumeInfo getValues();
    void openEdit();
    void closeEdit();
    void restroyEdit(ResumeInfo resumeInfo);
}
