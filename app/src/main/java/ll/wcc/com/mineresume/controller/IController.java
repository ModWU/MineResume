package ll.wcc.com.mineresume.controller;

import java.util.List;

import ll.wcc.com.mineresume.model.ResumeInfo;

/**
 * Created by 123 on 2017/10/25.
 */

public interface IController {

    long getCount();
    List<ResumeInfo> getAllResumes();
    long insertResume(ResumeInfo resumeInfo);
    long alertResume(ResumeInfo resumeInfo);
    long deleteResume(long id);
}
