package org.learningequality.androidapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
* Created by Eli on 10/12/14.
*/
public class CustomTouchLayout extends RelativeLayout {
  //  private RelativeLayout root_list;
    int menu_width = 200;
    LayoutInflater mInflater;

    private MyObservable observe_id = MyObservable.get_id_tracker();

    public CustomTouchLayout(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public CustomTouchLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public CustomTouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public void init()
    {
       // root_list = NodeListActivity.root_list;
        //mInflater.inflate(R.layout.custom_view, this, true);
//        TextView tv = (TextView) v.findViewById(R.id.textView1);
//        tv.setText(" Custom RelativeLayout");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev); // With this i tell my layout to consume all the touch events from its childs
    }

    float mPreviousX = 0;
    float mCurrentX = 0;
    float moving;
    int id;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        id = observe_id.get_tracked_id();
        mCurrentX = e.getRawX(); //important! use getRawX() which returns the absolute coordinates, relative to the device screen.
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float i = mCurrentX - mPreviousX;
                if(NodeListActivity.root_list.getX() < 0 || i < 0) {
                    if(NodeListActivity.root_list.getX() > -menu_width*(id-1) || i > 0) {
                        moving += i;
                        if(moving > 0){
                            NodeListActivity.root_list.setX(0);
                        }else if(moving < -menu_width*(id-1)) {
                            NodeListActivity.root_list.setX(-menu_width*(id-1));
                        }else{
                            NodeListActivity.root_list.setX(moving);
                        }
                    }
                }
                break;
        }
        mPreviousX = mCurrentX;
        return false;
    }
}
