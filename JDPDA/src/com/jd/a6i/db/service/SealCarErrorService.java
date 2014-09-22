package com.jd.a6i.db.service;

import java.util.List;

import android.content.Context;

import com.jd.a6i.db.dao.SealCarErrorDao;
import com.jd.a6i.db.dao.impl.SealCarErrorDaoImpl;
import com.jd.a6i.db.pojo.SealCarError;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SealCarErrorService extends BaseService<SealCarError> {
	private SealCarErrorDao sealCarErrorDao;
	private static SealCarErrorService instance;
	private SealCarErrorService(Context context){
		this.sealCarErrorDao = new SealCarErrorDaoImpl(context);
		super.prepareBaseDao(sealCarErrorDao);
	}
	
	public static SealCarErrorService getInstance(Context context){
		if(instance==null){
			instance = new SealCarErrorService(context);
		}
		return instance;
	}
	
	public List<SealCarError> findSealCarErrorList(int uploadStatus) {
		try {
			return sealCarErrorDao.findSealCarErrorList(uploadStatus);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteSealCarError(int uploadStatus, int hours) {
		try {
			return sealCarErrorDao.deleteSealCarError(uploadStatus, hours);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
