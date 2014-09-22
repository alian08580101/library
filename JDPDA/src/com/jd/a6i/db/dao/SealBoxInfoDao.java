package com.jd.a6i.db.dao;

import java.util.List;

import com.jd.a6i.db.pojo.SealBoxInfo;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface SealBoxInfoDao extends BaseDao<SealBoxInfo> {
	public SealBoxInfo findUniqueSealBoxInfo(String sealCode) throws DaoException;
	public List<SealBoxInfo> findSealBoxInfoList(int uploadStatus) throws DaoException;
	public int deleteSealBoxInfo(int uploadStatus,int hours) throws DaoException;
}
