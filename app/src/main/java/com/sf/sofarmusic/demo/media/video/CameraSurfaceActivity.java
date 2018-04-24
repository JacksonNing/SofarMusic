package com.sf.sofarmusic.demo.media.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sf.libplayer.video.SofarCamera;
import com.sf.sofarmusic.R;
import com.sf.sofarmusic.base.BaseActivity;
import com.sf.sofarmusic.util.FileUtil;
import com.sf.sofarmusic.util.FontUtil;
import com.sf.sofarmusic.util.LogUtil;

/**
 * Created by sufan on 2018/4/23.
 */

public class CameraSurfaceActivity extends BaseActivity implements SurfaceHolder.Callback {

    private TextView tv_camera;
    private Button btn_take_picture;

    private SurfaceView surface_camera;
    private SurfaceHolder holder;

    private SofarCamera mSofarCamera;
    private String picturePath;

    private int api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_camera_surface);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        tv_camera = findViewById(R.id.tv_camera);
        btn_take_picture = findViewById(R.id.btn_take_picture);
        surface_camera = findViewById(R.id.surface_camera);
    }

    public void initData() {
        picturePath = FileUtil.getPictureDir(this) + "/take_picture.jpg";
        tv_camera.setTypeface(FontUtil.setFont(this));

        api = getIntent().getIntExtra("api", 0);

        holder = surface_camera.getHolder();
        if(api==1) {
            mSofarCamera = new SofarCamera.Builder()
                    .context(this)
                    .cameraApi(SofarCamera.CAMERA1)
                    .cameraId(SofarCamera.CAMERA_BACK)
                    .holder(holder)
                    .build();
        }else{
            mSofarCamera = new SofarCamera.Builder()
                    .context(this)
                    .cameraApi(SofarCamera.CAMERA2)
                    .cameraId(SofarCamera.CAMERA_BACK)
                    .holder(holder)
                    .build();
        }

       // mSofarCamera.newBuilder().cameraId(SofarCamera.CAMERA_FRONT).build();
    }

    public void initEvent() {
        holder.addCallback(this);
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSofarCamera.switchCamera();
            }
        });
        btn_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSofarCamera.takePicture(picturePath);
            }
        });

    }

    //SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.d("surfaceCreated:holder==null:" + (holder == null));
        mSofarCamera.openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtil.d("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.d("surfaceDestroyed");
        mSofarCamera.destroyCamera();
    }
}
