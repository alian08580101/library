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

	private Context context; // 运行上下文
	private List<Map<String, Object>> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private int nFlag = 0;//标识这个适配器用那个视图
	
    public final class ListItemView{                //自定义控件集合     
    	public TextView boxNoTv = null;
    	public TextView waybillNoTv = null;     
    }  
    
    public final class ListItemViewPart{                //自定义控件集合    
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
				//获取list_item布局文件的视图   
				convertView = listContainer
						.inflate(R.layout.listviewitem, null);
				//获取控件对象   
				listItemView.boxNoTv = (TextView) convertView
						.findViewById(R.id.item1);
				listItemView.waybillNoTv = (TextView) convertView
						.findViewById(R.id.item2);
				//设置控件集到convertView   
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
				//获取list_item布局文件的视图   
				convertView = listContainer
						.inflate(R.layout.listviewpart, null);
				//获取控件对象   
				listItemViewPart.index = (TextView) convertView
						.findViewById(R.id.index);
				listItemViewPart.boxNoTv = (TextView) convertView
						.findViewById(R.id.boxcodepart);
				listItemViewPart.waybillNoTv = (TextView) convertView
						.findViewById(R.id.waybillpart);
				listItemViewPart.mark = (TextView) convertView
						.findViewById(R.id.mark);
				
				//设置控件集到convertView   
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
