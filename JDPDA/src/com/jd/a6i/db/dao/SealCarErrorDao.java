package com.jd.a6i.db.dao;

import java.util.List;

import com.jd.a6i.db.pojo.SealCarError;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface SealCarErrorDao extends BaseDao<SealCarError> {
	public List<SealCarError> findSealCarErrorList(int uploadStatus) throws DaoException;
	public int deleteSealCarError(int uploadStatus,int hours) throws DaoException;
}
