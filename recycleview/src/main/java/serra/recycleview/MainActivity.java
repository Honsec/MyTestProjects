package serra.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview =(RecyclerView) findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));

        recyclerview.setItemAnimator(new DefaultItemAnimator());

        List<String > strings=new ArrayList<String >();

        int i=0;
        while(i++<10){

            strings.add(i+"");
        }

        final MyAdapter myAdapter=new MyAdapter(this,R.layout.item_recyclerview,recyclerview);
        myAdapter.setDatas(strings);
        myAdapter.setOnItemClickLitener(new Recy_Adapter2.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, int pos) {
                Log.i("onItemClick","position:"+position);
                myAdapter.addData(position,"aa");

            }
        });
    }



    class MyAdapter extends Recy_Adapter2<String>{
        public MyAdapter(Context context, int Layout_id) {
            super(context, Layout_id);
        }

        public MyAdapter(Context context, int Layout_id, RecyclerView mRecyclerView) {
            super(context, Layout_id, mRecyclerView);
        }

        @Override
        public void onBindViewHold(Holder holder, int position, String data) {
            Log.i("onBindViewHold","position:"+position);
            Log.i("onBindViewHold","LayoutPosition:"+holder.getLayoutPosition());
            holder.setText(R.id.item_text, getDatas().get(position) + "");

        }

        @Override
        public void setHolderViews(Holder holder) {
                holder.getView(R.id.item_text);
        }

    }


}
