package com.example.holawith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.holawith.Mychatroom.R;
import com.holawith.notebook.NoteBookItem;
import com.holawith.notebook.NoteBookadapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class IndexActivity extends Activity {

	LinearLayout layoutIndex;
	/** 锟斤拷母锟斤拷锟斤拷锟� */
	private String[] str = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "U", "V", "W", "X", "Y",
			"Z" };

	int height;// 锟斤拷锟斤拷叨锟�
	List<NoteBookItem> listData;
	private ListView listView;
	NoteBookadapter adapter;
	private TextView tv_show;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
		layoutIndex.setBackgroundColor(Color.rgb(255, 255, 255));

		getData();
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new NoteBookadapter(this, listData, this.str);
		listView.setAdapter(adapter);
		tv_show = (TextView) findViewById(R.id.tv);
		tv_show.setVisibility(View.INVISIBLE);

	}

	public void getData() {
		listData = new ArrayList<NoteBookItem>();

		NoteBookItem n1 = new NoteBookItem();
		n1.call = "锟斤拷锟斤拷";
		n1.name = "allen";
		n1.mobile = "18217594856";
		n1.index = String.valueOf(Pinyin4j.getHanyuPinyin(n1.name).charAt(0));
		listData.add(n1);

		NoteBookItem n2 = new NoteBookItem();
		n2.call = "锟斤拷锟斤拷师";
		n2.name = "android";
		n2.mobile = "13658974521";
		n2.index = String.valueOf(Pinyin4j.getHanyuPinyin(n2.name).charAt(0));
		listData.add(n2);

		NoteBookItem n3 = new NoteBookItem();
		n3.call = "锟斤拷锟斤拷";
		n3.name = "波风水门";
		n3.mobile = "13658974521";
		n3.index = String.valueOf(Pinyin4j.getHanyuPinyin(n3.name).charAt(0));
		listData.add(n3);

		NoteBookItem n4 = new NoteBookItem();
		n4.call = "锟斤拷师";
		n4.name = "ccccc";
		n4.number = "021-25635784";
		n4.index = String.valueOf(Pinyin4j.getHanyuPinyin(n4.name).charAt(0));
		listData.add(n4);

		NoteBookItem n5 = new NoteBookItem();
		n5.call = "锟酵凤拷";
		n5.name = "ddddd";
		n5.number = "010-25635784";
		n5.index = String.valueOf(Pinyin4j.getHanyuPinyin(n5.name).charAt(0));
		listData.add(n5);

		NoteBookItem n6 = new NoteBookItem();
		n6.call = "锟酵凤拷";
		n6.name = "bruth";
		n6.number = "010-25635784";
		n6.index = String.valueOf(Pinyin4j.getHanyuPinyin(n6.name).charAt(0));
		listData.add(n6);

		NoteBookItem n7 = new NoteBookItem();
		n7.call = "锟斤拷锟斤拷";
		n7.name = "eee";
		n7.number = "010-25635784";
		n7.index = String.valueOf(Pinyin4j.getHanyuPinyin(n7.name).charAt(0));
		listData.add(n7);

		NoteBookItem n8 = new NoteBookItem();
		n8.call = "锟酵凤拷";
		n8.name = "mary";
		n8.number = "010-25635784";
		n8.index = String.valueOf(Pinyin4j.getHanyuPinyin(n8.name).charAt(0));
		listData.add(n8);

		NoteBookItem n9 = new NoteBookItem();
		n9.call = "锟酵凤拷";
		n9.name = "fffff";
		n9.number = "010-25635784";
		n9.index = String.valueOf(Pinyin4j.getHanyuPinyin(n9.name).charAt(0));
		listData.add(n9);

		NoteBookItem n10 = new NoteBookItem();
		n10.call = "锟酵凤拷";
		n10.name = "gggg";
		n10.number = "010-25635784";
		n10.index = String.valueOf(Pinyin4j.getHanyuPinyin(n10.name).charAt(0));
		listData.add(n10);

		NoteBookItem n11 = new NoteBookItem();
		n11.call = "锟酵凤拷";
		n11.name = "jhhh";
		n11.number = "010-25635784";
		n11.index = String.valueOf(Pinyin4j.getHanyuPinyin(n11.name).charAt(0));
		listData.add(n11);

		Collections.sort(listData, new ListComparator());
	}

	class ListComparator implements Comparator<NoteBookItem> {

		@Override
		public int compare(NoteBookItem arg0, NoteBookItem arg1) {
			// TODO Auto-generated method stub
			return String.valueOf(Pinyin4j.getHanyuPinyin(arg0.name).charAt(0))
					.compareTo(
							String.valueOf(Pinyin4j.getHanyuPinyin(arg1.name)
									.charAt(0)));
		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// 锟斤拷oncreate锟斤拷锟斤拷执锟斤拷锟斤拷锟斤拷拇锟斤拷锟矫伙拷锟接︼拷锟斤拷锟轿猳ncreate锟斤拷锟斤拷玫锟斤拷锟絞etHeight=0
		System.out
				.println("layoutIndex.getHeight()=" + layoutIndex.getHeight());
		height = layoutIndex.getHeight() / str.length;
		getIndexView();
	}

	/** 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷 */
	public void getIndexView() {
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, height);
		// params.setMargins(10, 5, 10, 0);
		for (int i = 0; i < str.length; i++) {
			final TextView tv = new TextView(this);
			tv.setLayoutParams(params);
			tv.setText(str[i]);
			// tv.setTextColor(Color.parseColor("#606060"));
			// tv.setTextSize(16);
			tv.setPadding(10, 0, 10, 0);
			layoutIndex.addView(tv);
			layoutIndex.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event)

				{
					float y = event.getY();
					int index = (int) (y / height);
					if (index > -1 && index < str.length) {// 锟斤拷止越锟斤拷
						String key = str[index];
						if (adapter.getSelector().containsKey(key)) {
							int pos = adapter.getSelector().get(key);
							if (listView.getHeaderViewsCount() > 0) {// 锟斤拷止ListView锟叫憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷没锟叫★拷
								listView.setSelectionFromTop(
										pos + listView.getHeaderViewsCount(), 0);
							} else {
								listView.setSelectionFromTop(pos, 0);// 锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷
							}
							tv_show.setVisibility(View.VISIBLE);
							tv_show.setText(str[index]);
						}
					}
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#606060"));
						break;

					case MotionEvent.ACTION_MOVE:

						break;
					case MotionEvent.ACTION_UP:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#00ffffff"));
						tv_show.setVisibility(View.INVISIBLE);
						break;
					}
					return true;
				}
			});
		}
	}

}
