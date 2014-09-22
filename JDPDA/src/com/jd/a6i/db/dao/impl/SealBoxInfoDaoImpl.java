package com.jd.a6i.db.dao.impl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.SealBoxInfoDao;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class SealBoxInfoDaoImpl extends CreateBaseDaoImpl<SealBoxInfo> implements SealBoxInfoDao {
	public SealBoxInfoDaoImpl(Context context){
		super.prepare(context, new RowMapper<SealBoxInfo>() {
			@Override
			public SealBoxInfo onRowMapper(Cursor cursor) {
				SealBoxInfo sealBoxInfo = new SealBoxInfo();
				sealBoxInfo.setBoxCode(cursor.getString(cursor.getColumnIndex("boxCode")));
				sealBoxInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sealBoxInfo.setSealCode(cursor.getString(cursor.getColumnIndex("sealCode")));
				sealBoxInfo.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				sealBoxInfo.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				sealBoxInfo.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				sealBoxInfo.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				sealBoxInfo.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				sealBoxInfo.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				sealBoxInfo.setUploadStatus(cursor.getInt(cursor.getColumnIndex("uploadStatus")));
				return sealBoxInfo;
			}
		});
	}
	@Override
	public SealBoxInfo findUniqueSealBoxInfo(String sealCode) throws DaoException {
		String sql = "SELECT * FROM sealBoxInfo WHERE sealCode = ?";
		String[] values = {sealCode};
		SealBoxInfo sealBoxInfo = super.findUniqueResult(sql, values);
		return sealBoxInfo;
	}
	@Override
	public List<SealBoxInfo> findSealBoxInfoList(int uploadStatus) throws DaoException {
		String sql = "SELECT * FROM sealBoxInfo WHERE uploadStatus = ?";
		String[] values = {String.valueOf(uploadStatus)};
		List<SealBoxInfo> sealBoxInfoList = super.find(sql, values);
		return sealBoxInfoList;
	}
	@Override
	public int deleteSealBoxInfo(int uploadStatus, int hours) throws DaoException {
		String sql = "DELETE FROM sealBoxInfo WHERE uploadStatus = ? AND (julianday(datetime('now','localtime'))-julianday(operateTime))*24>"+hours;
		String[] values = {String.valueOf(uploadStatus)};
		return super.deleteUnique(sql, values);
	}

}
