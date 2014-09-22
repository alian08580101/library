package com.jd.a6i.db.service;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.jd.a6i.db.dao.SortingTallyDao;
import com.jd.a6i.db.dao.impl.SortingTallyDaoImpl;
import com.jd.a6i.db.pojo.SortingTally;
import com.whl.dao.common.DaoException;
import com.whl.service.common.BaseService;

public class SortingTallyService extends BaseService<SortingTally> {
	private SortingTallyDao sortingTallyDao;
	private static SortingTallyService instance;
	private SortingTallyService(Context context){
		sortingTallyDao = new SortingTallyDaoImpl(context);
		super.prepareBaseDao(sortingTallyDao);
	}
	
	public static SortingTallyService getInstance(Context context){
		if(instance==null){
			instance = new SortingTallyService(context);
		}
		return instance;
	}
	
	public List<SortingTally> findSortingTallyList(String boxCode){
		try {
			return sortingTallyDao.findSortingTallyList(boxCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SortingTally findUniqueSortingTally(String packageCode){
		try {
			return sortingTallyDao.findUniqueSortingTally(packageCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> findCommitedPackNo(String... boxCode){
		try {
			return sortingTallyDao.findCommitedPackNo(boxCode);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SortingTally> findSortingTallyList(int uploadStatus){
		try {
			return sortingTallyDao.findSortingTallyList(uploadStatus);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteSortingTally(int uploadStatus, int hours) {
		try {
			return sortingTallyDao.deleteSortingTally(uploadStatus,hours);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
