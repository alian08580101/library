package com.jd.a6i.db.dao.impl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.SealCarErrorDao;
import com.jd.a6i.db.pojo.SealCarError;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class SealCarErrorDaoImpl extends CreateBaseDaoImpl<SealCarError> implements SealCarErrorDao {
	public SealCarErrorDaoImpl(Context context){
		super.prepare(context, new RowMapper<SealCarError>() {
			@Override
			public SealCarError onRowMapper(Cursor cursor) {
				SealCarError sealCarError = new SealCarError();
				sealCarError.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				sealCarError.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sealCarError.setShieldsCode(cursor.getString(cursor.getColumnIndex("shieldsCode")));
				sealCarError.setCarCode(cursor.getString(cursor.getColumnIndex("carCode")));
				sealCarError.setShieldsError(cursor.getString(cursor.getColumnIndex("shieldsError")));
				sealCarError.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				sealCarError.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				sealCarError.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				sealCarError.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				sealCarError.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				sealCarError.setUploadStatus(cursor.getInt(cursor.getColumnIndex("uploadStatus")));
				return sealCarError;
			}
		});
	}

	@Override
	public List<SealCarError> findSealCarErrorList(int uploadStatus) throws DaoException {
		String sql = "SELECT * FROM sealCarError WHERE uploadStatus = ?";
		String[] values = {String.valueOf(uploadStatus)};
		List<SealCarError> sealCarErrorList = super.find(sql, values);
		return sealCarErrorList;
	}

	@Override
	public int deleteSealCarError(int uploadStatus, int hours) throws DaoException {
		String sql = "DELETE FROM sealCarError WHERE uploadStatus = ? AND (julianday(datetime('now','localtime'))-julianday(operateTime))*24>"+hours;
		String[] values = {String.valueOf(uploadStatus)};
		return super.deleteUnique(sql, values);
	}
}
