package com.jd.a6i.db.service;

import android.content.Context;

import com.jd.a6i.db.dao.SealBoxDao;
import com.jd.a6i.db.dao.impl.SealBoxDaoImpl;
import com.jd.a6i.db.pojo.SealBox;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SealBoxService extends BaseService<SealBox> {
	private Context context;
	private SealBoxDao sealBoxDao;
	public SealBoxService(Context context){
		sealBoxDao = new SealBoxDaoImpl(context);
		super.prepareBaseDao(sealBoxDao);
	}
	
	public SealBox findUniqueSealBox(String sealCode){
		try {
			return sealBoxDao.findUniqueSealBox(sealCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
