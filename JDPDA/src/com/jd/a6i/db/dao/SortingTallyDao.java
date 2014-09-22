package com.jd.a6i.db.dao;

import java.util.List;
import java.util.Map;

import com.jd.a6i.db.pojo.SortingTally;
import com.whl.dao.common.BaseDao;
import com.whl.dao.common.DaoException;

public interface SortingTallyDao extends BaseDao<SortingTally> {
	public List<SortingTally> findSortingTallyList(String boxCode) throws DaoException;
	public SortingTally findUniqueSortingTally(String packageCode) throws DaoException;
	public List<Map<String,Object>> findCommitedPackNo(String... strings) throws DaoException;
	public List<SortingTally> findSortingTallyList(int uploadStatus) throws DaoException;
	public int deleteSortingTally(int uploadStatus, int hours) throws DaoException;
}
