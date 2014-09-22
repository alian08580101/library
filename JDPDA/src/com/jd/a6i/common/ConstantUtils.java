package com.jd.a6i.common;

import java.util.ArrayList;
import java.util.List;

import com.jd.a6i.db.pojo.StationInfo;

public class ConstantUtils {
	public final static int MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;
	
	public final static String TASK_CLASS_TYPE = "taskClass";
	/**δ�ϴ���־*/
	public final static int UPLOAD_0 = 0;
	/**���ϴ���־*/
	public final static int UPLOAD_1 = 1;
	/**��ס�����־*/
	public final static int REMEMBER_PASSWORD_1 = 1;
	/**���������־*/
	public final static int REMEMBER_PASSWORD_0 = 0;
	
	/**������ʱ�첽�ϴ��㲥Action*/
	public final static String ACTION_UPLOAD_TASK = "com.jd.a6i.upload.task";
	/**�ϴ������쳣type*/
	public final static int UPLOAD_SEAL_CAR_ERROR_TYPE = 1120;
	/**�ϴ�����쳣type*/
	public final static int UPLOAD_SEAL_BOX_ERROR_TYPE = 1140;
	/**�ϴ��ּ����type*/
	public final static int UPLOAD_SORTING_TALLY_TYPE = 1200;
	/**�ϴ��ջ�����type*/
	public final static int UPLOAD_DELIVERY_TYPE = 1110;
	/**�ϴ�������Ϣtype*/
	public final static int UPLOAD_SEAL_BOX_INFO_TYPE = 1210;
	
	public final static String SORTING_BUNDLE = "sortingBundle";
	public final static String SORTING_TYPE = "sortingType";
	public final static int WAREHOUSE_SORTING = 0;
	public final static int AFTERSALE_SORTING = 1;
	public final static int BMERCHANT_SORTING = 2;
	public final static int STEP_SORT_CENTER_SORTING = 3;
	public final static int DEFAULT_SORTING = 4;
	/**
	 * 	�����ѡ��130
	 *  �˻�ԭ��110
	 *  ��ǩ�쳣120
	 *  ����쳣130
	 *  �ּ��������򱸼���ּ�����ԭ��140
	 */
	public final static int TYPE_GROUP_SEAL = 120;
	
	public static final String DES_KEY = "JD.COM";
	
	
	/**check expression*/
	public static final String SEAL_CAR_NO_REGULAR_EXPRESSION = "^\\d{8}[C]$";
	public static final String SEAL_BOX_NO_REGULAR_EXPRESSION = "^\\d{8}[X]$";
	public static final String CAR_NO_REGULAR_EXPRESSION = "^\\d{3}[A-Za-z0-9-]{5,8}$";
	public static final String TURNOVER_BOX_NO_EXPRESSION = "^([MZ]{2})([0-9]{5})F([0-9]{6})$";
	public static final List<StationInfo> scanSiteCode = new ArrayList<StationInfo>();
}
