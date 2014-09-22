package com.jd.a6i;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.a6i.db.pojo.Account;

public abstract class JDBaseFragment extends Fragment {
	public View fragmentView;
	public int layoutId;
	
	protected Resources resources = null;
	
	protected TextView textTitle = null;
	protected TextView textUser = null;
	
	public JDBaseFragment(){
		prepareLayout(layoutId);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layoutId, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		prepareHeader();
	}
	
	public void baseToast(int stringId){
		Toast.makeText(getActivity(), getResources().getString(stringId), Toast.LENGTH_SHORT).show();
	}
	
	public void bastToast(int stringId,String...fromatArgs){
		Toast.makeText(getActivity(), getResources().getString(stringId, fromatArgs), Toast.LENGTH_SHORT).show();
	}
	
	public void prepareLayout(int layoutId){
		this.layoutId = layoutId;
	}
	
	public void prepareHeader(){
		resources = getResources();
		textTitle = (TextView) getView().findViewById(R.id.textTitle);
		textUser = (TextView) getView().findViewById(R.id.textUser);
		Account account = JDApplication.getInstance().getAccount();
		textUser.setText(account.getErpAccount());
	}
}
