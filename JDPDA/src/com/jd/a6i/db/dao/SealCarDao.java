package com.jd.a6i.db.dao;

import com.jd.a6i.db.pojo.SealCar;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface SealCarDao extends BaseDao<SealCar> {
	public SealCar findUniqueSealCar(String sealCode) throws DaoException;
}
