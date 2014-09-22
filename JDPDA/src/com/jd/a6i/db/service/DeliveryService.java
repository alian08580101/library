package com.jd.a6i.db.service;

import java.util.List;

import android.content.Context;

import com.jd.a6i.db.dao.DeliveryDao;
import com.jd.a6i.db.dao.impl.DeliveryDaoImpl;
import com.jd.a6i.db.pojo.Delivery;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class DeliveryService extends BaseService<Delivery> {
	private Context context;
	private DeliveryDao deliveryDao;
	private static DeliveryService instance;
	private DeliveryService(Context context){
		this.deliveryDao = new DeliveryDaoImpl(context);
		super.prepareBaseDao(deliveryDao);
	}
	
	public static DeliveryService getInstance(Context context){
		if(instance==null){
			instance = new DeliveryService(context);
		}
		return instance;
	}
	
	public Delivery findUniqueDelivery(String packOrBox){
		try {
			return deliveryDao.findUniqueDelivery(packOrBox);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Delivery> findDeliveryList(int uploadStatus){
		try {
			return deliveryDao.findDeliveryList(uploadStatus);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteSortingTally(int uploadStatus, int hours){
		try {
			return deliveryDao.deleteDelivery(uploadStatus, hours);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
