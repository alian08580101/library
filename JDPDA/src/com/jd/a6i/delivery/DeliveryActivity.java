package com.jd.a6i.delivery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.SealBox;
import com.jd.a6i.db.pojo.SealCar;
import com.jd.a6i.db.service.DeliveryService;
import com.jd.a6i.view.JDProgressDialog;
import com.jd.a6i.view.TextEditView;
import com.jd.a6i.volley.BaseRequest;
import com.jd.a6i.volley.SealBoxRequest;
import com.jd.a6i.volley.SealCarRequest;
import com.jd.a6i.volley.VolleyClient;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;

public class DeliveryActivity extends JDBaseActivity implements OnClickListener,OnKeyListener,DialogInterface.OnClickListener{
	public static final String TAG = "DeliveryActivity";
	/**默认对话框标记*/
	public static final int ALERT_DEFAULT = 0;
	/**校验车号对话框标记*/
	public static final int ALERT_CAR_NO = 1;
	/**校验箱号对话框标记*/
	public static final int ALERT_BOX_NO = 2;
	/**下一箱清空标记*/
	public static final int	CLEAR_BOX_NO = 3;
	/**下一车清空标记*/
	public static final int CLEAR_CAR_NO = 4;
	
	public static final int CHECK_SEAL_CAR_NO = 0;
	public static final int CHECK_SEAL_BOX_NO = 1;
	private int checkType;
	
	private EditText editSealCarNo;
	private EditText editSealBoxNo;
	private EditText editCarNo;
	private EditText editDepatureBatch;
	private EditText editTurnoverBoxNo;
	private EditText editBoxNo;
	private EditText editLogisticsDriver;
	private Button buttonNextCar;
	private Button buttonErrorSealCar;
	private Button buttonErrorSealBox;
	private TextView textBoxAndSingleton;
	
	private int boxCount = 0;
	private int packageCount = 0;
	private JDProgressDialog deliveryProgressDialog = null;
	private AlertDialog.Builder deliveryAlertDialog = null;
	private SealCar sealCar = null;
	private SealBox sealBox = null;
	private int alertType;
	
