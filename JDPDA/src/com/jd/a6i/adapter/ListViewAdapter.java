package com.jd.a6i.adapter;

import java.util.List;
import java.util.Map;

import com.jd.a6i.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private Context context; // ����������
	private List<Map<String, Object>> listItems; // ��Ʒ��Ϣ����
	private LayoutInflater listContainer; // ��ͼ����
	private int nFlag = 0;//��ʶ������������Ǹ���ͼ
	
    public final class ListItemView{                //�Զ���ؼ�����     
    	public TextView boxNoTv = null;
    	public TextView waybillNoTv = null;     
    }  
    
    public final class ListItemViewPart{                //�Զ���ؼ�����    
    	public TextView index = null;
    	public TextView boxNoTv = null;
    	public TextView waybillNoTv = null; 
    	public TextView mark = null;
    }  
    
	private ListItemView listItemView = null;
	private ListItemViewPart listItemViewPart = null;
	
	public ListViewAdapter(Context context,List<Map<String, Object>> listItems,int nFlag){
		this.context = context;
		this.listItems = listItems;
		this.nFlag = nFlag;
		listContainer = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (listItems==null)?0:listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (listItems==null)?null:listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (nFlag == 0) {
			if (convertView == null) {
				listItemView = new ListItemView();
				//��ȡlist_item�����ļ�����ͼ   
				convertView = listContainer
						.inflate(R.layout.listviewitem, null);
				//��ȡ�ؼ�����   
				listItemView.boxNoTv = (TextView) convertView
						.findViewById(R.id.item1);
				listItemView.waybillNoTv = (TextView) convertView
						.findViewById(R.id.item2);
				//���ÿؼ�����convertView   
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			listItemView.boxNoTv.setText(context.getResources().getString(R.string.boxNo)+String.valueOf(listItems.get(position).get(
					"boxNo")));
			listItemView.waybillNoTv.setText(context.getResources().getString(R.string.billNo)+String.valueOf(listItems.get(position)
					.get("waybillNo")));
		}
		else if(nFlag == 1){
			if (convertView == null) {
				listItemViewPart = new ListItemViewPart();
				//��ȡlist_item�����ļ�����ͼ   
				convertView = listContainer
						.inflate(R.layout.listviewpart, null);
				//��ȡ�ؼ�����   
				listItemViewPart.index = (TextView) convertView
						.findViewById(R.id.index);
				listItemViewPart.boxNoTv = (TextView) convertView
						.findViewById(R.id.boxcodepart);
				listItemViewPart.waybillNoTv = (TextView) convertView
						.findViewById(R.id.waybillpart);
				listItemViewPart.mark = (TextView) convertView
						.findViewById(R.id.mark);
				
				//���ÿؼ�����convertView   
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			
			listItemViewPart.index.setText(context.getResources().getString(R.string.indextv)+String.valueOf(listItems.get(position).get(
					"index")));
			listItemViewPart.waybillNoTv.setText(context.getResources().getString(R.string.waybilltv)+String.valueOf(listItems.get(position)
					.get("packageBarcode")));
			listItemViewPart.boxNoTv.setText(context.getResources().getString(R.string.boxNo)+String.valueOf(listItems.get(position)
					.get("boxcode")));
			listItemViewPart.mark.setText(context.getResources().getString(R.string.mark)+String.valueOf(listItems.get(position)
					.get("mark")));
		}
		return convertView;
	}

}
