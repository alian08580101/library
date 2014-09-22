package com.jd.a6i.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jd.a6i.db.dao.SortingTallyDao;
import com.jd.a6i.db.pojo.SortingTally;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.database.CreateDatabaseHelper;
import com.whl.utils.RowMapper;

public class SortingTallyDaoImpl extends CreateBaseDaoImpl<SortingTally> implements SortingTallyDao {
	public SortingTallyDaoImpl(Context context){
		super.prepare(context, new RowMapper<SortingTally>() {
			@Override
			public SortingTally onRowMapper(Cursor cursor) {
				SortingTally sortingTally = new SortingTally();
				sortingTally.setBoxCode(cursor.getString(cursor.getColumnIndex("boxCode")));
				sortingTally.setReceiveSiteCode(cursor.getInt(cursor.getColumnIndex("receiveSiteCode")));
				sortingTally.setReceiveSiteName(cursor.getString(cursor.getColumnIndex("receiveSiteName")));
				sortingTally.setPackageCode(cursor.getString(cursor.getColumnIndex("packageCode")));
				sortingTally.setIsLoss(cursor.getInt(cursor.getColumnIndex("isLoss")));
				sortingTally.setIsCancel(cursor.getInt(cursor.getColumnIndex("isCancel")));
				sortingTally.setFeatureType(cursor.getInt(cursor.getColumnIndex("featureType")));
				sortingTally.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sortingTally.setBusinessType(cursor.getInt(cursor.getColumnIndex("businessType")));
				sortingTally.setUserCode(cursor.getInt(cursor.getColumnIndex("userCode")));
				sortingTally.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
				sortingTally.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				sortingTally.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				sortingTally.setOperateTime(cursor.getString(cursor.getColumnIndex("operateTime")));
				sortingTally.setUploadStatus(cursor.getInt(cursor.getColumnIndex("uploadStatus")));
				return sortingTally;
			}
		});
	}
	@Override
	public List<SortingTally> findSortingTallyList(String boxCode) throws DaoException {
		String sql = "SELECT * FROM sortingTally WHERE boxCode = ?";
		String[] values = {boxCode};
		List<SortingTally> sortingTallyList = super.find(sql, values);
		return sortingTallyList;
	}
	@Override
	public SortingTally findUniqueSortingTally(String packageCode) throws DaoException {
		String sql = "SELECT * FROM sortingTally WHERE packageCode = ?";
		String[] values = {packageCode};
		SortingTally sortingTally = super.findUniqueResult(sql, values);
		return sortingTally;
	}
	@Override
	public List<SortingTally> findSortingTallyList(int uploadStatus) throws DaoException {
		String sql = "SELECT * FROM sortingTally WHERE uploadStatus = ?";
		String[] values = {String.valueOf(uploadStatus)};
		List<SortingTally> sortingTallyList = super.find(sql, values);
		return sortingTallyList;
	}
	@Override
	public int deleteSortingTally(int uploadStatus, int hours) throws DaoException {
		String sql = "DELETE FROM sortingTally WHERE uploadStatus = ? AND (julianday(datetime('now','localtime'))-julianday(operateTime))*24 > "+hours;
		String[] values = {String.valueOf(uploadStatus)};
		int result = super.deleteUnique(sql, values);
		return result;
	}

	public List<Map<String,Object>> findCommitedPackNo(String... strings) throws DaoException {
		String sql = "SELECT uploadStatus, COUNT(packageCode) AS packNum, siteCode,siteName FROM sortingTally WHERE boxCode=? GROUP BY uploadStatus, siteCode, siteName";
		CreateDatabaseHelper c = getDatabaseHelper();
		SQLiteDatabase sqlite = c.getReadableDatabase();
		Cursor cur = sqlite.rawQuery(sql,strings);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			int packNumColumn = cur.getColumnIndex("packNum");
			int uploadStatusColumn = cur.getColumnIndex("uploadStatus");
			int siteCodeColumn = cur.getColumnIndex("siteCode");
			int siteNameColumn = cur.getColumnIndex("siteName");
			
			String packNum = String.valueOf(cur.getInt(packNumColumn));
			String uploadStatus = cur.getString(uploadStatusColumn);
			String siteCode = String.valueOf(cur.getInt(siteCodeColumn));
			String siteName = cur.getString(siteNameColumn);
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("packNum", packNum);
			map.put("uploadStatus", uploadStatus);
			map.put("siteCode", siteCode);
			map.put("siteName", siteName);
			
			items.add(map);
		}
		return items;
	}
	
}
