package im.unico.customjcvideo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.unico.customjcvideo.R;
import im.unico.customjcvideo.adapter.MainRVAdapter;
import im.unico.customjcvideo.bean.MainBean;
import im.unico.customjcvideo.config.Config;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    private List<MainBean> data;
    private MainRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    private void loadData() {
        Type type = new TypeToken<List<MainBean>>() {
        }.getType();
        List<MainBean> temp = new Gson().fromJson(Config.DATA＿VIDEOS, type);
        data.addAll(temp);
        adapter.notifyDataSetChanged();
    }

    private void init() {
        data = new ArrayList<>();
        adapter = new MainRVAdapter(data, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(layoutManager);

        mainRecyclerView.setAdapter(adapter);

    }
}
