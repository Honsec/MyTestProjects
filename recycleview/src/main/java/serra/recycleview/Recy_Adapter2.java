package serra.recycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Hongsec
 *
 * @param <T>
 */
public abstract class Recy_Adapter2<T> extends RecyclerView.Adapter<Recy_Adapter2.Holder> {
	
	private List<T> datas;
	private Context mContext;
	private LayoutInflater inflate;
	private int layout_id;

	public Recy_Adapter2(Context context,int Layout_id){
		this(context,Layout_id,null);
	}




	public Recy_Adapter2(Context context,int Layout_id, RecyclerView mRecyclerView) {
		if(datas==null)datas=new ArrayList<T>();
		this.mContext=context;
		this.inflate=LayoutInflater.from(context);
		this.layout_id=Layout_id;

		if(mRecyclerView!=null){

			//use default
			mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
			mRecyclerView.setAdapter(this);
			mRecyclerView.setItemAnimator(new DefaultItemAnimator());
			//mRecyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));

		}

	}
	
	public void setDatas(List<T> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	public List<T> getDatas() {
		return datas;
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}
	
	public void addData(int position,T a)
	{
		datas.add(position, a);
		notifyItemInserted(position);
//		notifyDataSetChanged();
	}


	public void removeData(int position)
	{
		datas.remove(position);
		notifyItemRemoved(position);
//		notifyDataSetChanged();
	}
	
	@Override
	public void onBindViewHolder(final Holder holder, final int position) {
		//set  ...
		final int pos= holder.getLayoutPosition();
		if(OnItemClickLitener!=null){
			holder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					OnItemClickLitener.onItemClick(v, position,pos);
				}
			});
		}
		if(onItemLongClickListener!=null){
			holder.itemView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					onItemLongClickListener.onItemLongClick(v, position,pos);
					return false;
				}
			});
		}
		onBindViewHold(holder,position,datas.get(position));
	}
	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int arg1) {
		Holder<T> holder=new Holder<T>(inflate.inflate(layout_id,parent,false));
		return holder;
	}
	private OnItemClickLitener OnItemClickLitener;
	public interface OnItemClickLitener
	{
		/**
		 * 
		 * @param view
		 * @param position  用来使用view的时候使用
		 * @param pos  用来增删时 使用
		 */
		void onItemClick(View view, int position,int pos);
	}
	private onItemLongClickListener onItemLongClickListener;
	public interface onItemLongClickListener
	{	
		/**
		 * 
		 * @param view
		 * @param position  用来使用view的时候使用
		 * @param pos  用来增删时 使用
		 */
		void onItemLongClick(View view , int position,int pos);
	}
	public void setOnItemClickLitener(OnItemClickLitener OnItemClickLitener) {
		this.OnItemClickLitener = OnItemClickLitener;
	}
	public void setOnItemLongClickListener(
			onItemLongClickListener onItemLongClickListener) {
		this.onItemLongClickListener = onItemLongClickListener;
	}
	/**绑定数据实现**/
	public abstract void onBindViewHold(Holder holder,int position,T data);
	/**设置要添加的view  （findview）**/
	public abstract void setHolderViews(Holder holder);
	
	class Holder<T> extends ViewHolder{
		private final SparseArray<View> mViews;
		private View content;
		public Holder(View content) {
			super(content);
			this.mViews=new SparseArray<View>();
			this.content=content;
			setHolderViews(this);
		}
		
		//get view
		public <T extends View>T getView(int viewId){
			View view=mViews.get(viewId);
			if(view==null){
				view=content.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return (T)view;
		}
		
		public Holder setView(int viewId){
			View view=mViews.get(viewId);
			if(view==null){
				view=content.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return this;
		}
		
		public  TextView getTextView(int viewId){
			View view=getView(viewId);
			if(view instanceof TextView)
				return ((TextView)view);
			else{
				throw new UnsupportedOperationException("Only support TextView");
			}
		}
		
		public ImageView getImageView(int viewId){
			View view=getView(viewId);
			if(view instanceof ImageView)
				return ((ImageView)view);
			else{
				throw new UnsupportedOperationException("Only support ImageView");
			}
		}
		
		public Holder setText(int viewId,String txt){
			getTextView(viewId).setText(txt);
			return this;
		}
		public Holder setImageResource(int viewId,int source){
			getImageView(viewId).setImageResource(source);
			return this;
		}
		public Holder setImageBitmap(int viewId,Bitmap bitmap){
			getImageView(viewId).setImageBitmap(bitmap);
			return this;
		}
		public Holder setCheck(int viewId,boolean bool){
			View view=getView(viewId);
			if(view instanceof CompoundButton){
				((CompoundButton)view).setChecked(bool);
			}
			return this;
		}
		public Holder setVisibility(int viewid,int visiable){
			View view=getView(viewid);
			view.setVisibility(visiable);
			return this;
		}
		
	}
}