	private String currentSealCarNo = "";
	private String currentCarNo = "";
	private String currentTurnoverBoxNo = "";
	private String currentSealBoxNo = "";
	private String currentBoxNo = "";
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(getCheckType()==CHECK_SEAL_CAR_NO){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					if (msg.obj != null) {
						sealCar = (SealCar) msg.obj;
						String driverName = sealCar.getDriver();
						int driverCode = sealCar.getDriverCode();
						currentSealCarNo = sealCar.getSealCode();
						editLogisticsDriver.setText(driverCode+"-"+driverName);
					} else {
						editLogisticsDriver.setText(null);
					}
					editCarNo.requestFocus();
					break;
				case BaseRequest.REQUEST_FAILURE:
					promptManager.playSound(1, 1);
					baseToast(R.string.errorCheckSealCarNo);
					break;
				}
			}else if(getCheckType()==CHECK_SEAL_BOX_NO){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					if(msg.obj!=null){
						sealBox = (SealBox) msg.obj;
						currentSealBoxNo = sealBox.getSealCode();
					}
					editBoxNo.requestFocus();
					break;
				case BaseRequest.REQUEST_FAILURE:
					promptManager.playSound(1, 1);
					baseToast(R.string.errorCheckSealBoxNo);
					break;
				}
			}
		}
		
	};
	
	public DeliveryActivity(){
		prepareLayout(R.layout.delivery_activity);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextEditView jdSealCarNo = (TextEditView) findViewById(R.id.jdSealCarNo);
		TextEditView jdSealBoxNo = (TextEditView) findViewById(R.id.jdSealBoxNo);
		TextEditView jdCarNo = (TextEditView) findViewById(R.id.jdCarNo);
		TextEditView jdDepartureBatch = (TextEditView) findViewById(R.id.jdDepartureBatch);
		TextEditView jdTurnoverBoxNo = (TextEditView) findViewById(R.id.jdTurnoverBoxNo);
		TextEditView jdBoxNo = (TextEditView) findViewById(R.id.jdBoxNo);
		TextEditView jdLogisticsDriver = (TextEditView) findViewById(R.id.jdLogisticsDriver);
		editSealCarNo = (EditText) jdSealCarNo.findViewById(R.id.editText);
		editSealBoxNo = (EditText) jdSealBoxNo.findViewById(R.id.editText);
		editCarNo = (EditText) jdCarNo.findViewById(R.id.editText);
		editDepatureBatch = (EditText) jdDepartureBatch.findViewById(R.id.editText);
		editTurnoverBoxNo = (EditText) jdTurnoverBoxNo.findViewById(R.id.editText);
		editBoxNo = (EditText) jdBoxNo.findViewById(R.id.editText);
		editLogisticsDriver = (EditText) jdLogisticsDriver.findViewById(R.id.editText);
		TextView textBoxNo = (TextView) jdBoxNo.findViewById(R.id.textView);
		String boxNoLabel = getResources().getString(R.string.boxNo)+"<font color='red'>*</font>";
		textBoxNo.setText(Html.fromHtml(boxNoLabel));
		
		buttonNextCar = (Button) findViewById(R.id.buttonNextCar);
		buttonErrorSealCar = (Button) findViewById(R.id.buttonErrorSealCar);
		buttonErrorSealBox = (Button) findViewById(R.id.buttonErrorSealBox);
		textBoxAndSingleton = (TextView) findViewById(R.id.textBoxAndSingleton);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		editLogisticsDriver.setEnabled(false);
		editLogisticsDriver.setFocusable(false);
		editSealCarNo.setId(R.id.sealCarNoId);
		editCarNo.setId(R.id.carNoId);
		editTurnoverBoxNo.setId(R.id.turnoverBoxNoId);
		editSealBoxNo.setId(R.id.sealBoxNoId);
		editBoxNo.setId(R.id.boxNoId);

		textTitle.setText(R.string.deliveryOfBackGoodsGroup);
		editSealCarNo.setOnKeyListener(this);
		editCarNo.setOnKeyListener(this);
		editTurnoverBoxNo.setOnKeyListener(this);
		editSealBoxNo.setOnKeyListener(this);
		editBoxNo.setOnKeyListener(this);
		
		buttonNextCar.setOnClickListener(this);
		buttonErrorSealCar.setOnClickListener(this);
		buttonErrorSealBox.setOnClickListener(this);
		
		textBoxAndSingleton.setVisibility(View.VISIBLE);
		textBoxAndSingleton.setText(String.format(getResources().getString(R.string.boxAndSingletonCount, 0,0)));
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonNextCar:
			clear(CLEAR_CAR_NO);
			break;
		case R.id.buttonErrorSealCar:
			errorSealCarOrBoxAction("sealCarError");
			break;
		case R.id.buttonErrorSealBox:
			errorSealCarOrBoxAction("sealBoxError");
			break;
		}
	}
	
	protected void errorSealBoxAction(){
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_ENTER){
			if(event.getAction()==KeyEvent.ACTION_UP){
				switch (v.getId()) {
				case R.id.sealCarNoId:
					String sealCarNo = editSealCarNo.getText().toString();
					return checkSealCarNo(sealCarNo, event);
				case R.id.carNoId:
					String carNo = editCarNo.getText().toString();
					return checkCarNo(carNo, event);
				case R.id.turnoverBoxNoId:
					String turnoverBoxNo = editTurnoverBoxNo.getText().toString();
					return checkTurnoverBoxNo(turnoverBoxNo, event);
				case R.id.sealBoxNoId:
					String sealBoxNo = editSealBoxNo.getText().toString();
					return checkSealBoxNo(sealBoxNo, event);
				case R.id.boxNoId:
					String boxNo = editBoxNo.getText().toString();
					return checkBoxNo(boxNo, event);
				}
			}
		}
		return false;
	}
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(getAlertType()==ALERT_CAR_NO){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				currentCarNo = getEditTextContent(editCarNo);
				editTurnoverBoxNo.requestFocus();
				break;
			case DialogInterface.BUTTON_POSITIVE:
				currentCarNo = getEditTextContent(editCarNo);
				break;
			}
		}else if(getAlertType()==ALERT_BOX_NO){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				if(saveDelivery()){
					currentBoxNo = getEditTextContent(editBoxNo);
					clear(CLEAR_BOX_NO);
				}
				break;
			case DialogInterface.BUTTON_POSITIVE:
				currentBoxNo = getEditTextContent(editBoxNo);
				break;
			}
		}
		setAlertType(ALERT_DEFAULT);
		dialog.dismiss();
	}
	
	/**
	 * 取消进度条显示
	 */
	public void cancelProgress(){
		if(deliveryProgressDialog!=null&&deliveryProgressDialog.isShowing()){
			deliveryProgressDialog.dismiss();
			deliveryProgressDialog = null;
		}
	}
	
	/**
	 * 封车号校验
	 * @param sealCarNo
	 * @param event
	 * @return
	 */
	protected boolean checkSealCarNo(String sealCarNo,KeyEvent event){
		if(sealCarNo!=null&&!"".equals(sealCarNo)){
			if(!Confirmation.isLegalSealCarNo(sealCarNo, ConstantUtils.SEAL_CAR_NO_REGULAR_EXPRESSION)){
				promptManager.playSound(1, 1);
				baseToast(R.string.errorSealCarNo);
				return true;
			}else{
				if (event.getAction()==KeyEvent.ACTION_UP) {
					setCheckType(CHECK_SEAL_CAR_NO);
					Map<String,Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("sealCarCode", sealCarNo.toUpperCase());
					SealCarRequest sealCarRequest = new SealCarRequest(Method.GET,handler, paramsMap);
					VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
					volleyClient.addRequest(sealCarRequest);
					volleyClient.startRequest();
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 车号校验
	 * @param carNo
	 * @param event
	 * @return
	 */
	protected boolean checkCarNo(String carNo,KeyEvent event){
		if (carNo != null && !"".equals(carNo)) {
			if(!Confirmation.isLegalCarNo(carNo, ConstantUtils.CAR_NO_REGULAR_EXPRESSION)){
				promptManager.playSound(1, 1);
				baseToast(R.string.errorCarNo);
				return true;
			}else{
				if(sealCar!=null){
					if(!sealCar.getVehicleCode().equals(carNo)){
						if (event.getAction()==KeyEvent.ACTION_UP) {
							if (deliveryAlertDialog == null) {
								deliveryAlertDialog = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);
							}
							View messageView = messageView(R.string.matchSealCarNoWithCarNoPrompt);
							setAlertType(ALERT_CAR_NO);
							deliveryAlertDialog.setIcon(R.drawable.ico_question);
							deliveryAlertDialog.setTitle(R.string.prompt);
							deliveryAlertDialog.setView(messageView);
							deliveryAlertDialog.setNegativeButton(R.string.yes, this);
							deliveryAlertDialog.setPositiveButton(R.string.no, this);
							deliveryAlertDialog.show();
						}
						return true;
					}
				}
				currentCarNo = carNo;
				return false;
			}
		}
		return false;
	}
	/**
	 * 周转箱号校验
	 * @param turnoverBoxNo
	 * @param event
	 * @return
	 */
	protected boolean checkTurnoverBoxNo(String turnoverBoxNo,KeyEvent event){
		if("".equals(turnoverBoxNo)||null==turnoverBoxNo){
			return false;
		}else{
			if(Confirmation.isLegalTurnoverBoxNo(turnoverBoxNo, ConstantUtils.TURNOVER_BOX_NO_EXPRESSION)){
				currentTurnoverBoxNo = turnoverBoxNo;
				return false;
			}
			promptManager.playSound(1, 1);
			baseToast(R.string.errorTurnoverBoxNo);
			return true;
		}
	}
	/**
	 * 封箱号校验
	 * @param sealBoxNo
	 * @param event
	 * @return
	 */
	protected boolean checkSealBoxNo(String sealBoxNo,KeyEvent event){
		if(sealBoxNo!=null&&!"".equals(sealBoxNo)){
			if(!Confirmation.isLegalExpression(sealBoxNo, ConstantUtils.SEAL_BOX_NO_REGULAR_EXPRESSION)){
				promptManager.playSound(1, 1);
				baseToast(R.string.errorSealBoxNo);
				return true;
			}else{
				if (event.getAction()==KeyEvent.ACTION_UP) {
					setCheckType(CHECK_SEAL_BOX_NO);
					Map<String,Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("sealBoxCode", sealBoxNo.toUpperCase());
					SealBoxRequest sealCarRequest = new SealBoxRequest(Method.GET, handler, paramsMap);
					VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
					volleyClient.addRequest(sealCarRequest);
					volleyClient.startRequest();
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 校验箱号
	 * @param boxNo
	 * @param event
	 * @return
	 */
	protected boolean checkBoxNo(String boxNo,KeyEvent event){
		if (boxNo != null && !"".equals(boxNo)) {
			if(!Confirmation.isReceiveBoxNo(boxNo)){
				promptManager.playSound(1, 1);
				baseToast(R.string.errorBoxNo);
				return true;
			} else {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					currentBoxNo = boxNo;
					DeliveryService deliveryService = DeliveryService.getInstance(this);
					Delivery delivery = deliveryService.findUniqueDelivery(currentBoxNo);
					if(delivery!=null){
						promptManager.playSound(1, 1);
						baseToast(R.string.deliveryCheckPrompt,currentBoxNo);
						return true;
					}
					if (sealBox != null) {
						if (!boxNo.equals(sealBox.getBoxCode())) {
							if (deliveryAlertDialog == null) {
								deliveryAlertDialog = new Builder(this,AlertDialog.THEME_HOLO_LIGHT);
							}
							View messageView = messageView(R.string.matchSealCarNoWithBoxNoPrompt);
							setAlertType(ALERT_BOX_NO);
							deliveryAlertDialog.setIcon(R.drawable.ico_question);
							deliveryAlertDialog.setTitle(R.string.prompt);
							deliveryAlertDialog.setView(messageView);
							deliveryAlertDialog.setNegativeButton(R.string.yes,this);
							deliveryAlertDialog.setPositiveButton(R.string.no,this);
							deliveryAlertDialog.show();
						}else{
							if(saveDelivery()){
								clear(CLEAR_BOX_NO);
							}
						}
						return true;
					}
					if(saveDelivery()){
						clear(CLEAR_BOX_NO);
					}
					return true;
				}
				return false;
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.checkBoxNoPrompt);
			return true;
		}
	}
	/**
	 * 保存箱
	 * @param packOrBox
	 * @return
	 */
	protected boolean saveDelivery(){
		Account account = JDApplication.getInstance().getAccount();
		Delivery delivery = new Delivery();
		delivery.setShieldsCarCode(currentSealCarNo);
		delivery.setCarCode(currentCarNo);
		delivery.setSealBoxCode(currentSealBoxNo);
		delivery.setPackOrBox(currentBoxNo);
		delivery.setTurnoverBoxCode(currentTurnoverBoxNo);
		delivery.setQueueNo("");
		delivery.setDepartureCarId(getEditTextContent(editDepatureBatch));
		delivery.setShieldsCarTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		delivery.setBusinessType(20);
		delivery.setUploadStatus(ConstantUtils.UPLOAD_0);
		delivery.setUserCode(account!=null?account.getStaffId():0);
		delivery.setUserName(account!=null?account.getStaffName():"");
		delivery.setSiteCode(account!=null?account.getSiteCode():0);
		delivery.setSiteName(account!=null?account.getSiteName():"");
		delivery.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		DeliveryService deliveryService = DeliveryService.getInstance(this);
		try {
			if(deliveryService.save(delivery)==1){
				showBoxAndSingleton();
				return true;
			}
			return false;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected void showBoxAndSingleton(){
		if(Confirmation.isBoxCode(currentBoxNo)){
			boxCount++;
		}else{
			packageCount++;
		}
		textBoxAndSingleton.setText(getResources().getString(R.string.boxAndSingletonCount, boxCount,packageCount));
	}
	
	protected String getEditTextContent(EditText editText){
		if(editText.getText()==null){
			return "";
		}
		return editText.getText().toString();
	}
	
	protected void clear(int clearType){
		switch(clearType){
		case CLEAR_BOX_NO:
			editTurnoverBoxNo.requestFocus();
			nextBox();
			break;
		case CLEAR_CAR_NO:
			editSealCarNo.requestFocus();
			nextCar();
			break;
		}
	}
	
	protected void nextBox(){
		editTurnoverBoxNo.setText(null);
		editSealBoxNo.setText(null);
		editBoxNo.setText(null);
	}
	
	protected void nextCar(){
		boxCount=0;
		packageCount=0;
		textBoxAndSingleton.setText(getResources().getString(R.string.boxAndSingletonCount, boxCount,packageCount));
		nextBox();
		editSealCarNo.setText(null);
		editCarNo.setText(null);
		editLogisticsDriver.setText(null);
	}
	
	protected void errorSealCarOrBoxAction(String sealType){
		if("sealCarError".equals(sealType)){
			if(currentSealCarNo==null||"".equals(currentSealCarNo)){
				editSealCarNo.requestFocus();
				promptManager.playSound(1, 1);
				baseToast(R.string.errorSealCarNo);
				return;
			}else if(currentCarNo==null||"".equals(currentCarNo)){
				editCarNo.requestFocus();
				promptManager.playSound(1, 1);
				baseToast(R.string.errorCarNo);
				return;
			}
		}else if("sealBoxError".equals(sealType)){
			if(currentSealBoxNo==null||"".equals(currentSealBoxNo)){
				editSealBoxNo.requestFocus();
				promptManager.playSound(1, 1);
				baseToast(R.string.errorSealBoxNo);
				return;
			}else if(currentBoxNo==null||"".equals(currentBoxNo)){
				editBoxNo.requestFocus();
				promptManager.playSound(1, 1);
				baseToast(R.string.errorBoxNo);
				return;
			}
		}
		errorSealCarOrBoxIntent(sealType);
	}
	
	protected void errorSealCarOrBoxIntent(String sealType){
		Intent sealCarOrBoxNoErrorIntent = new Intent(this,SealCarOrBoxErrorActivity.class);
		sealCarOrBoxNoErrorIntent.putExtra("sealType", sealType);
		if("sealCarError".equals(sealType)){
			sealCarOrBoxNoErrorIntent.putExtra("sealCarNo", currentSealCarNo);
			sealCarOrBoxNoErrorIntent.putExtra("carNo", currentCarNo);
		}else if("sealBoxError".equals(sealType)){
			sealCarOrBoxNoErrorIntent.putExtra("sealBoxNo", currentSealBoxNo);
			sealCarOrBoxNoErrorIntent.putExtra("boxNo", currentBoxNo);
		}
		startActivity(sealCarOrBoxNoErrorIntent);
	}
	
	public int getAlertType() {
		return alertType;
	}

	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}
	
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
}
