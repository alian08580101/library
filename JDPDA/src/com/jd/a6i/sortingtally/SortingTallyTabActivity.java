package com.jd.a6i.sortingtally;

import android.app.TabActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.jd.a6i.R;

public class SortingTallyTabActivity extends TabActivity implements OnFocusChangeListener{
	
	private TabHost tabHost;
	private int imageViewArray[] = {R.drawable.small_sorting_48,R.drawable.big_sorting_48};
	private String titleArray[] = {"小件分拣","大件分拣"};
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sorting_fragment);
		
		tabHost = getTabHost();
		tabHost.setup();/**getResources().getString(R.string.smallSorting),
	    				      getResources().getDrawable(R.drawable.small_sorting_48)*/
		
		View smallSortingTabView = getTabItemView(0);
		smallSortingTabView.setId(R.id.smallSortingTabId);
		smallSortingTabView.setOnFocusChangeListener(this);
		View bigSortingTabView = getTabItemView(1);
		bigSortingTabView.setId(R.id.bigSortingTabId);
		bigSortingTabView.setOnFocusChangeListener(this);
		tabHost.addTab(tabHost.newTabSpec("small")
	            .setIndicator(smallSortingTabView)
	            .setContent(getIntent().setClass(this, SmallSortingActivity.class)));
		
		tabHost.addTab(tabHost.newTabSpec("big")
	            .setIndicator(bigSortingTabView)
	            .setContent(getIntent().setClass(this, BigSortingActivity.class)));
		tabHost.setCurrentTab(0);
		
		/*fragmentTabHost.setup(this, getSupportFragmentManager(),R.id.layoutTabContent);
		for (int i = 0; i < fragmentArray.length; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = fragmentTabHost.newTabSpec(titleArray[i]).setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			fragmentTabHost.addTab(tabSpec, fragmentArray[i], getIntent().getExtras());
			// 设置Tab按钮的背景
			fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}*/
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
		//view.setBackgroundResource(R.drawable.selector_tab_background);
		TextView textView = (TextView) view.findViewById(R.id.textTab);
		textView.setTextSize(18);
		textView.setText(titleArray[index]);
		textView.setOnFocusChangeListener(this);
		setTextViewDrawable(this, textView, imageViewArray[index], 1);
		Log.d("TabView", "-----"+view.getId());
		return view;
	}
	
	
	
	protected TextView setTextViewDrawable(Context context,TextView textView,int drawableId,int location){
		Drawable drawable= context.getResources().getDrawable(drawableId);
		drawable.setBounds(0, 5, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		switch(location){
		case 0:
			textView.setCompoundDrawables(drawable,null,null,null);
			break;
		case 1:
			textView.setCompoundDrawables(null,drawable,null,null);
			break;
		case 2:
			textView.setCompoundDrawables(null,null,drawable,null);
			break;
		case 3:
			textView.setCompoundDrawables(null,null,null,drawable);
			break;
		}
		return textView;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		Log.d("onFocusChange", "-=-=-=-=-=-=-=");
		if(v.getId()==R.id.smallSortingTabId){
			Log.d("TabFocus", "small---");
		}else if(v.getId()==R.id.bigSortingTabId){
			Log.d("TabFocus", "big---");
		}
	}
}
