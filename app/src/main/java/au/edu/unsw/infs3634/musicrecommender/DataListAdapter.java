package au.edu.unsw.infs3634.musicrecommender;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import au.edu.unsw.infs3634.musicrecommender.beans.ListBean;
import au.edu.unsw.infs3634.musicrecommender.utils.StringUtil;

public class DataListAdapter extends BaseQuickAdapter<ListBean, DataListAdapter.ViewHolder> {


    public DataListAdapter(@Nullable List<ListBean> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(ViewHolder holder, ListBean bean) {

        Glide.with(mContext).load(bean.pic).into(holder.imgv_pic);

        holder.tv_name.setText(StringUtil.getContentEmpty(bean.name));
        holder.tv_people.setText("singer：" + bean.people);
        holder.tv_score.setText("score：" + bean.score);
        holder.tv_cate.setText("cate：" + bean.cate);
        holder.addOnClickListener(R.id.tv_more);
    }


    public class ViewHolder extends BaseViewHolder {
        TextView tv_name;
        ImageView imgv_pic;
        TextView tv_people;
        TextView tv_score;
        Button tv_more;
        TextView tv_cate;

        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            imgv_pic = view.findViewById(R.id.imgv_pic);
            tv_people = view.findViewById(R.id.tv_people);
            tv_score = view.findViewById(R.id.tv_score);
            tv_more = view.findViewById(R.id.tv_more);
            tv_cate = view.findViewById(R.id.tv_cate);
        }
    }

}
