package com.jd.a6i.db.dao.impl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.SealBoxErrorDao;
import com.jd.a6i.db.pojo.SealBoxError;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class SealBoxErrorDaoImpl extends CreateBaseDaoImpl<SealBoxError> implements SealBoxErrorDao {
	public SealBoxErrorDaoImpl(Context context){
		super.prepare(context, new RowMapper<SealBoxError>() {
			@Override
			public SealBoxError onRowMapper(Cursor cursor) {
				SealBoxError sealBoxError = new SealBoxError();
				sealBoxError.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				sealBoxError.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sealBoxError.setShieldsCode(cursor.getString(cursor.getColumnIndex("shieldsCode")));
				sealBoxError.setBoxCode(cursor.getString(cursor.getColumnIndex("boxCode")));
				sealBoxError.setShieldsError(cursor.getString(cursor.getColumnIndex("shieldsError")));
				sealBoxError.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				sealBoxError.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				sealBoxError.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				sealBoxError.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				sealBoxError.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				sealBoxError.setUploadStatus(cursor.getInt(cursor.getColumnIndex("uploadStatus")));
				return sealBoxError;
			}
		});
	}

	@Override
	public List<SealBoxError> findSealBoxErrorList(int uploadStatus) throws DaoException {
		String sql = "SELECT * FROM sealBoxError WHERE uploadStatus = ?";
		String[] values = {String.valueOf(uploadStatus)};
		List<SealBoxError> sealBoxErrorList = super.find(sql, values);
		return sealBoxErrorList;
	}

	@Override
	public int deleteSealBoxError(int uploadStatus, int hours) throws DaoException {
		String sql = "DELETE FROM sealBoxError WHERE uploadStatus = ? AND (julianday(datetime('now','localtime'))-julianday(operateTime))*24>"+hours;
		String[] values = {String.valueOf(uploadStatus)};
		return super.deleteUnique(sql, values);
	}
}
