package itdujun.com.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * description:
 * author: dujun
 * created at: 2017/10/20 16:56
 * update: 2017/10/20
 * version:
 */
public class StateLayout extends RelativeLayout {

    private View loadingView;
    private View failView;
    private View emptyView;
    private View noNetView;
    private View contentView;

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {   // 完成速度接战服的
        super.onFinishInflate();
        loadingView = findViewById(R.id.loadview);
        failView = findViewById(R.id.failure_view);
        emptyView = findViewById(R.id.empty_view);
        noNetView = findViewById(R.id.no_net_view);

        failView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFailViewClickListener != null) {
                    onFailViewClickListener.onFailViewClick();
                }
            }
        });

        noNetView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onFailViewClickListener != null){
                    onFailViewClickListener.onNoNetViewClick();
                }
            }
        });

        showLoadingView();
    }

    /**
     * 展示加载界面
     */
    public void showLoadingView() {
        showView(loadingView);
    }

    /**
     * 展示失败界面
     */
    public void showFailView() {
        showView(failView);
    }

    /**
     * 展示空界面
     */
    public void showEmptyView() {
        showView(emptyView);
    }

    /**
     *  展示正常的界面
     */
    public void showContentView() {  //
        showView(contentView);
    }


    /**
     * 展示无网络界面
     */
    public void showNoNetView(){
        showView(noNetView);
    }

    public void setContentView(int resId) {
        contentView = LayoutInflater.from(getContext()).inflate(resId, this, false);
        // contentView = View.inflate(getContext(), resId, null);
        addView(contentView);
        contentView.setVisibility(View.GONE);
    }

    private void showView(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.setVisibility(childAt == v ? View.VISIBLE : View.GONE);
        }
    }

    private OnViewClickListener onFailViewClickListener;

    public interface OnViewClickListener {
        void onFailViewClick();
        void onNoNetViewClick();
    }

    public void setOnFailViewClickListener(OnViewClickListener onFailViewClickListener) {
        this.onFailViewClickListener = onFailViewClickListener;
    }
}
