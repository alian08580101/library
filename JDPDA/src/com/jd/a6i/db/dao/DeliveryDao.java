package com.jd.a6i.db.dao;

import java.util.List;

import com.jd.a6i.db.pojo.Delivery;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface DeliveryDao extends BaseDao<Delivery> {
	public Delivery findUniqueDelivery(String packOrBox) throws DaoException;
	public List<Delivery> findDeliveryList(int uploadStatus) throws DaoException;
	public int deleteDelivery(int uploadStatus,int hours) throws DaoException;
}
