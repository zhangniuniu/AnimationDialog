package zhangniuniu.animationdialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author：zhangyong
 * @email：zhangyonglncn@gmail.com
 * @create_time: 2017/11/22 15:08
 * @description：
 */

public class AnimationDialog extends Dialog {

    public AnimationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    /**
     * 设置Dialog布局
     *
     * @param view
     */
    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        contentView = view;
    }

    int targetViewX, targetViewY;


    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    View contentView, targetView;

    @Override
    public void show() {
        super.show();
        if (targetView != null) {
            resetDialog();
        }
    }

    private void resetDialog() {
        //放大View为原来大小
        contentView.setPivotX(0);
        contentView.setPivotY(0);
        contentView.setScaleX(1);
        contentView.setScaleY(1);
    }


    boolean isDissmissIng = false;

    @Override
    public void dismiss() {
        if (targetView == null) {
            super.dismiss();
            return;
        }
        if (isDissmissIng) {
            return;
        }
        isDissmissIng = true;
        int[] location = new int[2];
        //获取移动最终目标View的位置
        targetView.getLocationOnScreen(location);
        targetViewX = location[0];
        targetViewY = location[1];

        //获取dialog的坐标的位置
        contentView.getLocationOnScreen(location);

        //将背景设置透明
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.dimAmount = 0f;
        dialogWindow.setAttributes(lp);

        //获取将要移动的距离
        final int xLenth = targetViewX - location[0];
        final int yLenth = targetViewY - location[1];

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();

                Window dialogWindow = AnimationDialog.this.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();


                lp.x = (int) (xLenth * value);
                lp.y = (int) (yLenth * value);

                dialogWindow.setAttributes(lp);

                contentView.setPivotX(0);
                contentView.setPivotY(0);

                contentView.setScaleX(1 - value);
                contentView.setScaleY(1 - value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                AnimationDialog.super.dismiss();
                isDissmissIng = false;
                Window dialogWindow = getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                //设置dialog黑色背景透明度
                lp.dimAmount = 0.5f;
                lp.x = 0;
                lp.y = 0;
                dialogWindow.setAttributes(lp);
            }
        });
        animator.setDuration(800);
        animator.start();
    }
}
