package com.jd.a6i.db.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jd.a6i.db.dao.InspectionDao;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.Inspection;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.database.CreateDatabaseHelper;
import com.whl.utils.RowMapper;

public class InspectionDaoImpl extends CreateBaseDaoImpl<Inspection> implements
		InspectionDao {
        
	private Context context = null;
	public InspectionDaoImpl(Context context)
	{
		this.context = context;
		super.prepare(this.context, new RowMapper<Inspection>() {
			@Override
			public Inspection onRowMapper(Cursor cursor) {
				Inspection inspection = new Inspection();
				inspection.setId(cursor.getInt(cursor.getColumnIndex("id")));
				inspection.setSealBoxCode(cursor.getString(cursor.getColumnIndex("sealBoxCode")));
				inspection.setBoxCode(cursor.getString(cursor.getColumnIndex("boxCode")));
				inspection.setPackageBarOrWaybillCode(cursor.getString(cursor.getColumnIndex("packageBarOrWaybillCode")));
				inspection.setExceptionType(cursor.getString(cursor.getColumnIndex("exceptionType")));
				inspection.setOperateType(cursor.getInt(cursor.getColumnIndex("operateType")));
				inspection.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				inspection.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				inspection.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				inspection.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				inspection.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				inspection.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				inspection.setStateCode(cursor.getInt(cursor.getColumnIndex("stateCode")));
				inspection.setReceiveSiteCode(cursor.getInt(cursor.getColumnIndex("receiveSiteCode")));
				return inspection;
			}
		});
	}
	@Override
	public Inspection findUniqueInspection(String... strings)
			throws DaoException {
		String sql = "select * from Inspection where packageBarOrWaybillCode = ? AND operateType = ?";
		Inspection inspection = super.findUniqueResult(sql, strings);
		return inspection;
	}
	@Override
	public List<Inspection> findInspection(String... strings)
			throws DaoException {
		String sql = "select DISTINCT packageBarOrWaybillCode,id,sealBoxCode,boxCode,exceptionType,receiveSiteCode,operateType,businessType,userName,userCode,siteCode,siteName,operateTime,stateCode from Inspection where boxCode=? and operateType=?";
		List<Inspection> listInspection = super.find(sql, strings);
		return listInspection;
	}
	@Override
	public List<Inspection> findAllInspectionType(String... strings)
			throws DaoException {
		String sql = "select * from Inspection where stateCode=?";
		List<Inspection> listInspection = super.find(sql, strings);
		return listInspection;
	}
	@Override
	public int deleteInspection(String... strings) throws DaoException {
		// TODO Auto-generated method stub
		String sql =String.format("delete from Inspection where (julianday(datetime('now','localtime'))-julianday(operateTime))*24>%1$s AND stateCode = %2$s",strings[0],strings[1]);
		int result = super.deleteUnique(sql);
		return result;
	}

	public List<Map<String,Object>> findtypecount(String... strings) throws DaoException {
		String sql = "select receiveSiteCode,count(operateType)AS TypeNo from Inspection where exceptionType =? group by receiveSiteCode";
		CreateDatabaseHelper c = getDatabaseHelper();
		SQLiteDatabase sqlite = c.getReadableDatabase();
		Cursor cur = sqlite.rawQuery(sql,strings);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			int typeNoColumn = cur.getColumnIndex("TypeNo");
			int receiveSiteCodeColumn = cur.getColumnIndex("receiveSiteCode");
			String typeNo = String.valueOf(cur.getInt(typeNoColumn));
			String receiveSiteCode = cur.getString(receiveSiteCodeColumn);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeNo", typeNo);
			map.put("receiveSiteCode", receiveSiteCode);
			items.add(map);
		}
		return items;
	}
	
}
