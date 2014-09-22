package com.jd.a6i.db.service;

import java.util.List;

import android.content.Context;

import com.jd.a6i.db.dao.SealBoxInfoDao;
import com.jd.a6i.db.dao.impl.SealBoxInfoDaoImpl;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SealBoxInfoService extends BaseService<SealBoxInfo> {
	private static SealBoxInfoService instance;
	private SealBoxInfoDao sealBoxInfoDao;
	private SealBoxInfoService(Context context){
		this.sealBoxInfoDao = new SealBoxInfoDaoImpl(context);
		super.prepareBaseDao(sealBoxInfoDao);
	}
	
	public static SealBoxInfoService getInstance(Context context){
		if(instance==null){
			instance = new SealBoxInfoService(context);
		}
		return instance;
	}
	
	public SealBoxInfo findUniqueSealBoxInfo(String sealCode){
		try {
			return sealBoxInfoDao.findUniqueSealBoxInfo(sealCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SealBoxInfo> findSealBoxInfoList(int uploadStatus){
		try {
			return sealBoxInfoDao.findSealBoxInfoList(uploadStatus);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteSealBoxInfo(int uploadStatus, int hours){
		try {
			return sealBoxInfoDao.deleteSealBoxInfo(uploadStatus, hours);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
