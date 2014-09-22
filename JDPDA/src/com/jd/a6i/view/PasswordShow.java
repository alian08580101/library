package com.jd.a6i.view;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jd.a6i.R;

public class PasswordShow extends RelativeLayout implements OnCheckedChangeListener,View.OnFocusChangeListener{
	private CheckBox checkShow;
	private EditText editText;
	private RelativeLayout layoutPassword;
	private LayoutInflater layoutInflater;
	public PasswordShow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPasswordShow();
	}

	public PasswordShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPasswordShow();
	}

	public PasswordShow(Context context) {
		super(context);
		initPasswordShow();
	}
	
	private void initPasswordShow(){
		if(layoutInflater==null){
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View passwordShowView = layoutInflater.inflate(R.layout.jd_password_show, this, true);
		this.layoutPassword = (RelativeLayout) passwordShowView.findViewById(R.id.layoutPassword);
		this.editText = (EditText) passwordShowView.findViewById(R.id.editText);
		this.checkShow = (CheckBox) passwordShowView.findViewById(R.id.checkShow);
		
		this.editText.setOnFocusChangeListener(this);
		this.checkShow.setOnFocusChangeListener(this);
		this.checkShow.setOnCheckedChangeListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(v.getId()==R.id.editText||v.getId()==R.id.checkShow){
			passwordShowViewFocus(hasFocus);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		editText.requestFocus();
		if(isChecked){
			this.editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		}else{
			this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
	}
	
	protected void passwordShowViewFocus(boolean hasFocus){
		if(hasFocus){
			layoutPassword.setBackgroundResource(R.drawable.jd_corner_round_focuse);
		}else{
			layoutPassword.setBackgroundResource(R.drawable.jd_corner_round_normal);
		}
	}
}
