package com.example.duanwu.demo_p21;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParticularsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imags;
    private Button shang;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        initView();
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("imagePath");

    }

    private void initView() {
        imags = (ImageView) findViewById(R.id.imags);
        shang = (Button) findViewById(R.id.shang);

        shang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shang:
               uploadImage();

                break;
        }
    }
       private void uploadImage() {
                 //手机APP的权限，清单文件的权限，修改文件的路径和key，value值
               File file = new File(/*Utils.getSDPath() +"/k.jpg"*/imagePath);
               if (file.exists()) {
                   //上传文件的格式
                   String format = "image/jpg"; //image/jpg  image/gif multipart/form-data
                   RequestBody request = RequestBody.create(MediaType.parse(format), file);
                   //设置上传文件的内容
                   RequestBody body = new MultipartBody.Builder()
                           .setType(MultipartBody.FORM)
                           .addFormDataPart("key", "image1806b")  //传参数
                           .addFormDataPart("file", file.getName(), request)
                           .build();
                   OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                   //设置上传地址和内容
                   Request request1 = new Request.Builder()
                           .url("http://yun918.cn/study/public/file_upload.php")
                           .post(body)
                           .build();
                   Call call = okHttpClient.newCall(request1);
                   call.enqueue(new Callback() {
                       @Override
                       public void onFailure(Call call, IOException e) {

                       }

                       @Override
                       public void onResponse(Call call, Response response) throws IOException {
                           String result = response.body().string();
                           final ImageBean imageBean = new Gson().fromJson(result, ImageBean.class);
                           if (imageBean.getCode() == 200) {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       updateImageView(imageBean);
                                   }
                               });
                           }
                           Log.i("result", result);
                       }
                   });
               }
           }

           //更新本地imageview
           private void updateImageView(ImageBean imageBean) {
               //txt_url.setText(imageBean.getData().getUrl());
               RequestOptions options = new RequestOptions().circleCrop();
               Glide.with(this).load(imageBean.getData().getUrl()).apply(options).into(imags);
           }
}
