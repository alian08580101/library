package com.jd.a6i.db.dao;

import java.util.List;

import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.pojo.Inspection;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface ErrorTypeDao extends BaseDao<ErrorType> {
	public int deleteAllErrorType() throws DaoException;
	public List<ErrorType> findErrorTypeList(int typeGroup) throws DaoException;
	public List<ErrorType> findErrorType(String...strings) throws DaoException;
}
