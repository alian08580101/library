package com.jd.a6i.db.service;

import java.util.List;

import android.content.Context;

import com.jd.a6i.db.dao.ErrorTypeDao;
import com.jd.a6i.db.dao.impl.ErrorTypeDaoImpl;
import com.jd.a6i.db.pojo.ErrorType;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class ErrorTypeService extends BaseService<ErrorType> {
	private ErrorTypeDao errorTypeDao;
	private static ErrorTypeService instance;
	public static ErrorTypeService getErrorTypeService(Context context){
		if(instance==null){
			instance = new ErrorTypeService(context);
		}
		return instance;
	}
	
	private ErrorTypeService(Context context){
		this.errorTypeDao = new ErrorTypeDaoImpl(context);
		super.prepareBaseDao(errorTypeDao);
	}
	
	public int deleteAllErrorType() {
		try {
			return errorTypeDao.deleteAllErrorType();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<ErrorType> findErrorTypeList(int typeGroup){
		try {
			return errorTypeDao.findErrorTypeList(typeGroup);
		} catch (DaoException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ErrorType> findErrorType(String... strings)
			throws DaoException {
		try {
			return errorTypeDao.findErrorType(strings);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
