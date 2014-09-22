package com.jd.a6i.query;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.db.pojo.MenuItem;

public class QueryActivity extends JDBaseActivity implements OnItemClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuLayout(initMenu());
	}
	
	public QueryActivity() {
		prepareLayout(R.layout.menu_body);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText(getResources().getString(R.string.dataQuery));
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
		menuList.setAdapter(menuItemAdapter);
		menuList.setOnItemClickListener(this);
	}
	
	protected List<MenuItem> initMenu(){
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		MenuItem packageBoxNoItem = new MenuItem(); 
		packageBoxNoItem.setItemName(resources.getString(R.string.packageBoxNoQuery));
		packageBoxNoItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		packageBoxNoItem.setItemIcon(resources.getDrawable(R.drawable.jd_bill_query_48));
		
		MenuItem packagePartItem = new MenuItem();
		packagePartItem.setItemName(resources.getString(R.string.packagePartQuery));
		packagePartItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		packagePartItem.setItemIcon(resources.getDrawable(R.drawable.jd_package_part_query_48));
		
		MenuItem packageItem = new MenuItem();
		packageItem.setItemName(resources.getString(R.string.packageQuery));
		packageItem.setItemArrow(resources.getDrawable(R.drawable.png_navigation_right));
		packageItem.setItemIcon(resources.getDrawable(R.drawable.jd_package_query_48));
		
		menuItemList.add(packageBoxNoItem);
		menuItemList.add(packagePartItem);
		menuItemList.add(packageItem);
		return menuItemList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			Intent queryPackageBoxIntent = new Intent(this,QueryPackageBoxActivity.class);
			queryPackageBoxIntent.putExtra("DeliveryCategory", 20);
			startActivity(queryPackageBoxIntent);
			break;
		case 1:
			Intent queryPackagePartIntent = new Intent(this,QueryPackagePartActivity.class);
			startActivity(queryPackagePartIntent);
			break;
		case 2:
			Intent queryPackageIntent = new Intent(this,QueryPackageActivity.class);
			startActivity(queryPackageIntent);
			break;
		default:
			break;
		}
	}
}
