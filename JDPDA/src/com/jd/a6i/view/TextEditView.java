package com.jd.a6i.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.a6i.R;

public class TextEditView extends LinearLayout {
	
	public TextView textView;
	public EditText editText;
	public String labelText;
	public float labelSize;
	public int labelPosition;
	public int labelColor;

	public TextEditView(Context context) {
		super(context);
		initJDEditText();
	}

	public TextEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttributeSet(attrs);
		initJDEditText();
	}
	
	public TextEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttributeSet(attrs);
		initJDEditText();
	}
	
	public void initAttributeSet(AttributeSet attrs){
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextEditView);
		this.labelText = typedArray.getString(R.styleable.TextEditView_labelText);	
		/*this.labelSize = typedArray.getDimensionPixelSize(R.styleable.TextEditView_labelSize, 
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));*/
		this.labelSize = typedArray.getDimensionPixelSize(R.styleable.TextEditView_labelSize, 18);
		this.labelPosition = typedArray.getInt(R.styleable.TextEditView_labelPosition, 0);
		this.labelColor = typedArray.getColor(R.styleable.TextEditView_labelColor, 0x00FFAA00);
		typedArray.recycle();
	}
	
	public void initJDEditText(){
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutView = null;
		if(labelPosition==0){
			layoutView = inflater.inflate(R.layout.labeledittext_horizontal, this, true);
		}else if(labelPosition==1){
			layoutView = inflater.inflate(R.layout.labeledittext_vertical, this, true);
		}
		this.textView = (TextView) layoutView.findViewById(R.id.textView);
		this.editText = (EditText) layoutView.findViewById(R.id.editText);
		this.textView.setText(labelText);
		this.textView.setTextSize(labelSize);
		
		this.editText.setTransformationMethod(new CharLowerToCapital());
		//this.textView.setTextColor(labelColor);
	}

	public String getLabelText() {
		return this.textView.getText().toString();
	}

	public float getLabelSize() {
		return this.textView.getTextSize();
	}

	public int getLabelPosition() {
		return labelPosition;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
		this.textView.setText(labelText);
	}

	public void setLabelSize(float labelSize) {
		this.labelSize = labelSize;
		this.textView.setTextSize(labelSize);
	}

	public void setLabelPosition(int labelPosition) {
		this.labelPosition = labelPosition;
	}
	
	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
		this.textView.setText(labelColor);
	}

}
