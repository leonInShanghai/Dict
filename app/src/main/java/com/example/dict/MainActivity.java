package com.example.dict;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.example.dict.bean.TuWenBean;
import com.example.dict.utils.ChineseCharactersUtils;
import com.example.dict.utils.FileUtil;
import com.example.dict.utils.PatternUtils;
import com.example.dict.utils.RecognizeService;
import com.example.dict.utils.StatusBarFontColor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/17 Copyright © Leon. All rights reserved.
 * Functions: 中华词典的首页
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private TextView pyTv,bsTv,cyTv,twenTv,juziTv;
    private EditText ziEt;

    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    // 避免快速点击开启两个界面
    private boolean mIsFirstClick;

    @Override
    protected void onResume() {
        super.onResume();
        mIsFirstClick = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 改变改变状态栏字体颜色为黑色
        StatusBarFontColor.changeFontColorBlack(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        alertDialog = new AlertDialog.Builder(this);
        initAccessTokenWithAkSk();
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        },  getApplicationContext(),  "2suF59R8detTycwDiaffHHoT", "kjTNOz1i2rO17VKlfOoBnaPwnyoPYTxG");
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            // result是扫描图片识别出的字符串，可以将字符串传递给显示界面
                            Log.d("MainActivity", "result--> " + result);
                            TuWenBean wenBean = new Gson().fromJson(result, TuWenBean.class);
                            List<TuWenBean.WordsResultBean> wordsList = wenBean.getWords_result();
                            // 将提取到的有用的汉字存放到集合中, 传递到下一个界面
                            ArrayList<String> list = new ArrayList<>();
                            if (wordsList != null && wordsList.size() > 0) {
                                for (int i = 0; i < wordsList.size(); i++) {
                                    TuWenBean.WordsResultBean bean = wordsList.get(i);
                                    // String words = bean.getWords();
                                    String res = bean.getWords();

                                    // 剔除全部数字,字母,标点符号.
                                    // String res = PatternUtils.removeAll(words);

                                    // 将字符串每个文字都添加到集合中.
                                    for (int j = 0; j < res.length(); j++) {
                                        String s = String.valueOf(res.charAt(j));
                                        // 添加集合之前，先判断一下集合中是否已经包括这个汉字
                                        // if (!list.contains(s)) {
                                        //     list.add(String.valueOf(res.charAt(j)));
                                        // }
                                        list.add(String.valueOf(res.charAt(j)));
                                    }
                                }
                            }
                            // 判断是否识别出可显示文字
                            if (list.size() == 0) {
                                Toast.makeText(MainActivity.this, "无法识别图片中文字", Toast.LENGTH_SHORT).show();
                            } else {
                                // 跳转到显示页（IdentifyImgActivity）
                                Intent it = new Intent(MainActivity.this, IdentifyImgActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("wordlist", list);
                                it.putExtras(bundle);
                                startActivity(it);
                            }
                        }
                    });
        }
    }

    private void initView() {
        pyTv = findViewById(R.id.main_tv_pinyin);
        bsTv = findViewById(R.id.main_tv_bushou);
        cyTv = findViewById(R.id.main_tv_chengyu);
        twenTv = findViewById(R.id.main_tv_tuwen);
        juziTv = findViewById(R.id.main_tv_juzi);
        ziEt = findViewById(R.id.main_et);
    }

    /**
     * 从xml中设置的点击事件
     * @param view
     */
    public void onClick(View view) {

        Intent intent = new Intent();

        // 避免快速点击开启两个界面
        if (mIsFirstClick) {
            return;
        }
        mIsFirstClick = true;

        switch (view.getId()) {
            case R.id.main_iv_setting:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.main_iv_search:
                String text = ziEt.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    intent.setClass(this, WordInfoActivity.class);
                    intent.putExtra("zi", text);
                    startActivity(intent);
                }
                break;

            case R.id.main_tv_pinyin:
                intent.setClass(this, SearchPinyinActivity.class);
                startActivity(intent);
                break;

            case R.id.main_tv_bushou:
                intent.setClass(this, SearchBuShouActivity.class);
                startActivity(intent);
                break;

            case R.id.main_tv_chengyu:
                intent.setClass(this, SearchChengyuActivity.class);
                startActivity(intent);
                break;

            case R.id.main_tv_tuwen:
                if (!checkTokenStatus()) {
                    return;
                }
                intent.setClass(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
                break;
        }
    }
}