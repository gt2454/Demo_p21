package com.example.duanwu.demo_p21;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapater  extends RecyclerView.Adapter {
    Context  context;
    List<Bean.DataBean.DatasBean> lista=new ArrayList<>();

    public Adapater(Context context, List<Bean.DataBean.DatasBean> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=null;
        RecyclerView.ViewHolder holder=null;
        if(i==1){
            v= LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
            holder=new MyHolder(v);
        }
        if(i==2){
            v= LayoutInflater.from(context).inflate(R.layout.items,viewGroup,false);
            holder=new MyHolder2(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        if(itemViewType==1){
            MyHolder holder1= (MyHolder) viewHolder;
            holder1.textView.setText(lista.get(i).getTitle());
            Glide.with(context).load(lista.get(i).getEnvelopePic()).into(holder1.imageView);
        }
        if(itemViewType==2){
            MyHolder2 holder1= (MyHolder2) viewHolder;
            Glide.with(context).load(lista.get(i).getEnvelopePic()).into(holder1.imageView2);
        }

          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if(mListener!=null){
                             mListener.onItemClick(v,i);

                         }
                     }
                 });


    }
    private OnItemCLickListener mListener;
    public interface OnItemCLickListener{
        void onItemClick(View v,int position);
        // void onLongClick(View v,int  position);
    }

    public void setOnItemCLickListener(OnItemCLickListener listener){
        this.mListener = listener;
    }
    @Override
    public int getItemCount() {
        return lista.size();
    }
    class   MyHolder  extends  RecyclerView.ViewHolder{

        private final ImageView imageView;
        private final TextView textView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

        }
    }
    class   MyHolder2  extends  RecyclerView.ViewHolder{

        private ImageView imageView2;

        public MyHolder2(@NonNull View itemView) {
            super(itemView);
           imageView2= itemView.findViewById(R.id.image2);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position%6==0){
            return  2;

        }else{
            return 1;
        }
    }
}
