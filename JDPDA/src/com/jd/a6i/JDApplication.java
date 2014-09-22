package com.jd.a6i;

import java.util.List;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.jd.a6i.db.pojo.Account;
import com.whl.database.CreateDatabaseHelper;

public class JDApplication extends Application {
	private Account account;
	private boolean isLogin = false;
	private String jsonList;
	private static JDApplication instance;
	public static JDApplication getInstance(){
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		initDatabase();
		UncaughtExceptionHandlerManager jdUncaughtExceptionHandler = UncaughtExceptionHandlerManager.getInstance();
		jdUncaughtExceptionHandler.init(this);
		instance = this;
	}
	
	public boolean isDebuggable() {
        PackageManager pm = getPackageManager();
        try {
            //noinspection ConstantConditions
            int flags = pm.getApplicationInfo(getPackageName(), 0).flags;
            return ((flags & ApplicationInfo.FLAG_DEBUGGABLE) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	protected void initDatabase(){
		CreateDatabaseHelper createDatabaseHelper = CreateDatabaseHelper.getInstanceDatabaseHelper(getApplicationContext());
		SQLiteDatabase database = createDatabaseHelper.getWritableDatabase();
		database.beginTransaction();
		String[] sqlArray = getResources().getStringArray(R.array.createSqlArray);
		for (String sql : sqlArray) {
			database.execSQL(sql);
		}
		database.setTransactionSuccessful();
		database.endTransaction();
		database.close();
	}
	public String getJsonList() {
		return jsonList;
	}
	public void setJsonList(String jsonList) {
		this.jsonList = jsonList;
	}
}
