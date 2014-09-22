package com.jd.a6i.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.a6i.R;


public class DropDownView extends RelativeLayout implements View.OnFocusChangeListener,View.OnClickListener,OnItemClickListener{
	private LayoutInflater layoutInflater;
	private DropAdapter dropAdapter;
	private List<String> dropList;
	private String editContent;
	
	private EditText editText;
	private ImageButton imageArrow;
	private RelativeLayout layoutDrop;
	private PopupWindow popupWindowDrop;
	private ListView listViewDrop;
	
	public String editHint;
	public int editStyle;
	public Drawable editLeftDrawable;
	public Drawable editTopDrawable;
	public Drawable editRightDrawable;
	public Drawable editBottomDrawable;
	public Drawable dropArrowDrawable;
	public boolean dropEditable;
	
	private boolean isChecked;
	private int dropArrowResourceId;
	private OnDropDownItemListener onDropDownItemListener;
	
	private Activity activity;
	
	public DropDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttributeSet(attrs);
		initDropDownView();
	}

	public DropDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttributeSet(attrs);
		initDropDownView();
	}

	public DropDownView(Context context) {
		super(context);
		initDropDownView();
	}
	
	private void initAttributeSet(AttributeSet attrs){
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DropDownView);
		this.editHint = typedArray.getString(R.styleable.DropDownView_editHint);
		this.editStyle = typedArray.getResourceId(R.styleable.DropDownView_editStyle, 0);
		
		int editLeftResourceId = typedArray.getResourceId(R.styleable.DropDownView_editLeftDrawable, 0);
		if(editLeftResourceId!=0){
			Bitmap editLeftBitmap = BitmapFactory.decodeResource(getResources(), editLeftResourceId);
			this.editLeftDrawable = new BitmapDrawable(getResources(), editLeftBitmap);
		}else{
			this.editLeftDrawable = null;
		}
		
		int editTopResourceId = typedArray.getResourceId(R.styleable.DropDownView_editTopDrawable, 0);
		if(editTopResourceId!=0){
			Bitmap editTopBitmap = BitmapFactory.decodeResource(getResources(), editTopResourceId);
			this.editTopDrawable = new BitmapDrawable(getResources(), editTopBitmap);
		}else{
			this.editTopDrawable = null;
		}
		
		int editRightResourceId = typedArray.getResourceId(R.styleable.DropDownView_editRightDrawable, 0);
		if(editRightResourceId!=0){
			Bitmap editRightBitmap = BitmapFactory.decodeResource(getResources(), editRightResourceId);
			this.editRightDrawable = new BitmapDrawable(getResources(), editRightBitmap);
		}else{
			this.editRightDrawable = null;
		}
		
		int editBottomResourceId = typedArray.getResourceId(R.styleable.DropDownView_editBottomDrawable, 0);
		if(editBottomResourceId!=0){
			Bitmap editBottomBitmap = BitmapFactory.decodeResource(getResources(), editBottomResourceId);
			this.editBottomDrawable = new BitmapDrawable(getResources(), editBottomBitmap);
		}else{
			this.editBottomDrawable = null;
		}
		
		this.dropEditable = typedArray.getBoolean(R.styleable.DropDownView_dropEditable, true);
		
		dropArrowResourceId = typedArray.getResourceId(R.styleable.DropDownView_dropArrowDrawable, 0);
	}
	
	private void initDropDownView(){
		if(layoutInflater==null){
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View dropDownView = layoutInflater.inflate(R.layout.jd_drop_down, this, true);
		
		this.layoutDrop = (RelativeLayout) dropDownView.findViewById(R.id.layoutDrop);
		this.editText = (EditText) dropDownView.findViewById(R.id.editText);
		this.imageArrow = (ImageButton) dropDownView.findViewById(R.id.imageArrow);
		
		this.editText.setHint(editHint);
		this.editText.setTextAppearance(getContext(), editStyle);
		this.editText.setEnabled(dropEditable);
		setCompoundDrawables(this.editText, editLeftDrawable, editTopDrawable, editRightDrawable, editBottomDrawable);
		this.imageArrow.setBackgroundResource(dropArrowResourceId);
		
		this.editText.setOnFocusChangeListener(this);
		this.imageArrow.setOnFocusChangeListener(this);
		
		this.imageArrow.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.editText||v.getId()==R.id.imageArrow){
			editText.requestFocus();
			//boolean hasFocus = v.hasFocus();
			//dropDownViewFocus(hasFocus);
			popupWindowDrop().showAsDropDown(layoutDrop);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(v.getId()==R.id.editText||v.getId()==R.id.imageArrow){
			dropDownViewFocus(hasFocus);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (dropList!=null) {
			String editContent = dropList.get(position);
			this.editContent = editContent;
			editText.setText(editContent);
			editText.setSelection(editContent.length());
			if(onDropDownItemListener==null){
				popupWindowDrop().dismiss();
			}else{
				boolean isDissmiss = onDropDownItemListener.onClickItem(view, position, editContent);
				if(!isDissmiss){
					popupWindowDrop().dismiss();
				}
			}
			
		}
	}
	
	protected void dropDownViewFocus(boolean hasFocus){
		if(hasFocus){
			layoutDrop.setBackgroundResource(R.drawable.jd_corner_round_focuse);
		}else{
			layoutDrop.setBackgroundResource(R.drawable.jd_corner_round_normal);
		}
	}
	
	public void setOnDropDownItemListener(OnDropDownItemListener onDropDownItemListener){
		this.onDropDownItemListener = onDropDownItemListener;
	}
	
	private PopupWindow popupWindowDrop(){
		if(listViewDrop==null){
			listViewDrop = new ListView(getContext());
		}
		if(dropAdapter==null){
			dropAdapter = new DropAdapter(getContext());
		}
		dropAdapter.setDropList(getDropList());
		listViewDrop.setAdapter(dropAdapter);
		listViewDrop.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		if(isChecked()){
			listViewDrop.setSelection(itemPosition(editText.getText().toString()));
		}
		listViewDrop.setOnItemClickListener(this);
		if(popupWindowDrop==null){
			popupWindowDrop = new PopupWindow(listViewDrop,layoutDrop.getWidth(), 5*editText.getHeight(), true);
		}
		popupWindowDrop.setBackgroundDrawable(new BitmapDrawable(getContext().getResources()));
		popupWindowDrop.setFocusable(true);
		popupWindowDrop.update();
		return popupWindowDrop;
	}
	
	protected int itemPosition(String editContent){
		if(dropList!=null){
			if(dropList.contains(editContent)){
				return dropList.indexOf(editContent);
			}
		}
		return 0;
	}

	public List<String> getDropList() {
		return dropList;
	}

	public void setDropList(List<String> dropList) {
		this.dropList = dropList;
	}

	public ListView getListViewDrop(){
		return listViewDrop;
	}
	
	public ImageButton getImageArrow(){
		return imageArrow;
	}
	
	public EditText getEditText(){
		return editText;
	}
	
	/**
	 * @param context
	 * @param textView
	 * @param leftDrawable
	 * @param topDrawable
	 * @param rightDrawable
	 * @param bottomDrawable
	 */
	protected void setCompoundDrawables(EditText editText,
										Drawable leftDrawable, 
										Drawable topDrawable,
										Drawable rightDrawable, 
										Drawable bottomDrawable) {
		if (leftDrawable != null) {
			leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
		}
		if (topDrawable != null) {
			topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		}
		if (rightDrawable != null) {
			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
		}
		if (bottomDrawable != null) {
			bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
		}
		editText.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
	}
	
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getEditContent(){
		return editContent;
	}
	
	public boolean isDropEditable() {
		return dropEditable;
	}

	public void setDropEditable(boolean dropEditable) {
		this.dropEditable = dropEditable;
	}

	/*********************************************************************************************************/
	class DropAdapter extends BaseAdapter{
		private Context context;
		private List<String> dropList;
		public DropAdapter(Context context){
			this.context = context;
		}
		@Override
		public int getCount() {
			return dropList==null?0:dropList.size();
		}

		@Override
		public Object getItem(int position) {
			return dropList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public int getViewTypeCount() {
			if(dropList==null||dropList.size()==0){
				return 1;
			}
			return dropList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				if(layoutInflater==null){
					layoutInflater = LayoutInflater.from(context);
				}
				convertView = layoutInflater.inflate(R.layout.jd_drop_item, null);
			}
			TextView textContent = (TextView) convertView.findViewById(R.id.textContent);
			String content = dropList.get(position);
			textContent.setText(content);
			return convertView;
		}
		public List<String> getDropList() {
			return dropList;
		}
		public void setDropList(List<String> dropList) {
			this.dropList = dropList;
		}
	}
	/*******************************************************************************************************/
	public interface OnDropDownItemListener{
		public boolean onClickItem(View view,int position,String itemContent);
	}
}
