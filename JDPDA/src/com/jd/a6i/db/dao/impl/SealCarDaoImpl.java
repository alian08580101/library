package com.jd.a6i.db.dao.impl;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.SealCarDao;
import com.jd.a6i.db.pojo.SealCar;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class SealCarDaoImpl extends CreateBaseDaoImpl<SealCar> implements SealCarDao {
	private Context context;
	public SealCarDaoImpl(Context context){
		this.context = context;
		super.prepare(this.context, new RowMapper<SealCar>() {
			@Override
			public SealCar onRowMapper(Cursor cursor) {
				SealCar sealCar = new SealCar();
				sealCar.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sealCar.setDriver(cursor.getString(cursor.getColumnIndex("driver")));
				sealCar.setDriverCode(cursor.getInt(cursor.getColumnIndex("driverCode")));
				sealCar.setSealCode(cursor.getString(cursor.getColumnIndex("sealCode")));
				sealCar.setVehicleCode(cursor.getString(cursor.getColumnIndex("vehicleCode")));
				return sealCar;
			}
		});
	}
	@Override
	public SealCar findUniqueSealCar(String sealCode) throws DaoException {
		String sql = "SELECT * FROM sealCar WHERE sealCode = ?";
		SealCar sealCar = super.findUniqueResult(sql, sealCode);
		return sealCar;
	}
}
