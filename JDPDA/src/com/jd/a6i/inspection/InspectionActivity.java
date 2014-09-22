package com.jd.a6i.inspection;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.db.pojo.MenuItem;

public class InspectionActivity extends JDBaseActivity implements OnItemClickListener {

	private static final String TAG = "InspectionActivity";
	
	public InspectionActivity()
	{
		prepareLayout(R.layout.menu_body);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.inspection_activity);
		menuLayout(initMenu());
	}

	protected List<MenuItem> initMenu(){
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		MenuItem deliveryItem = new MenuItem(); 
		deliveryItem.setItemName(resources.getString(R.string.self_return_inspection));
		deliveryItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		deliveryItem.setItemIcon(resources.getDrawable(R.drawable.jd_delivery_48));
		
		MenuItem inspectionItem = new MenuItem();
		inspectionItem.setItemName(resources.getString(R.string.the_three_party_to_return));
		inspectionItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		inspectionItem.setItemIcon(resources.getDrawable(R.drawable.jd_inspection_48));
		
		MenuItem sortingTallyItem = new MenuItem();
		sortingTallyItem.setItemName(resources.getString(R.string.sorting_center_return_inspection));
		sortingTallyItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		sortingTallyItem.setItemIcon(resources.getDrawable(R.drawable.jd_sorting_tally_48));

		menuItemList.add(deliveryItem);
		menuItemList.add(inspectionItem);
		menuItemList.add(sortingTallyItem);
		
		return menuItemList;
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		textTitle.setText(getResources().getString(R.string.returnGroupInpection));
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
		menuList.setAdapter(menuItemAdapter);
		menuList.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspection, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch(arg2){
		case 0:
			Intent inspectionOperateIntent = new Intent();
			inspectionOperateIntent.putExtra("type", 0);
			inspectionOperateIntent.setClass(InspectionActivity.this, InspectionOperateActivity.class);
			startActivity(inspectionOperateIntent);
			break;
		case 1:
			Intent inspectionOperateIntent1 = new Intent();
			inspectionOperateIntent1.putExtra("type", 1);
			inspectionOperateIntent1.setClass(InspectionActivity.this, InspectionOperateActivity.class);
			startActivity(inspectionOperateIntent1);
			break;
		case 2:
			Intent inspectionOperateIntent2 = new Intent();
			inspectionOperateIntent2.putExtra("type", 2);
			inspectionOperateIntent2.setClass(InspectionActivity.this, InspectionOperateActivity.class);
			startActivity(inspectionOperateIntent2);
			break;
		default:
			break;
				
		}
	}

}
