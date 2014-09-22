package com.jd.a6i.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jd.a6i.R;

public class JDProgressDialog extends ProgressDialog {
	private static JDProgressDialog instance;
	private Context context;
	public JDProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}
	
	public static JDProgressDialog getJDProgressDialog(Context context, int theme){
		if(instance==null){
			instance=new JDProgressDialog(context, theme);
		}
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
		setContentView(R.layout.jd_progress_dialog);
	}
	
	public void setDialogText(int stringId){
		TextView textProgressTitle = (TextView) findViewById(R.id.textProgressTitle);
		textProgressTitle.setText(stringId);
		AnimationDrawable animationDrawable =(AnimationDrawable) textProgressTitle.getCompoundDrawables()[1];
		animationDrawable.start();
	}

	public void setDialogText(String prompt){
		TextView textProgressTitle = (TextView) findViewById(R.id.textProgressTitle);
		textProgressTitle.setText(prompt);
		AnimationDrawable animationDrawable =(AnimationDrawable) textProgressTitle.getCompoundDrawables()[1];
		animationDrawable.start();
	}
}
