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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.adapter.ListViewAdapter;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.IncompleteQueryRequest;
import com.jd.a6i.db.pojo.IncompleteQueryResponse;
import com.jd.a6i.db.pojo.IncompleteQueryResponseData;
import com.jd.a6i.db.pojo.StationInfo;
import com.jd.a6i.task.QuerypartAsynctask;
import com.jd.a6i.task.ScanSitecodeAsynTask;
import com.jd.a6i.view.TextEditView;

public class QueryPackagePartActivity extends JDBaseActivity {
	private String receiveSiteNo = null;
	private String boxNo = null;
	private TextEditView sitecodeTev = null;
	private TextEditView boxcodeTev = null;
	private EditText sitecodeEt = null;
	private EditText boxcodeEt = null;
	private IncompleteQueryResponseData[] incompleteQueryResponseData = null;
	private RadioButton scannedRB= null;
	private RadioButton unScannedRB= null;
	private ListView queryPartlistview = null;
	private Button queryButton = null;
	
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch(msg.what){
			case ScanSitecodeAsynTask.SCANSITECODE_ERROR:
				promptManager.playSound(1, 1);
				receiveSiteNo = ""; 
				sitecodeEt.setSelected(true);
				sitecodeEt.requestFocus();
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(QueryPackagePartActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				break;
			case ScanSitecodeAsynTask.SCANSITECODE_SUCCESS:
				ConstantUtils.scanSiteCode.add((StationInfo)msg.obj);
				receiveSiteNo = String.valueOf(((StationInfo)msg.obj).getSiteCode());
				if(!boxcodeEt.getText().toString().equals("")){
					boxNo = boxcodeEt.getText().toString();
				}
				scannedRB.setChecked(false);
				unScannedRB.setChecked(false);
				dtInit(receiveSiteNo,boxNo);
				receiveSiteNo= "";
				boxNo = "";
				
				break;
			case QuerypartAsynctask.QUERYPART_ERROR:
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(QueryPackagePartActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				promptManager.playSound(1, 1);
				break;
			case QuerypartAsynctask.QUERYPART_SUCCESS:
				IncompleteQueryResponse incompleteQueryResponse = JsonConverter.convertJsonToObject(msg.obj.toString(), TypeToken.get(IncompleteQueryResponse.class));
				incompleteQueryResponseData = incompleteQueryResponse.getData();
				List<Map<String,Object>> items =null;
				if(scannedRB.isChecked()){
					items = getlist(incompleteQueryResponseData,getResources().getString(R.string.scaned));
				}
				else if(unScannedRB.isChecked()){
					items = getlist(incompleteQueryResponseData,getResources().getString(R.string.unscaned));
				}
				else{
					items = getlist(incompleteQueryResponseData,null);
				}
				
				if(items!=null){
					ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,1);
					queryPartlistview.setAdapter(listViewAdapter);
				}
				else{
					promptManager.playSound(1, 1);
					messagebox(R.string.querynodata);
				}
				
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		receiveSiteNo = intent.getStringExtra("receiveSiteNo");
		boxNo = intent.getStringExtra("boxNo");
		
		sitecodeTev = (TextEditView)findViewById(R.id.jdStation);
		boxcodeTev = (TextEditView)findViewById(R.id.jdBoxNo);
		
		sitecodeEt = (EditText)sitecodeTev.findViewById(R.id.editText);
		boxcodeEt = (EditText)boxcodeTev.findViewById(R.id.editText);
		scannedRB = (RadioButton)findViewById(R.id.radioScaned);
		unScannedRB = (RadioButton)findViewById(R.id.radioUnscaned);
		queryPartlistview = (ListView)findViewById(R.id.listDifferenceQuery);
		queryButton = (Button)findViewById(R.id.buttonQuery);
		
		if(receiveSiteNo!=null){
			sitecodeEt.setText(receiveSiteNo);
		}
		if(boxNo!=null){
			boxcodeEt.setText(boxNo);
		}
		
		
		scannedRB.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					List<Map<String,Object>> items =null;
					items = getlist(incompleteQueryResponseData,getResources().getString(R.string.scaned));
					if(items!=null){
						ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,1);
						queryPartlistview.setAdapter(listViewAdapter);
					}
				}
			}
			
		});
		
		unScannedRB.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					List<Map<String,Object>> items =null;
					items = getlist(incompleteQueryResponseData,getResources().getString(R.string.unscaned));
					if(items!=null){
						ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,1);
						queryPartlistview.setAdapter(listViewAdapter);
					}
				}
			}
			
		});
		
		queryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				queryButtonClick();
			}
		});
		
		if((receiveSiteNo!=null&&!receiveSiteNo.equals(""))||(boxNo!=null&&!boxNo.equals(""))){
			dtInit(receiveSiteNo,boxNo);
		}
		sitecodeEt.requestFocus();
		
