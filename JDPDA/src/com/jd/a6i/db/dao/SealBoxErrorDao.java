package com.jd.a6i.db.dao;

import java.util.List;

import com.jd.a6i.db.pojo.SealBoxError;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface SealBoxErrorDao extends BaseDao<SealBoxError> {
	public List<SealBoxError> findSealBoxErrorList(int uploadStatus) throws DaoException;
	public int deleteSealBoxError(int uploadStatus,int hours) throws DaoException;
}
