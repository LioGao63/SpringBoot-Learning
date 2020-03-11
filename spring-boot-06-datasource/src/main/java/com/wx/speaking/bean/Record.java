package com.wx.speaking.bean;

import com.wx.speaking.utils.FileUtil;
import com.wx.speaking.utils.HttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Record {
    // 合成webapi接口地址
    private static final String WEBISE_URL = "https://api.xfyun.cn/v1/service/v1/ise";
    // 应用ID
    private static final String APPID = "5e06ebad";
    // 接口密钥
    private static final String API_KEY = "a6963cb34d0abadb7d1722df6e53f915";
    // 音频编码
    private static final String AUE = "raw";
    // 采样率
    private static final String AUF = "audio/L16;rate=16000";
    // 结果级别
    private static final String RESULT_LEVEL = "entirety";
    // 语种
    private static final String LANGUAGE = "en_us";
    // 评测种类
    private static String CATEGORY = "";
    // 音频文件地址
    private static String AUDIO_PATH = "";
    // 评测文本
    private static String TEXT = "";

    /**
     * 评测 WebAPI 调用示例程序
     */
    public static void callApi(String category, String audioPath, String text) throws IOException {
        CATEGORY = category;
        AUDIO_PATH = audioPath;
        TEXT = text;
        Map<String, String> header = buildHttpHeader(CATEGORY);
        byte[] audioByteArray = FileUtil.read(AUDIO_PATH);
        String audioBase64 = new String(Base64.encodeBase64(audioByteArray), "UTF-8");
        String result = HttpUtil.doPost1(WEBISE_URL, header, "audio=" + URLEncoder.encode(audioBase64, "UTF-8") + "&text=" + URLEncoder.encode(TEXT, "UTF-8"));
        System.out.println("评测 WebAPI 接口调用结果：" + result);

    }

    /**
     * 组装http请求头
     */
    private static Map<String, String> buildHttpHeader(String category) throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"result_level\":\"" + RESULT_LEVEL + "\",\"language\":\"" + LANGUAGE + "\",\"category\":\"" + CATEGORY + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }
}
