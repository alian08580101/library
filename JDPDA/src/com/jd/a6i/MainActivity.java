package com.jd.a6i;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jd.a6i.cancelsorting.CancelSortingActivity;
import com.jd.a6i.db.pojo.MenuItem;
import com.jd.a6i.delivery.DeliveryActivity;
import com.jd.a6i.inspection.InspectionActivity;
import com.jd.a6i.query.QueryActivity;
import com.jd.a6i.sortingtally.SortingTallyActivity;
import com.jd.a6i.task.DownloadAsynctask;


public class MainActivity extends JDBaseActivity implements OnItemClickListener{
	private long touchTime = 0;
	public MainActivity() {
		prepareLayout(R.layout.menu_body);
	}

	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		boolean loginStatus = JDApplication.getInstance().isLogin();
		if(!loginStatus){
			loginError();
		}
		menuLayout(initMenu());
	}
	
	@Override
	protected void onResume() {
		super.onResume();	
		textTitle.setText("DMS逆向物流");
		menuList.setAdapter(menuItemAdapter);
		menuList.setOnItemClickListener(this);
	}
	
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if((currentTime-touchTime)>2000){
			Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
			touchTime = currentTime;
		}else{
			finish();
		}
	}
	
	
	protected List<MenuItem> initMenu(){
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		MenuItem deliveryItem = new MenuItem(); 
		deliveryItem.setItemName(resources.getString(R.string.delivery));
		deliveryItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		deliveryItem.setItemIcon(resources.getDrawable(R.drawable.jd_delivery_48));
		
		MenuItem inspectionItem = new MenuItem();
		inspectionItem.setItemName(resources.getString(R.string.inspection));
		inspectionItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		inspectionItem.setItemIcon(resources.getDrawable(R.drawable.jd_inspection_48));
		
		MenuItem sortingTallyItem = new MenuItem();
		sortingTallyItem.setItemName(resources.getString(R.string.sortingTally));
		sortingTallyItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		sortingTallyItem.setItemIcon(resources.getDrawable(R.drawable.jd_sorting_tally_48));
		
		MenuItem cancelSortingItem = new MenuItem();
		cancelSortingItem.setItemName(resources.getString(R.string.cancelSorting));
		cancelSortingItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		cancelSortingItem.setItemIcon(resources.getDrawable(R.drawable.jd_cancel_sorting_48));
		
		MenuItem queryItem = new MenuItem();
		queryItem.setItemName(resources.getString(R.string.query));
		queryItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		queryItem.setItemIcon(resources.getDrawable(R.drawable.jd_query_48));
		
		menuItemList.add(deliveryItem);
		menuItemList.add(inspectionItem);
		menuItemList.add(sortingTallyItem);
		menuItemList.add(cancelSortingItem);
		menuItemList.add(queryItem);
		return menuItemList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch(position){
		case 0:
			Intent deliveryIntent = new Intent(this,DeliveryActivity.class);
			startActivity(deliveryIntent);
			break;
		case 1:
			Intent inspectionIntent = new Intent(this,InspectionActivity.class);
			startActivity(inspectionIntent);
			break;
		case 2:
			Intent sortingTallyIntent = new Intent(this,SortingTallyActivity.class);
			startActivity(sortingTallyIntent);
			break;
		case 3:
			Intent cancelSortingIntent = new Intent(this,CancelSortingActivity.class);
			startActivity(cancelSortingIntent);
			break;
		case 4:
			Intent queryIntent = new Intent(this,QueryActivity.class);
			startActivity(queryIntent);
			break;
		}
	}

	@Override
	public void finish() {
		super.finish();
		JDApplication.getInstance().setLogin(false);
	}
	
}
