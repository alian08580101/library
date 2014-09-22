package com.jd.a6i.sortingtally;

import java.util.List;

import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.db.pojo.MenuItem;

public class DifferenceQueryActivity extends JDBaseActivity {
	public DifferenceQueryActivity(){
		prepareLayout(R.layout.query_package_part_common);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText("一单多件不全查询");
	}

}
