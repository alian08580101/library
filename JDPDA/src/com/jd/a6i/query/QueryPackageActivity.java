package com.jd.a6i.query;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.PackageQuery;
import com.jd.a6i.db.service.SortingTallyService;
import com.jd.a6i.task.QueryPackageAsyncTask;
import com.jd.a6i.view.TextEditView;

public class QueryPackageActivity extends JDBaseActivity {
	private TextEditView boxcodeTev = null;
	private EditText boxcodeEt = null;
	private TextView messageShow = null;
	private PackageQuery packageQuery = null;
	private StringBuilder boxMess = null;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case QueryPackageAsyncTask.QUERYPACKAGE_ERROR:
				promptManager.playSound(1, 1);
//				if(msg.obj!=null&&!msg.obj.toString().equals("")){
//					Toast.makeText(QueryPackageActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
//				}
//				else{
//					messagebox(R.string.networkError);
//				}
				messageShow.setText(String.format(getResources().getString(R.string.noBox), boxcodeEt.getText().toString()));
				boxcodeEt.requestFocus(); 
				break;
			case QueryPackageAsyncTask.QUERYPACKAGE_SUCCESS:
				boxMess=new StringBuilder();
				packageQuery = JsonConverter.convertJsonToObject(msg.obj.toString(), TypeToken.get(PackageQuery.class));
				if(packageQuery.getReceiveSiteCode()==0&&(packageQuery.getReceiveSiteName()==null||packageQuery.getReceiveSiteName().equals(""))){
					Toast.makeText(QueryPackageActivity.this, String.format(getResources().getString(R.string.noBox), boxcodeEt.getText().toString()), Toast.LENGTH_LONG).show();
					return;
				}
				List<Map<String,Object>> items = SortingTallyService.getInstance(getApplicationContext()).findCommitedPackNo();
				int unCommited = 0;
				boxMess=new StringBuilder(String.format(getResources().getString(R.string.boxMess), boxcodeEt.getText().toString()));
				if(items!=null&&items.size()>0){
					for(Map<String,Object> item:items){
						if(item.get("uploadStatus").equals("0")){
							unCommited +=Integer.parseInt(item.get("packNum").toString());
						}
					}
					boxMess.append(String.format(getResources().getString(R.string.updataedNum), packageQuery.getTotalPack(),unCommited));
					boxMess.append(String.format(getResources().getString(R.string.totalBoxNum), packageQuery.getTotalPack()+unCommited));
					boxMess.append(String.format(getResources().getString(R.string.siteBoxMess), items.get(0).get("siteName"),items.get(0).get("siteCode")));
				}
				else{
					boxMess.append(String.format(getResources().getString(R.string.packageNum), packageQuery.getTotalPack()));
					boxMess.append(String.format(getResources().getString(R.string.siteBoxMess), packageQuery.getReceiveSiteName(),packageQuery.getReceiveSiteCode()));
				}
				boxcodeEt.requestFocus();
				messageShow.setText(boxMess.toString());
				break;
			
			}
		}
	};
	
	public QueryPackageActivity(){
		prepareLayout(R.layout.query_package_activity);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		boxcodeTev = (TextEditView)findViewById(R.id.queryPackageBoxcode);
		boxcodeEt = (EditText)findViewById(R.id.editText);
		boxcodeEt.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					if(event.getAction() == KeyEvent.ACTION_DOWN){
						return boxcodeKeyDown();
					}
				}
				return false;
			}
		});
		messageShow = (TextView)findViewById(R.id.queryPackageMessage);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText(getResources().getString(R.string.packageQuery));
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
	}

	private void messagebox(int id){
		Toast.makeText(QueryPackageActivity.this, getResources().getText(id), Toast.LENGTH_LONG).show();
	}
	
	private boolean boxcodeKeyDown(){
		if(boxcodeEt.getText().toString().equals("")){
			//messagebox(R.string.checkBoxNoPrompt);
			messageShow.setText(getResources().getString(R.string.checkBoxNoPrompt));
			promptManager.playSound(1, 1);
			boxcodeEt.requestFocus();
			return false;
		}
		else{
//			if(!Confirmation.isBoxCode(boxcodeEt.getText().toString())){
//				messageShow.setText(getResources().getString(R.string.errorBoxNo));
//				promptManager.playSound(1, 1);
//				boxcodeEt.requestFocus();
//				return false;
//			}
			QueryPackageAsyncTask queryPackageAsyncTask = new QueryPackageAsyncTask(getApplicationContext(),mHandler);
			queryPackageAsyncTask.execute(String.valueOf(JDApplication.getInstance().getAccount().getSiteCode()),boxcodeEt.getText().toString());
		}
		return true;
	}
}
