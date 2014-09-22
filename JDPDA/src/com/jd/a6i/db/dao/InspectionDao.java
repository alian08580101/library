package com.jd.a6i.db.dao;

import java.util.List;
import java.util.Map;

import com.jd.a6i.db.pojo.Inspection;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface InspectionDao extends BaseDao<Inspection>{
	public Inspection findUniqueInspection(String...strings) throws DaoException;
	public List<Inspection> findInspection(String...strings)throws DaoException;
	public List<Inspection> findAllInspectionType(String...strings)throws DaoException;
	public int deleteInspection(String...strings)throws DaoException;
	public List<Map<String,Object>> findtypecount(String... strings) throws DaoException;
}
