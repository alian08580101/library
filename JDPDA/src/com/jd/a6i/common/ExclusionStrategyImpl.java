package com.jd.a6i.common;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.Inspection;
import com.jd.a6i.db.pojo.SealBoxError;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.jd.a6i.db.pojo.SealCarError;
import com.jd.a6i.db.pojo.SortingTally;

public class ExclusionStrategyImpl implements ExclusionStrategy {
	private static final String SEAL_CAR_ERROR_FILTER_FIELD = "|uploadStatus|"; 
	private static final String SEAL_BOX_ERROR_FILTER_FIELD = "|uploadStatus|";
	private static final String SORTING_TALLY_FILTER_FIELD = "|uploadStatus|";
	private static final String DELIVERY_FILTER_FIELD = "|uploadStatus|";
	private static final String SEAL_BOX_INFO_FIELD = "|uploadStatus|";
	private static final String INSPECTION_FILTER_FIELD ="|stateCode|";
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
	@Override
	public boolean shouldSkipField(FieldAttributes fieldAttributes) {
//		Log.e(TAG, "类名:"+fieldAttributes.getDeclaringClass());
//		Log.e(TAG, "属性名:"+fieldAttributes.getName());
		if(fieldAttributes.getDeclaringClass()==SealCarError.class){
			if(SEAL_CAR_ERROR_FILTER_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		if(fieldAttributes.getDeclaringClass()==SealBoxError.class){
			if(SEAL_BOX_ERROR_FILTER_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		if(fieldAttributes.getDeclaringClass()==SortingTally.class){
			if(SORTING_TALLY_FILTER_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		if(fieldAttributes.getDeclaringClass()==Delivery.class){
			if(DELIVERY_FILTER_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		if(fieldAttributes.getDeclaringClass()==Inspection.class){
			if(INSPECTION_FILTER_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		if(fieldAttributes.getDeclaringClass()==SealBoxInfo.class){
			if(SEAL_BOX_INFO_FIELD.contains("|"+fieldAttributes.getName()+"|")){
				return true;
			}
		}
		return false;
	}

}
