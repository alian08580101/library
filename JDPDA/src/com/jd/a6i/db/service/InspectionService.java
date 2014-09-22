package com.jd.a6i.db.service;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.jd.a6i.db.dao.InspectionDao;
import com.jd.a6i.db.dao.impl.InspectionDaoImpl;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.Inspection;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class InspectionService extends BaseService<Inspection> {
	private Context context;
	private InspectionDao InspectionDao;
	private static InspectionService instance = null;
	
	public InspectionService(Context context){
		this.context = context;
		InspectionDao = new InspectionDaoImpl(this.context);
		super.prepareBaseDao(InspectionDao);
	}

	public static InspectionService getInstance(Context context){
		if(instance==null){
			instance = new InspectionService(context);
		}
		return instance;
	}
	
	public Inspection findUniqueInspection(String...items){
		try {
			return InspectionDao.findUniqueInspection(items);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Inspection> findInspection(String... strings){
		try {
			return InspectionDao.findInspection(strings);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Inspection> findAllInspectionType(String... strings){
		try{
			return InspectionDao.findAllInspectionType(strings);
		}catch(DaoException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteInspection(String... strings){
		try{
			return InspectionDao.deleteInspection(strings);
		}catch(DaoException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Map<String,Object>> findtypecount(String... strings){
		try{
			return InspectionDao.findtypecount(strings);
		}catch(DaoException e){
			e.printStackTrace();
		}
		return null;
	}
}
