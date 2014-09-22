package com.jd.a6i.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.adapter.ListViewAdapter;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.BoxQuery;
import com.jd.a6i.db.pojo.BoxQueryData;
import com.jd.a6i.task.QueryPackageBoxAsyncTask;
import com.jd.a6i.view.TextEditView;

public class QueryPackageBoxActivity extends JDBaseActivity {
	//正向投递
	private static final int DELIVERYCATEGORYDIRECT = 10;
	//逆向投递
	private static final int DELIVERYCATEGORYRESERVE = 20;
	//三方投递
	private static final int DELIVERYCATEGORYTHIRD = 30;
	
	private TextView propmtTv = null;
	private int deliveryCategory = 0;
	private TextEditView waybillEditview = null;
	private EditText waybill= null;
	private BoxQueryData[] boxQueryData = null;
	private ListView listView = null;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch(msg.what){
			case QueryPackageBoxAsyncTask.QUERYPACKAGEBOX_ERROR:
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(QueryPackageBoxActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.querynodata);
				}
				promptManager.playSound(1, 1);
				break;
			case QueryPackageBoxAsyncTask.QUERYPACKAGEBOX_SUCCESS:
				BoxQuery boxQuery = JsonConverter.convertJsonToObject(msg.obj.toString(), TypeToken.get(BoxQuery.class));
				boxQueryData = boxQuery.getBoxPackList();
				List<Map<String,Object>> items = getlist(boxQueryData);
				if(items!=null){
					ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,0);
					listView.setAdapter(listViewAdapter);
				}
				else{
					promptManager.playSound(1, 1);
					messagebox(R.string.querynodata);
				}
				
				break;
			}
		}
	};
	
	private void messagebox(int id){
		Toast.makeText(QueryPackageBoxActivity.this, getResources().getText(id), Toast.LENGTH_LONG).show();
	}
	public QueryPackageBoxActivity(){
		prepareLayout(R.layout.query_package_box_activity);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		propmtTv = (TextView)findViewById(R.id.propmt);
        propmtTv.setText(getResources().getString(R.string.queryPropmt));
        
        waybillEditview = (TextEditView)findViewById(R.id.jdPackageBoxNo);
        waybill = (EditText)waybillEditview.findViewById(R.id.editText);
        waybill.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					if(event.getAction() == KeyEvent.ACTION_DOWN){
						if(!waybill.getText().toString().equals("")){
							if (Confirmation.isPackSortNumber(waybill.getText().toString())) {
								keyEnter();
							}
							else{
								messagebox(R.string.waybillNoError);
								promptManager.playSound(1, 1);
							}
							
						}
						else
						{
							messagebox(R.string.noWaybillNo);
							promptManager.playSound(1, 1);
						}
						return true;
					}
				}
				return false;
			}
		});
        listView = (ListView)findViewById(R.id.listQuery);
       
        
//        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
//        for(int i = 0;i<5;i++){
//        	Map<String,Object> map = new HashMap<String, Object>();
//        	map.put("boxNo", "TC010F481010F51100000001");
//        	map.put("waybillNo", "TC010F481010F51100");
//        	items.add(map);
//        }
//		if(items!=null){
//			ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,0);
//			listView.setAdapter(listViewAdapter);
//		}
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		deliveryCategory = intent.getIntExtra("DeliveryCategory", 0);
		switch(deliveryCategory){
		case DELIVERYCATEGORYDIRECT:
			textTitle.setText(getResources().getString(R.string.packBoxQueryDirect));
			break;
		case DELIVERYCATEGORYRESERVE:
			textTitle.setText(getResources().getString(R.string.packBoxQueryReserve));
			break;
		case DELIVERYCATEGORYTHIRD:
			textTitle.setText(getResources().getString(R.string.packBoxQueryThird));
			break;
		}
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
	}
	
	/**
	 * 相应Enter键
	 */
	private void keyEnter(){
		QueryPackageBoxAsyncTask queryPackageBoxAsyncTask = new QueryPackageBoxAsyncTask(getApplicationContext(),mHandler);
		queryPackageBoxAsyncTask.execute(String.valueOf(JDApplication.getInstance().getAccount().getSiteCode()),waybill.getText().toString(),String.valueOf(deliveryCategory));
	}
	
	private List<Map<String,Object>> getlist(BoxQueryData[] boxQueryData){
		if(boxQueryData!=null && boxQueryData.length>0){
			List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();

			for(BoxQueryData item:boxQueryData){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("boxNo", item.getBoxCode());
				map.put("waybillNo", item.getWaybillCode());
				items.add(map);
			}
			return items;
		}
		return null;
	}
}
