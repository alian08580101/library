package com.jd.a6i.db.service;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.jd.a6i.db.dao.AccountDao;
import com.jd.a6i.db.dao.impl.AccountDaoImpl;
import com.jd.a6i.db.pojo.Account;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class AccountService extends BaseService<Account> {
	private Context context;
	private AccountDao accountDao;
	private static AccountService instance;
	
	public static AccountService getInstance(Context context){
		if(instance==null){
			instance = new AccountService(context);
		}
		return instance;
	}
	
	private AccountService(Context context){
		this.context = context;
		accountDao = new AccountDaoImpl(this.context);
		super.prepareBaseDao(accountDao);
	}
	
	public Account findUniqueAccount(String erpAccount){
		try {
			return accountDao.findUniqueAccount(erpAccount);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String, String>> findAllAccountPass(){
		try {
			return accountDao.findAllAccountPass();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
