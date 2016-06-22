package com.yangchangfu.pickview_lib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yangchangfu.pickview_lib.WheelView.OnWheelChangedListener;
import com.yangchangfu.pickview_lib.WheelView.WheelView;
import com.yangchangfu.pickview_lib.WheelView.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/4/25.
 */
public class PickView extends LinearLayout implements OnWheelChangedListener, View.OnClickListener {

    private Context mContext;
    private View view = null;
    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayout;
    private PopupWindow mPopWindow;
    private WheelView[] wheelViews;
    private Object[] mDatas;
    private int mCount = -1;
    private int mMaxColumn = 3;
    private TextView cancelTextView;
    private TextView sureTextView;
    private TextView selectedTextView;

    private List<Item> mItems;
    private int [] selectedIndexs;

    private OnSelectListener onSelectListener = null;

    public PickView(Context context) {
        this(context, null, 0);

        System.out.println("---UIPickerView(Context context) -----");
    }

    public PickView(Context context, View view) {
        this(context, null, 0);

        System.out.println("---UIPickerView(Context context) -----");
        this.view = view;
    }

    public PickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        System.out.println("---UIPickerView(Context context, AttributeSet attrs) -----");
    }

    public PickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        System.out.println("---UIPickerView(Context context, AttributeSet attrs, int defStyleAttr) -----");

        this.mContext = context;
        setOrientation(HORIZONTAL);//设置基布局方向为垂直方向
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private void setPickerViewColumns(int columns){

        if (columns <= 0){
            throw new IllegalArgumentException("columns - 参数不匹配, columns不能小于1");
        }

        if (columns > mMaxColumn){
            throw new IllegalArgumentException("columns - 参数不匹配, columns不能大于3");
        }

        mCount = columns;

        wheelViews = new WheelView[mCount];
        mDatas = new Object[mCount];
        selectedIndexs = new int[mCount];

        mRelativeLayout = new RelativeLayout(mContext);
        mRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRelativeLayout.setBackgroundColor(Color.parseColor("#4f000000"));

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(VERTICAL);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        linearLayout.setLayoutParams(layoutParams);
        mRelativeLayout.addView(linearLayout);

        LayoutParams layoutParams0 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, 45));
        LinearLayout linearLayout1 = new LinearLayout(mContext);
        linearLayout1.setOrientation(HORIZONTAL);
        linearLayout1.setLayoutParams(layoutParams0);
        linearLayout1.setBackgroundColor(Color.WHITE);
        linearLayout.addView(linearLayout1);

        LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cancelTextView = new TextView(mContext);
        cancelTextView.setText("取消");
        cancelTextView.setTextColor(Color.parseColor("#00abdd"));
        cancelTextView.setTextSize(18);
        cancelTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        cancelTextView.setPadding(dip2px(mContext, 16), 0, 0, 0);
        cancelTextView.setLayoutParams(layoutParams1);
        cancelTextView.setOnClickListener(this);
        linearLayout1.addView(cancelTextView);

        selectedTextView = new TextView(mContext);
        selectedTextView.setText("暂无");
        selectedTextView.setTextColor(Color.parseColor("#d0d0d0"));
        selectedTextView.setTextSize(16);
        selectedTextView.setSingleLine(true);
        selectedTextView.setGravity(Gravity.CENTER);
        selectedTextView.setPadding(dip2px(mContext, 5), 0, dip2px(mContext, 5), 0);
        selectedTextView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        selectedTextView.setOnClickListener(this);
        selectedTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Toast.makeText(mContext, "textView", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        linearLayout1.addView(selectedTextView);

        LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        sureTextView = new TextView(mContext);
        sureTextView.setText("确定");
        sureTextView.setTextColor(Color.parseColor("#00abdd"));
        sureTextView.setTextSize(18);
        sureTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        sureTextView.setPadding(0, 0, dip2px(mContext, 16), 0);
        sureTextView.setLayoutParams(layoutParams2);
        sureTextView.setOnClickListener(this);
        linearLayout1.addView(sureTextView);

        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLinearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.addView(mLinearLayout);

        //初始化
        for (int index=0; index<mCount; index++){

            wheelViews[index] = new WheelView(mContext);
            wheelViews[index].setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            //ArrayWheelAdapter adapter = new ArrayWheelAdapter<String>(mContext, items);
            //wheelViews[index].setViewAdapter(adapter);
            wheelViews[index].setVisibleItems(7);
            //wheelViews[index].setWheelBackground(Resources.getSystem().getColor(Color.WHITE));
            mLinearLayout.addView(wheelViews[index], index);

            wheelViews[index].setTag(index);
            wheelViews[index].addChangingListener(this);
        }
    }

    private void setPickerViewDatasAtIndex(int index, List<String> lists){

        if (index < 0 || index > mCount - 1){
            throw new IllegalArgumentException("index - 参数不匹配, index不能大于" + String.valueOf(mCount - 1) + "或不能小于0");
        }

        if (lists.size() == 0 || lists == null){
            throw new IllegalArgumentException("lists - 参数不匹配, lists不能为null或空");
        }

        String[] items =  (String[])lists.toArray(new String[lists.size()]);

        mDatas[index] = items;

//        wheelViews[index] = new WheelView(mContext);
//        wheelViews[index].setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        ArrayWheelAdapter adapter = new ArrayWheelAdapter<String>(mContext, items);
        wheelViews[index].setViewAdapter(adapter);
        wheelViews[index].setCurrentItem(0);
//        wheelViews[index].setVisibleItems(7);
        //wheelViews[index].setWheelBackground(Resources.getSystem().getColor(Color.WHITE));
//        mLinearLayout.addView(wheelViews[index], index);

//        wheelViews[index].setTag(index);
//        wheelViews[index].addChangingListener(this);
    }

    private void setPickerViewDatasAtIndex(int index, String[] items){

        if (index < 0 || index > mCount - 1){
            throw new IllegalArgumentException("index - 参数不匹配, index不能大于" + String.valueOf(mCount - 1) + "或不能小于0");
        }

        if (items.length == 0 || items == null){
            throw new IllegalArgumentException("items - 参数不匹配, items不能为null或空");
        }

        mDatas[index] = items;

//        wheelViews[index] = new WheelView(mContext);
//        wheelViews[index].setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        ArrayWheelAdapter adapter = new ArrayWheelAdapter<String>(mContext, items);
        wheelViews[index].setViewAdapter(adapter);
//        wheelViews[index].setVisibleItems(7);
        //wheelViews[index].setWheelBackground(Color.WHITE);
//        mLinearLayout.addView(wheelViews[index], index);

//        wheelViews[index].setTag(index);
//        wheelViews[index].addChangingListener(this);
    }

    public void show() {

        int num = 0;
        for (int i=0; i<mCount; i++){
            if (wheelViews[i] == null){
                num ++;
            }
        }

        if (num == mCount){
            throw new IllegalArgumentException("UIPickerView 无数据填充，无法显示");
        }

        mPopWindow = new PopupWindow(mRelativeLayout);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setAnimationStyle(R.style.contextMenuAnim);//设置动画所对应的style
        mPopWindow.setFocusable(false);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM, 0, 0);

        // popView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mRelativeLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });
    }

    public void dismiss(){

        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
            mPopWindow = null;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        int index = (int) wheel.getTag();

        if (mCount == 1){
            int pCurrent = wheelViews[index].getCurrentItem();
            selectedIndexs[0] = pCurrent;
            setSelectedTextView();
        }
        else if (mCount == 2){

            if (index == 0) {
                int pCurrent = wheelViews[index].getCurrentItem();
                selectedIndexs[0] = pCurrent;
                selectedIndexs[1] = 0;
                setPickerViewDatasAtIndex2(1, mItems.get(selectedIndexs[0]).items);
            } else if (index == 1) {
                int pCurrent = wheelViews[index].getCurrentItem();
                selectedIndexs[1] = pCurrent;
            }
            setSelectedTextView();
        }
        else if (mCount == 3) {

            if (index == 0) {
                int pCurrent = wheelViews[index].getCurrentItem();
                selectedIndexs[0] = pCurrent;
                selectedIndexs[1] = 0;
                selectedIndexs[2] = 0;
                setPickerViewDatasAtIndex2(1, mItems.get(selectedIndexs[0]).items);
                setPickerViewDatasAtIndex2(2, mItems.get(selectedIndexs[0]).items.get(selectedIndexs[1]).items);
            } else if (index == 1) {
                int pCurrent = wheelViews[index].getCurrentItem();
                selectedIndexs[1] = pCurrent;
                selectedIndexs[2] = 0;
                setPickerViewDatasAtIndex2(2, mItems.get(selectedIndexs[0]).items.get(selectedIndexs[1]).items);
            } else if (index == 2) {
                int pCurrent = wheelViews[index].getCurrentItem();
                selectedIndexs[2] = pCurrent;
            }

            setSelectedTextView();
        }
    }

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;

        if (textView == cancelTextView){
            //取消
            //Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
            dismiss();
        }
        else if (textView == sureTextView){
            //确定
            //Toast.makeText(mContext, "确定", Toast.LENGTH_SHORT).show();

            if (onSelectListener != null){

                if (view != null){
                    onSelectListener.OnSelectItemClick(view, selectedIndexs, selectedTextView.getText().toString());
                } else {
                    onSelectListener.OnSelectItemClick(this, selectedIndexs, selectedTextView.getText().toString());
                }
            }

            dismiss();
        }
        else if (textView == selectedTextView){
            Toast.makeText(mContext, "selectedTextView", Toast.LENGTH_SHORT).show();
        }
    }

    public enum Style {
        SINGLE, DOUBLE, THREE
    }

    public void setPickerView(List<Item> items, Style style){

        this.mItems = items;

        if (style == Style.SINGLE)
        {
            mCount = 1;
            setPickerViewColumns(1);//1级
            selectedIndexs[0] = 0;

            setPickerViewDatasAtIndex2(0, items);
            setSelectedTextView();
        }
        else if (style == Style.DOUBLE)
        {
            mCount = 2;
            setPickerViewColumns(2);

            selectedIndexs[0] = 0;
            selectedIndexs[1] = 0;

            setPickerViewDatasAtIndex2(0, items);
            setPickerViewDatasAtIndex2(1, items.get(selectedIndexs[0]).items);
            setSelectedTextView();
        }
        else if (style == Style.THREE)
        {
            mCount = 3;
            setPickerViewColumns(3);

            selectedIndexs[0] = 0;
            selectedIndexs[1] = 0;
            selectedIndexs[2] = 0;

            setPickerViewDatasAtIndex2(0, items);
            setPickerViewDatasAtIndex2(1, items.get(selectedIndexs[0]).items);
            setPickerViewDatasAtIndex2(2, items.get(selectedIndexs[0]).items.get(selectedIndexs[1]).items);
            setSelectedTextView();
        }
    }

    private void setPickerViewDatasAtIndex2(int index, List<Item> lists){

        List<String> list = new ArrayList<>();
        for (int i=0; i<lists.size(); i++){
            list.add(lists.get(i).name);
        }

        setPickerViewDatasAtIndex(index, list);
    }

    private void setSelectedTextView(){

        if (mCount == 1){
            selectedTextView.setText(mItems.get(selectedIndexs[0]).name);
        }
        else if (mCount == 2){
            selectedTextView.setText(mItems.get(selectedIndexs[0]).name + "-" + mItems.get(selectedIndexs[0]).items.get(selectedIndexs[1]).name);
        }
        else if (mCount == 3){
            selectedTextView.setText(mItems.get(selectedIndexs[0]).name + "-" + mItems.get(selectedIndexs[0]).items.get(selectedIndexs[1]).name + "-" + mItems.get(selectedIndexs[0]).items.get(selectedIndexs[1]).items.get(selectedIndexs[2]).name);
        }
    }

    public void setShowSelectedTextView(boolean show){
        selectedTextView.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void OnSelectItemClick(View view, int[] selectedIndexs, String selectedText);
    }
}
