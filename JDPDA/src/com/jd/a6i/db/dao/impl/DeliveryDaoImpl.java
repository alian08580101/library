package com.jd.a6i.db.dao.impl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.DeliveryDao;
import com.jd.a6i.db.pojo.Delivery;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class DeliveryDaoImpl extends CreateBaseDaoImpl<Delivery> implements DeliveryDao {
	private Context context;
	public DeliveryDaoImpl(Context context){
		super.prepare(context, new RowMapper<Delivery>() {
			@Override
			public Delivery onRowMapper(Cursor cursor) {
				Delivery delivery = new Delivery();
				delivery.setId(cursor.getInt(cursor.getColumnIndex("id")));
				delivery.setShieldsCarCode(cursor.getString(cursor.getColumnIndex("shieldsCarCode")));
				delivery.setCarCode(cursor.getString(cursor.getColumnIndex("carCode")));
				delivery.setSealBoxCode(cursor.getString(cursor.getColumnIndex("sealBoxCode")));
				delivery.setPackOrBox(cursor.getString(cursor.getColumnIndex("packOrBox")));
				delivery.setTurnoverBoxCode(cursor.getString(cursor.getColumnIndex("turnoverBoxCode")));
				delivery.setQueueNo(cursor.getString(cursor.getColumnIndex("queueNo")));
				delivery.setDepartureCarId(cursor.getString(cursor.getColumnIndex("departureCarId")));
				delivery.setShieldsCarTime(cursor.getString(cursor.getColumnIndex("shieldsCarTime")));
				delivery.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				delivery.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				delivery.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				delivery.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				delivery.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				delivery.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				delivery.setUploadStatus(cursor.getInt(cursor.getColumnIndex("uploadStatus")));
				return delivery;
			}
		});
	}
	@Override
	public Delivery findUniqueDelivery(String packOrBox) throws DaoException {
		String sql = "SELECT * FROM delivery WHERE packOrBox = ?";
		Delivery delivery = super.findUniqueResult(sql, packOrBox);
		return delivery;
	}
	@Override
	public List<Delivery> findDeliveryList(int uploadStatus) throws DaoException {
		String sql = "SELECT * FROM delivery WHERE uploadStatus = ?";
		String[] values = {String.valueOf(uploadStatus)};
		List<Delivery> deliveryList = super.find(sql, values);
		return deliveryList;
	}
	@Override
	public int deleteDelivery(int uploadStatus, int hours) throws DaoException {
		String sql = "DELETE FROM delivery WHERE uploadStatus = ? AND (julianday(datetime('now','localtime'))-julianday(operateTime))*24>"+hours;
		String[] values = {String.valueOf(uploadStatus)};
		return super.deleteUnique(sql, values);
	}
}
