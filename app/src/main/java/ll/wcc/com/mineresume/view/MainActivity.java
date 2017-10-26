package ll.wcc.com.mineresume.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ll.wcc.com.mineresume.R;
import ll.wcc.com.mineresume.controller.DataController;
import ll.wcc.com.mineresume.controller.IController;
import ll.wcc.com.mineresume.model.ResumeInfo;

public class MainActivity extends FragmentActivity implements IViewModify {


    public static final int SELECT_NULL = -1;
    public static final int SELECT_CREATE = 0;
    public static final int SELECT_ALERT = 1;
    public static final int SELECT_DELETE = 2;

    private int mCurrentSelection = SELECT_NULL;

    public static final int PAGE_EMPTY = 0;
    public static final int PAGE_RESUME = 1;

    private IController mController;

    private LinearLayout mEmptyLay, mResumeLay;

    private SwipeRefreshLayout mSrLay;

    private ViewPager mViewPager;

    private LinkedList<ResumeFragment> mFragmentList = new LinkedList<ResumeFragment>();

    private LinkedList<ResumeInfo> mData = new LinkedList<ResumeInfo>();

    private FragmentPagerAdapter mFragmentAdapter;

    private long mCount = 0;

    private int mFragmentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
        initData();
    }

    private void initViews() {
        mEmptyLay = (LinearLayout) findViewById(R.id.id_empty_lay);
        mResumeLay = (LinearLayout) findViewById(R.id.id_resume_lay);
        mSrLay = (SwipeRefreshLayout) findViewById(R.id.id_sr_lay);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager_lay);

        mSrLay.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSrLay.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSrLay.setEnabled(false);
        Log.i("INFO", "initViews.");
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.i("INFO", "onRefresh...");
            loadResumeDatasAndShow();
        }

    };

    private void setListeners() {
        mSrLay.setOnRefreshListener(mRefreshListener);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //dosomething
                mFragmentIndex = position;
                Log.i("INFO", "scroll mFragmentIndex: " + mFragmentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, SELECT_CREATE, SELECT_CREATE, getResources().getString(R.string.menu_create));
        menu.add(0, SELECT_ALERT, SELECT_ALERT, getResources().getString(R.string.menu_alert));
        menu.add(0, SELECT_DELETE, SELECT_DELETE, getResources().getString(R.string.menu_delete));
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isEmpty = mFragmentList.isEmpty();
        if(isEmpty) {
            menu.removeItem(SELECT_DELETE);
            menu.removeItem(SELECT_ALERT);
        } else {
            if(menu.findItem(SELECT_DELETE) == null)
                menu.add(0, SELECT_DELETE, SELECT_DELETE, getResources().getString(R.string.menu_delete));
            if(menu.findItem(SELECT_ALERT) == null)
                menu.add(0, SELECT_ALERT, SELECT_ALERT, getResources().getString(R.string.menu_alert));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mCurrentSelection == SELECT_DELETE || mCurrentSelection == SELECT_ALERT || mCurrentSelection == SELECT_CREATE) {
            Toast.makeText(this, "等待操作完成...", Toast.LENGTH_SHORT).show();
            return true;
        }
        switch(item.getItemId()) {
            case SELECT_CREATE:
                create();
                break;

            case SELECT_ALERT:
                alert();
                break;

            case SELECT_DELETE:
                delete();
                break;
        }


        return true;
    }

    private void initData() {

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);

        mController = DataController.getInstance(this);
        mCount = mController.getCount();
        if(mCount > 0) {
            showResumePages();
        } else {
            showPageView(PAGE_EMPTY);
        }
    }

    private void showResumePages() {
        showPageView(PAGE_RESUME);
        refresh(true);
        //loadResumeDatasAndShow();
    }

    private void loadResumeDatasAndShow() {

        Observable.create(new ObservableOnSubscribe<List<ResumeInfo>>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ResumeInfo>> observableEmitter) throws Exception {
                Log.i("INFO", "subscribe...");
                List<ResumeInfo> resumeList = mController.getAllResumes();
                observableEmitter.onNext(resumeList);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread(), false,100).subscribe(new Observer<List<ResumeInfo>>() {//
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull List<ResumeInfo> resumeList) {
                Log.i("INFO", "onNext->resumeList size: " + resumeList.size());
                mFragmentList.clear();
                mData.clear();
                mData.addAll(resumeList);
                for(ResumeInfo resumeInfo : resumeList) {
                    mFragmentList.add(ResumeFragment.newInstance(false));
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.i("INFO", "onError");
                refresh(false);
                notifyDataSetChanged();
            }

            @Override
            public void onComplete() {
                Log.i("INFO", "onComplete");
                refresh(false);
                notifyDataSetChanged();
            }
        });

    }

    private void notifyDataSetChanged() {
        int count = mFragmentList.size();
        if(count > 0) {
            mFragmentAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(0);
        } else {
            showPageView(PAGE_EMPTY);
        }
    }

    private void refresh(final boolean isRefresh) {
        mSrLay.post(new Runnable() {
            @Override
            public void run() {
                mSrLay.setRefreshing(isRefresh);
            }
        });
        if(isRefresh) {
            mRefreshListener.onRefresh();
        }
    }

    @Override
    public void create() {
        confirmCreate();
        int count = mFragmentList.size();
        if(count == 1) {
            showPageView(PAGE_RESUME);
            this.invalidateOptionsMenu();
        }


    }

    @Override
    public void alert() {
        mCurrentSelection = SELECT_ALERT;
        mFragmentList.get(mFragmentIndex).openEdit();
    }

    @Override
    public void delete() {
        int count = mFragmentList.size();
        if(count > 0 && count <= 1) {
            confirmDel();
            showPageView(PAGE_EMPTY);
            this.invalidateOptionsMenu();
        } else if(count > 1) {
            confirmDel();
        }

        mCurrentSelection = SELECT_NULL;
       ;
    }

    private void confirmCreate() {
        mCurrentSelection = SELECT_CREATE;
        ResumeInfo resumeInfo = new ResumeInfo();
        resumeInfo.checkEmpty();
        ResumeFragment resumeFragment = ResumeFragment.newInstance(true);
        mFragmentList.addLast(resumeFragment);
        mData.addLast(resumeInfo);
        mFragmentAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mFragmentList.size() - 1);
        mCount = mFragmentList.size();
    }

    private void confirmDel() {
        mCurrentSelection = SELECT_DELETE;


        ResumeInfo resuInfo = mData.remove(mFragmentIndex);
        mFragmentList.remove(mFragmentIndex).delete(resuInfo.get_id());
        Log.i("INFO", "confirmDel->id: " + resuInfo.get_id());

        for(ResumeInfo ri : mData) {
            Log.i("INFO", "confirmDel->name: " + ri.getMineInfo().getName());
        }

        mFragmentAdapter.notifyDataSetChanged();
       /*if(mFragmentIndex == mFragmentList.size()) {
            mViewPager.setCurrentItem(mFragmentList.size() - 1);
            Log.i("INFO", "11->mFragmentIndex: " + mFragmentIndex);
        } else {
            mViewPager.setCurrentItem(mFragmentIndex);
            Log.i("INFO", "22->mFragmentIndex: " + mFragmentIndex);
        }*/
        mCount = mFragmentList.size();
    }

    @Override
    public void cancel() {
        Log.i("INFO", "MainActivity->cancel->mCurrentSelection: " + mCurrentSelection);
        if(mCurrentSelection == SELECT_CREATE) {
            Log.i("INFO", "MainActivity->cancel->mFragmentList.size() - 1 : " + (mFragmentList.size() - 1));
            mFragmentList.remove(mFragmentList.size() - 1);
            if(!mData.isEmpty())
                 mData.remove(mData.size() - 1);

            int count = mFragmentList.size();
            if(count <= 0) {
                showPageView(PAGE_EMPTY);
                mFragmentIndex = -1;
            } else {
                mFragmentIndex = count - 1;
            }

        } else if(mCurrentSelection == SELECT_ALERT) {
            mFragmentList.get(mFragmentIndex).restroyEditText();
        }

        mFragmentAdapter.notifyDataSetChanged();

        mCount = mFragmentList.size();
        mCurrentSelection = SELECT_NULL;
    }

    @Override
    public void save() {
        if(mCurrentSelection == SELECT_CREATE) {
            ResumeInfo resumeInfo = mFragmentList.get(mFragmentList.size() - 1).insert();
            Log.i("INFO", "save---------->id: " + resumeInfo.get_id() + ", name:" + resumeInfo.getMineInfo().getName());
            mData.get(mFragmentList.size() - 1).copy(resumeInfo);
        } else if(mCurrentSelection == SELECT_ALERT) {
            ResumeInfo resumeInfo = mFragmentList.get(mFragmentIndex).alert();
            mData.get(mFragmentIndex).copy(resumeInfo);
        }

        mCurrentSelection = SELECT_NULL;
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        private FragmentManager mFm;

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
            mFm = fm;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ResumeFragment fragment = (ResumeFragment) super.instantiateItem(container, position);
            ResumeInfo resumeInfo = mData.get(position);
            fragment.setResumeInfo(resumeInfo);
            Log.i("INFO", "instantiateItem：position: " + position);
            Log.i("INFO", "instantiateItem：name: " + resumeInfo.getMineInfo().getName());
            return fragment;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public long getItemId(int position) {
            return mFragmentList.get(position).hashCode();
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private void showPageView(int pageCall) {
        if(pageCall == PAGE_EMPTY) {
            mEmptyLay.setVisibility(View.VISIBLE);
            mResumeLay.setVisibility(View.GONE);
        } else {
            mEmptyLay.setVisibility(View.GONE);
            mResumeLay.setVisibility(View.VISIBLE);
        }
    }

}
