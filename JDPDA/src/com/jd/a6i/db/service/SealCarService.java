package com.jd.a6i.db.service;

import android.content.Context;

import com.jd.a6i.db.dao.SealCarDao;
import com.jd.a6i.db.dao.impl.SealCarDaoImpl;
import com.jd.a6i.db.pojo.SealCar;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SealCarService extends BaseService<SealCar> {
	private Context context;
	private SealCarDao sealCarDao;
	public SealCarService(Context context){
		sealCarDao = new SealCarDaoImpl(context);
		super.prepareBaseDao(sealCarDao);
	}
	
	public SealCar findUniqueSealCar(String sealCode){
		try {
			return sealCarDao.findUniqueSealCar(sealCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
