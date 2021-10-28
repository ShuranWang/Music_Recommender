package au.edu.unsw.infs3634.musicrecommender.activitys;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.musicrecommender.DataListAdapter;
import au.edu.unsw.infs3634.musicrecommender.R;
import au.edu.unsw.infs3634.musicrecommender.base.BaseActivity;
import au.edu.unsw.infs3634.musicrecommender.beans.ListBean;
import au.edu.unsw.infs3634.musicrecommender.utils.StringUtil;
import au.edu.unsw.infs3634.musicrecommender.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;

    private List<ListBean> list = new ArrayList<>();
    private List<ListBean> list_all = new ArrayList<>();
    private BaseQuickAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        tvTitle.setText("Search");
        tvTitleLeft.setText("Back");
        initList();
        initAdapter();
    }

    private void initList() {
        String[] str_name = new String[]{"See You Again", "Let It Go", "My Heart Will Go On", "Half the World Away",
                "easy on me", "red eye", "Treat It Like", "Starlight", "Long Way Off", "Hi-Lo"};
        String[] str_pic = new String[]{"https://bkimg.cdn.bcebos.com/pic/8b13632762d0f703ab0c4d230efa513d2797c597?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UyMjA=,g_7,xp_5,yp_5/format,f_auto",
                "https://bkimg.cdn.bcebos.com/pic/bd315c6034a85edf7cd76d574f540923dd54756f?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UyMjA=,g_7,xp_5,yp_5/format,f_auto",
                "https://bkimg.cdn.bcebos.com/pic/810a19d8bc3eb135622dd81ba51ea8d3fc1f44a3?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto",
                "https://bkimg.cdn.bcebos.com/pic/0d338744ebf81a4c387801a6dc2a6059252da613?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxNTA=,g_7,xp_5,yp_5/format,f_auto",
                "https://bkimg.cdn.bcebos.com/pic/5243fbf2b2119313b07e6186c4681bd7912397dd27fe?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto",
                "https://bkimg.cdn.bcebos.com/pic/f9198618367adab4945f07fa8cd4b31c8601e4f8?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxNTA=,g_7,xp_5,yp_5/format,f_auto",
                "https://wx4.sinaimg.cn/large/001LtKihly4gvg39t4jifj60u00u0n0q02.jpg",
                "https://bkimg.cdn.bcebos.com/pic/5882b2b7d0a20cf44ef927e870094b36acaf9998?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxODA=,g_7,xp_5,yp_5/format,f_auto",
                "https://y.qq.com/music/photo_new/T002R300x300M000003ZRArm0MGA3G_1.jpg?max_age=2592000",
                "https://img4.kuwo.cn/star/albumcover/500/63/17/1519297623.jpg"};
        String[] str_singer = new String[]{"Marc Klasfeld", "Verse", "Titanic：Music from the Motion Picture", "OASIS", "Easy on Me", "Chris Bender", "Dee Goodz", "Taylor Swift", "Danny Oertli", "JJ.johnson"};
        String[] str_score = new String[]{"5.0", "4.8", "4.9", "4.6", "5.0", "4.3", "4.1", "4.7", "4.2", "4.0"};
        String[] str_cate = new String[]{"嘻哈/流行", "流行", "流行", "流行", "流行", "流行", "流行", "Electronic Country", "New Age", "英文"};
        String[] str_url = new String[]{"http://music.163.com/song/media/outer/url?id=1888598037.mp3",
                "http://music.163.com/song/media/outer/url?id=865492223.mp3",
                "http://music.163.com/song/media/outer/url?id=30373203.mp3",
                "http://music.163.com/song/media/outer/url?id=28563132.mp3",
                "http://music.163.com/song/media/outer/url?id=1887190390.mp3",
                "http://music.163.com/song/media/outer/url?id=1885130659.mp3",
                "http://music.163.com/song/media/outer/url?id=1822718545.mp3",
                "http://music.163.com/song/media/outer/url?id=1329943008.mp3",
                "http://music.163.com/song/media/outer/url?id=1885112631.mp3",
                "http://music.163.com/song/media/outer/url?id=1883278776.mp3"};

        for (int i = 0; i < str_name.length; i++) {
            ListBean bean = new ListBean();
            bean.name = str_name[i];
            bean.pic = str_pic[i];
            bean.people = str_singer[i];
            bean.score = str_score[i];
            bean.cate = str_cate[i];
            bean.url = str_url[i];
            list_all.add(bean);
        }
    }

    private void initAdapter() {
        adapter = new DataListAdapter(list);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                showActivity(SearchActivity.this, MusicActivity.class, bundle);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                showActivity(SearchActivity.this, MusicActivity.class, bundle);
            }
        });
    }


    private void onSearch(String content) {
        list.clear();
        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).name.contains(content) || list_all.get(i).people.contains(content) || list_all.get(i).cate.contains(content) || list_all.get(i).score.contains(content)) {
                list.add(list_all.get(i));
            }
        }

        if (list.size() == 0) {
            ToastUtil.showToast(this, "No Data");
        }

        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.tv_title_left, R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_left:
                onBackPressed();
                break;
            case R.id.bt_search:
                if (StringUtil.isEmpty(edtSearch.getText().toString())) {
                    ToastUtil.showToast(this, "Please Input");
                    return;
                }
                String content = edtSearch.getText().toString();
                onSearch(content);
                break;
        }
    }
}
