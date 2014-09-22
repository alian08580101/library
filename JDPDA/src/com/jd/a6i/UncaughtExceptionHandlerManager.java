package com.jd.a6i;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jd.a6i.common.SharedPreferencesUtils;
import com.whl.utils.SimpleDateUtils;

public class UncaughtExceptionHandlerManager implements UncaughtExceptionHandler {
	public static final String TAG = "UncaughtExceptionHandlerManager";
	public static final String CRASH_SAVE_DIRECTORY = "/sdcard/crash/";
	public static final String EXTENSION_NAME = ".log";

	// ϵͳĬ�ϵ�UncaughtException������
	private Thread.UncaughtExceptionHandler unCaughtExceptionHandler;
	// CrashHandlerʵ��
	private static UncaughtExceptionHandlerManager INSTANCE = new UncaughtExceptionHandlerManager();
	// �����Context����
	private Context context;
	// �����洢�豸��Ϣ���쳣��Ϣ
	private Map<String, String> deviceMap = new HashMap<String, String>();

	// ���ڸ�ʽ������,��Ϊ��־�ļ�����һ����
	private static DateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private UncaughtExceptionHandlerManager() {
	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static UncaughtExceptionHandlerManager getInstance() {
		return INSTANCE;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	public void init(Context context) {
		this.context = context;
		// ��ȡϵͳĬ�ϵ�UncaughtException������
		unCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * ��UncaughtException����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		JDApplication.getInstance().setLogin(false);
		if (!handleException(ex) && unCaughtExceptionHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
			unCaughtExceptionHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			// �˳�����
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 * 
	 * @param ex
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// ʹ��Toast����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				//deletePastFile(CRASH_SAVE_DIRECTORY);
				//deleteFile();
				Looper.prepare();
				Toast.makeText(context, "�ܱ�Ǹ,��������쳣,�����˳�.", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// �ռ��豸������Ϣ
		//collectDeviceInfo(context);
		// ������־�ļ�
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * �ռ��豸������Ϣ
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				deviceMap.put("versionName", versionName);
				deviceMap.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				deviceMap.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * ���������Ϣ���ļ���
	 * @param throwable
	 * @return �����ļ�����,���ڽ��ļ����͵�������
	 */
	private void saveCrashInfo2File(Throwable throwable) {

		StringBuffer stringBuffer = new StringBuffer();
		if(deviceMap.size()>0){
			for (Map.Entry<String, String> entry : deviceMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				stringBuffer.append(key + "=" + value + "\n");
			}
		}
		stringBuffer.append("Error Start Time------"+SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN)+ "\n");
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		Throwable cause = throwable.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		
		String result = writer.toString();
		stringBuffer.append(result);
		
		try {
			//long timestamp = System.currentTimeMillis();
			String dateString = logDateFormat.format(new Date());
			String fileName = "jd-" + dateString + EXTENSION_NAME;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File fileDirectory = new File(CRASH_SAVE_DIRECTORY);
				if (!fileDirectory.exists()) {
					fileDirectory.mkdirs();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(CRASH_SAVE_DIRECTORY + fileName,true);
				fileOutputStream.write(stringBuffer.toString().getBytes());
				fileOutputStream.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		Log.e(TAG, stringBuffer.toString());
	}
	
	/**
	 * �õ�����ʱ��ǰ�ļ������ڣ������õ���Ҫɾ������־�ļ���
	 * */
	private synchronized  Date getDateBefore() {
		Date nowDate = new Date();
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(nowDate);
		nowCalendar.set(Calendar.DATE, nowCalendar.get(Calendar.DATE) - 1);
		return nowCalendar.getTime();
	}
	
	/**
	 * ɾ���ƶ�����־�ļ�
	 * */
	public synchronized  void deleteFile() {
		String needDeleteFile = logDateFormat.format(getDateBefore());
		String fileName = "jd-" + needDeleteFile + EXTENSION_NAME;
		File file = new File(CRASH_SAVE_DIRECTORY,fileName);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static synchronized void deletePastFile(String directory){
		File fileDirectory = new File(directory);
		File[] fileArray = fileDirectory.listFiles();
		if(fileArray==null||fileArray.length==0){
			return;
		}else{
			for (File file : fileArray) {
				if(file.isDirectory()){
					deletePastFile(file.getAbsolutePath());
				}else{
					if(file.isFile()&&file.exists()){
						String fileName = file.getName();
						String fileDate = fileName.substring(3, fileName.length()-4);
						Date pastDate = SimpleDateUtils.stringToDate(fileDate, SimpleDateUtils.DATE_PATTERN);
						Date nowDate = SimpleDateUtils.stringToDate(SimpleDateUtils.currentDate(), SimpleDateUtils.DATE_PATTERN);
						Log.d(TAG, pastDate+":"+nowDate);
						if(pastDate.before(nowDate)){
							file.delete();
						}
					}
				}
			}
		}
	}
}
