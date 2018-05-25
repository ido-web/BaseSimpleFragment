package itdujun.com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * description:
 * author: dujun
 * created at: 2018/5/25 15:00
 * update: 2018/5/25
 * version: 1.0
 */
public abstract class BaseSimpleFragment extends Fragment {
    private StateLayout stateLayout;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;



    /**
     * 查找View，省去强转操作
     */
    public <T> T findViewById(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) stateLayout.findViewById(id);
        return view;
    }


    //--------------------system method callback------------------------//

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        stateLayout = (StateLayout) inflater.inflate(R.layout.state_layout, null);

        stateLayout.setContentView(getContentView());
       // initView();
        return stateLayout;
    }

    //--------------------------------method---------------------------//

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initData();
        initEvent();
        isFirst = false;
    }


    //--------------------------abstract method------------------------//
//    /**
//     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
//     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
//     */
//    protected abstract void initView();
    /**
     * 返回Fragment的布局Id
     * @return
     */
    protected abstract int getContentView();

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    protected abstract void initView();


    /**
     * 这里获取数据，刷新界面
     */
    protected abstract void initData();


    protected abstract void initEvent();

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();


    /**
     * 加载成功后调用此方法，展示通过 getContentView() 获取的界面
     */
    protected void showContentView(){
        stateLayout.showContentView();
    }

    /**
     * 加载失败后调用此方法，展示一个加载失败的界面
     */
    protected void showFailureView(){
        stateLayout.showFailView();
    }

    /**
     * 网络错误后调用此方法，展示一个网络加载错误的界面
     */
    protected void showNetErrorView(){
        stateLayout.showNoNetView();
    }

    /**
     * 加载数据中，调用此方法，注意：默认在加载页面前调用了次方法，所以，加载界面前，不用再次调用此方法
     */
    protected void showLoadingView(){
        stateLayout.showLoadingView();
    }

    /**
     * 数据为空后，调用此方法，展示一个空界面
     */
    protected void showEmptyView(){
        stateLayout.showEmptyView();
    }

}
