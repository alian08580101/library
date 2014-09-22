package com.jd.a6i.db.dao.impl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.ErrorTypeDao;
import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.pojo.Inspection;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class ErrorTypeDaoImpl extends CreateBaseDaoImpl<ErrorType> implements ErrorTypeDao {
	public ErrorTypeDaoImpl(Context context){
		super.prepare(context, new RowMapper<ErrorType>() {
			@Override
			public ErrorType onRowMapper(Cursor cursor) {
				ErrorType errorType = new ErrorType();
				errorType.setId(cursor.getInt(cursor.getColumnIndex("id")));
				errorType.setTypeName(cursor.getString(cursor.getColumnIndex("typeName")));
				errorType.setTypeCode(cursor.getInt(cursor.getColumnIndex("typeCode")));
				errorType.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
				errorType.setParentId(cursor.getInt(cursor.getColumnIndex("parentId")));
				errorType.setNodeLevel(cursor.getInt(cursor.getColumnIndex("nodeLevel")));
				errorType.setTypeGroup(cursor.getInt(cursor.getColumnIndex("typeGroup")));
				errorType.setDataUpdate(cursor.getString(cursor.getColumnIndex("dataUpdate")));
				return errorType;
			}
		});
	}

	@Override
	public int deleteAllErrorType() throws DaoException {
		String sql = "DELETE FROM sqlite_sequence WHERE name='errorType';";
		if(super.deleteAll()==1){
			return super.execSQL(sql, null);
		}
		return 0;
	}

	@Override
	public List<ErrorType> findErrorTypeList(int typeGroup) throws DaoException {
		String sql = "SELECT * FROM errorType WHERE typeGroup = ?";
		List<ErrorType> errorTypeList = super.find(sql, new String[]{String.valueOf(typeGroup)});
		return errorTypeList;
	}
	
	@Override
	public List<ErrorType> findErrorType(String...strings)
			throws DaoException {
		String sql = "select * from ErrorType where typeGroup=?";
		List<ErrorType> listErrorType = super.find(sql, strings);
		return listErrorType;
	}
}
