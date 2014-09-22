package com.jd.a6i.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jd.a6i.db.dao.AccountDao;
import com.jd.a6i.db.pojo.Account;
import com.whl.dao.common.DaoException;
import com.whl.dao.impl.CreateBaseDaoImpl;
import com.whl.database.CreateDatabaseHelper;
import com.whl.utils.RowMapper;

public class AccountDaoImpl extends CreateBaseDaoImpl<Account> implements AccountDao {
	private Context context = null;
	public AccountDaoImpl(Context context){
		this.context = context;
		super.prepare(this.context, new RowMapper<Account>() {
			@Override
			public Account onRowMapper(Cursor cursor) {
				Account account = new Account();
				account.setId(cursor.getInt(cursor.getColumnIndex("id")));
				account.setDmsCode(cursor.getString(cursor.getColumnIndex("dmsCode")));
				account.setErpAccount(cursor.getString(cursor.getColumnIndex("erpAccount")));
				account.setOrgId(cursor.getInt(cursor.getColumnIndex("orgId")));
				account.setPassword(cursor.getString(cursor.getColumnIndex("password")));
				account.setSiteCode(cursor.getInt(cursor.getColumnIndex("siteCode")));
				account.setSiteName(cursor.getString(cursor.getColumnIndex("siteName")));
				account.setStaffId(cursor.getInt(cursor.getColumnIndex("staffId")));
				account.setStaffName(cursor.getString(cursor.getColumnIndex("staffName")));
				account.setRememberPassword(cursor.getInt(cursor.getColumnIndex("rememberPassword")));
				return account;
			}
		});
	}
	@Override
	public Account findUniqueAccount(String erpAccount) throws DaoException {
		String sql = "SELECT * FROM Account WHERE erpAccount = ?";
		Account account = super.findUniqueResult(sql, erpAccount);
		return account;
	}
	@Override
	public List<Map<String, String>> findAllAccountPass() throws DaoException {
		String sql = "select erpAccount,password from Account";
		CreateDatabaseHelper c = getDatabaseHelper();
		SQLiteDatabase sqlite = c.getReadableDatabase();
		Cursor cur = sqlite.rawQuery(sql,null);
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();

		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			int erpAccountColumn = cur.getColumnIndex("erpAccount");
			int passwordColumn = cur.getColumnIndex("password");
			String erpAccount = cur.getString(erpAccountColumn);
			String password = cur.getString(passwordColumn);
			Map<String, String> map = new HashMap<String, String>();
			map.put("erpAccount", erpAccount);
			map.put("password", password);
			items.add(map);
		}
		return items;
	}
}
