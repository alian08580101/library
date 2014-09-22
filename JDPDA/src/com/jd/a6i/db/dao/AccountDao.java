package com.jd.a6i.db.dao;

import java.util.List;
import java.util.Map;

import com.jd.a6i.db.pojo.Account;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface AccountDao extends BaseDao<Account>{
	public Account findUniqueAccount(String erpAccount) throws DaoException;
	public List<Map<String,String>>findAllAccountPass() throws DaoException;
}