//		List<Map<String,Object>> items =new ArrayList<Map<String,Object>>();
//		for(int i = 0;i<3;i++){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("index", i);
//			map.put("boxcode", "721846878");
//			map.put("packageBarcode", "92368309");
//			map.put("mark", "“—…®√Ë");
//			items.add(map);
//		}
//		if(items!=null){
//			ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),items,1);
//			queryPartlistview.setAdapter(listViewAdapter);
//		}
		
	}
	
	public QueryPackagePartActivity(){
		prepareLayout(R.layout.query_package_part_common);
	}
	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText(getResources().getString(R.string.querypart));
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
	}

	private void dtInit(String receiveSiteNo, String boxNo) {
        if((receiveSiteNo==null||receiveSiteNo.equals(""))&&(boxNo==null||boxNo.equals(""))){
			return;
		}
        QuerypartAsynctask querypartAsynctask = new QuerypartAsynctask(getApplicationContext(),mHandler);
        IncompleteQueryRequest incompleteQueryRequest = new IncompleteQueryRequest();
        incompleteQueryRequest.setSiteCode(String.valueOf(JDApplication.getInstance().getAccount().getSiteCode()));
        incompleteQueryRequest.setReceiveSiteCode(receiveSiteNo);
        incompleteQueryRequest.setBoxCode(boxNo);
        querypartAsynctask.execute(JsonConverter.convertObjectToJson(incompleteQueryRequest));
	}
	
	private void messagebox(int id){
		Toast.makeText(QueryPackagePartActivity.this, getResources().getText(id), Toast.LENGTH_LONG).show();
	}
	
	private List<Map<String,Object>> getlist(IncompleteQueryResponseData[] incompleteQueryResponseData,String selection){
		if(incompleteQueryResponseData == null||incompleteQueryResponseData.length<=0){
			return null;
		}
		else{
			List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
			int i=0;
			for(IncompleteQueryResponseData item:incompleteQueryResponseData){
				Map<String,Object> map = new HashMap<String,Object>();
				
				if(selection!=null&&!selection.equals("")){
					if(selection.equals(item.getMark())){
						map.put("index", i++);
						map.put("boxcode", item.getBoxCode());
						map.put("packageBarcode", item.getPackageBarcode());
						map.put("mark", item.getMark());
						items.add(map);
					}
				}
				else{
					map.put("index", i++);
					map.put("boxcode", item.getBoxCode());
					map.put("packageBarcode", item.getPackageBarcode());
					map.put("mark", item.getMark());
					items.add(map);
				}
			}
			return items;
		}

	}
	
	private void queryButtonClick(){
		boolean isCache = false;
		if(sitecodeEt.getText().toString().equals("")&&boxcodeEt.getText().toString().equals("")){
			promptManager.playSound(1, 1);
			messagebox(R.string.queryPartNoSite);
			sitecodeEt.requestFocus();
			return;
		}
		if(!sitecodeEt.getText().toString().equals("")){
			if(ConstantUtils.scanSiteCode.size()>0){
				for(StationInfo item:ConstantUtils.scanSiteCode){
					if(item.getDmsCode().equals(sitecodeEt.getText().toString())||sitecodeEt.getText().toString().equals(String.valueOf(item.getSiteCode()))){
						receiveSiteNo = String.valueOf(item.getSiteCode());
						isCache = true;
						break;
					}
				}
			}
			if(!isCache){
				ScanSitecodeAsynTask scanSitecodeAsynTask = new ScanSitecodeAsynTask(getApplicationContext(),mHandler);
				scanSitecodeAsynTask.execute(sitecodeEt.getText().toString());
				return;
			}
			
		}
		else{
			receiveSiteNo = "";
		}
			
		if(!boxcodeEt.getText().toString().equals("")){
			boxNo = boxcodeEt.getText().toString();
		}
		else{
			boxNo = "";
		}
		scannedRB.setChecked(false);
		unScannedRB.setChecked(false);
		dtInit(receiveSiteNo,boxNo);
		receiveSiteNo= "";
		boxNo = "";
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_F1){
			queryButtonClick();
			return true;
		}
			
		return super.onKeyDown(keyCode, event);
	}
}
