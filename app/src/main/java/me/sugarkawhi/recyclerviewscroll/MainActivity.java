package me.sugarkawhi.recyclerviewscroll;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    View activity_main;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        activity_main = findViewById(R.id.activity_main);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter<ItemHolder>() {
            @Override
            public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.item_image, parent, false);
                return new ItemHolder(view);
            }

            @Override
            public void onBindViewHolder(ItemHolder holder, int position) {
                if (position % 2 == 0)
                    holder.textView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                else
                    holder.textView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            }

            @Override
            public int getItemCount() {
                return 15;
            }
        });

//        recyclerView.smoothScrollBy(0, 200);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getRecyclerIndexViewPoint();
            }
        });
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }


    private void getRecyclerIndexViewPoint() {
        //第一个可见的View是position=0
        int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition != 0)
            return;

        View view = recyclerView.getChildAt(1);
        if (view != null) {
            //起始点高度
            float y = view.getY();
            Log.e(TAG, "y = " + y);
            //View高
            int measuredHeight = view.getMeasuredHeight();
            Log.e(TAG, "measuredHeight = " + measuredHeight);
            //计算
            int screenHeight = getLayoutMeasuredHeight();
            Log.e(TAG, "screenHeight = " + screenHeight);
            //需要向上滑动的距离
            float offset = ((measuredHeight / 2 + y) - screenHeight / 2);
            Log.e(TAG, "offset = " + offset);
            //
            if (offset > 0)
                recyclerView.smoothScrollBy(0, (int) offset);
            else
                Log.e(TAG, "不需要滑动");
        } else {
            Log.e(TAG, "view = null ");
        }
    }

    /**
     * 获取RecycylerView在屏幕中显示的高度
     * 不要使用屏幕的高度  因为包含了状态栏高度
     */
    private int getLayoutMeasuredHeight() {
        return activity_main.getMeasuredHeight();
    }


}
