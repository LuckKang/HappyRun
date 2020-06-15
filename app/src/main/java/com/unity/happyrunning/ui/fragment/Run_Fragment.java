package com.unity.happyrunning.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.unity.happyrunning.R;
import com.unity.happyrunning.base.BasicFragment;
import com.unity.happyrunning.commons.bean.SportMotionRecord;
import com.unity.happyrunning.commons.utils.LogUtils;
import com.unity.happyrunning.commons.utils.Status_sp;
import com.unity.happyrunning.db.DataManager;
import com.unity.happyrunning.db.RealmHelper;
import com.unity.happyrunning.ui.activity.SportMapActivity;
import com.unity.happyrunning.ui.permission.PermissionHelper;
import com.unity.happyrunning.ui.permission.PermissionListener;
import com.unity.happyrunning.ui.permission.Permissions;

import java.text.DecimalFormat;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Run_Fragment extends BasicFragment {

    TextView tvSportMile;

    TextView tvSportCount;

    TextView tvSportTime;

    Button btStart;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private final int SPORT = 0x0012;

    private DataManager dataManager = new DataManager(new RealmHelper());

    /*
    区别：
        1. Fragment中onCreate类似于Activity.onCreate，在其中可初始化除了view之外的一切；
        2. onCreateView是创建该fragment对应的视图，其中需要创建自己的视图并返回给调用者；
        3. super.onCreateView无所谓是否有调用，因为super.onCreateView直接返回null。
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);
        tvSportMile = view.findViewById(R.id.tv_sport_mile);
        tvSportCount = view.findViewById(R.id.tv_sport_count);
        tvSportTime = view.findViewById(R.id.tv_sport_time);
        btStart = view.findViewById(R.id.btStart);
        upDateUI();
        return view;
    }


    private void upDateUI() {
        try {
            List<SportMotionRecord> records = dataManager.queryRecordList(Integer.parseInt(SPUtils.getInstance().getString(Status_sp.USERID, "0")));
            if (null != records) {

                double sportMile = 0;
                long sportTime = 0;
                for (SportMotionRecord record : records) {
                    sportMile += record.getDistance();
                    sportTime += record.getDuration();
                }
                tvSportMile.setText(decimalFormat.format(sportMile / 1000d));
                tvSportCount.setText(String.valueOf(records.size()));
                tvSportTime.setText(decimalFormat.format((double) sportTime / 60d));
            }
        } catch (Exception e) {
            LogUtils.e("获取运动数据失败", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case SPORT:
                upDateUI();
//                setResult(RESULT_OK);
                break;
            default:
                break;
        }
    }

    //位置权限
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper.requestPermissions(getActivity(), Permissions.PERMISSIONS_LOCATION,
                        getResources().getString(R.string.app_name) + "需要获取位置", new PermissionListener() {
                            @Override
                            public void onPassed() {
                                startActivity(SportMapActivity.class, null);
                            }
                        });
            }
        });
    }

}
