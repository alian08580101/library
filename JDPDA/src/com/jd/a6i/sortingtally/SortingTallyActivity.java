package com.jd.a6i.sortingtally;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.db.pojo.MenuItem;

public class SortingTallyActivity extends JDBaseActivity implements OnItemClickListener{
	private static final String TAG = "SortingTallyActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuLayout(initMenu());
	}
	
	public SortingTallyActivity() {
		prepareLayout(R.layout.menu_body);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText("逆向物流分拣理货");
		menuList.setFocusable(true);
		menuList.setFocusableInTouchMode(true);
		menuList.setAdapter(menuItemAdapter);
		menuList.setOnItemClickListener(this);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	protected List<MenuItem> initMenu(){
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		MenuItem warehouseSortingItem = new MenuItem(); 
		warehouseSortingItem.setItemName(resources.getString(R.string.warehouseSorting));
		warehouseSortingItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		warehouseSortingItem.setItemIcon(resources.getDrawable(R.drawable.jd_warehouse_sorting_48));
		
		MenuItem aftersaleSortingItem = new MenuItem();
		aftersaleSortingItem.setItemName(resources.getString(R.string.aftersaleSorting));
		aftersaleSortingItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		aftersaleSortingItem.setItemIcon(resources.getDrawable(R.drawable.jd_aftersale_sorting_48));
		
		MenuItem bMerchantSortingItem = new MenuItem();
		bMerchantSortingItem.setItemName(resources.getString(R.string.bMerchantSorting));
		bMerchantSortingItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		bMerchantSortingItem.setItemIcon(resources.getDrawable(R.drawable.jd_b_merchant_48));
		
		MenuItem stepSortingCenterSortingItem = new MenuItem();
		stepSortingCenterSortingItem.setItemName(resources.getString(R.string.stepSortingCenterSorting));
		stepSortingCenterSortingItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		stepSortingCenterSortingItem.setItemIcon(resources.getDrawable(R.drawable.jd_step_center_sorting_48));
		
		menuItemList.add(warehouseSortingItem);
		menuItemList.add(aftersaleSortingItem);
		menuItemList.add(bMerchantSortingItem);
		menuItemList.add(stepSortingCenterSortingItem);
		
		return menuItemList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		Log.d(TAG, "Mode--touch:"+parent.isInTouchMode());
		sortingTallyIntent(position);
	}
	
	protected void sortingTallyIntent(int position){
		Intent sortingIntent = new Intent(this,SortingTallyTabActivity.class);
		switch(position){
		case 0:
			sortingIntent.putExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.WAREHOUSE_SORTING);
			break;
		case 1:
			sortingIntent.putExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.AFTERSALE_SORTING);
			break;
		case 2:
			sortingIntent.putExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.BMERCHANT_SORTING);
			break;
		case 3:
			sortingIntent.putExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.STEP_SORT_CENTER_SORTING);
			break;
		}
		startActivity(sortingIntent);
	}
}
