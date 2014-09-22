package com.jd.a6i.db.service;

import java.util.List;

import android.content.Context;

import com.jd.a6i.db.dao.SealBoxErrorDao;
import com.jd.a6i.db.dao.impl.SealBoxErrorDaoImpl;
import com.jd.a6i.db.pojo.SealBoxError;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SealBoxErrorService extends BaseService<SealBoxError> {
	private SealBoxErrorDao sealBoxErrorDao;
	private static SealBoxErrorService instance;
	private SealBoxErrorService(Context context){
		this.sealBoxErrorDao = new SealBoxErrorDaoImpl(context);
		super.prepareBaseDao(sealBoxErrorDao);
	}
	
	public static SealBoxErrorService getInstance(Context context){
		if(instance==null){
			instance = new SealBoxErrorService(context);
		}
		return instance;
	}
	
	public List<SealBoxError> findSealBoxErrorList(int uploadStatus) {
		try {
			return sealBoxErrorDao.findSealBoxErrorList(uploadStatus);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteSealBoxError(int uploadStatus, int hours) {
		try {
			return sealBoxErrorDao.deleteSealBoxError(uploadStatus, hours);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
