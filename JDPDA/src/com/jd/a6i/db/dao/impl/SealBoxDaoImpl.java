package com.jd.a6i.db.dao.impl;

import android.content.Context;
import android.database.Cursor;

import com.jd.a6i.db.dao.SealBoxDao;
import com.jd.a6i.db.pojo.SealBox;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.utils.RowMapper;

public class SealBoxDaoImpl extends CreateBaseDaoImpl<SealBox> implements SealBoxDao {
	private Context context;
	public SealBoxDaoImpl(Context context){
		super.prepare(this.context, new RowMapper<SealBox>() {
			@Override
			public SealBox onRowMapper(Cursor cursor) {
				SealBox sealBox = new SealBox();
				sealBox.setId(cursor.getInt(cursor.getColumnIndex("id")));
				sealBox.setBoxCode(cursor.getString(cursor.getColumnIndex("packBoxCode")));
				sealBox.setSealCode(cursor.getString(cursor.getColumnIndex("sealCode")));
				return sealBox;
			}
		});
	}
	@Override
	public SealBox findUniqueSealBox(String sealCode) throws DaoException {
		String sql = "SELECT * FROM SealBox WHERE sealCode = ?";
		SealBox sealBox = super.findUniqueResult(sql, sealCode);
		return sealBox;
	}
}
