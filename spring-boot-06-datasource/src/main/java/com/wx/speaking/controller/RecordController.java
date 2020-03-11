package com.wx.speaking.controller;

import com.wx.speaking.bean.Record;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
public class RecordController {

    @PostMapping("/sendResult")
    public void sendResult(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        String savePath = "F:\\J2EE\\speaking\\src\\main\\resources\\record";


        String id = request.getParameter("id");
        String password = request.getParameter("password");

        //获取上传文件
        MultipartFile recordFile = req.getFile("wx_record");
        System.out.println(id);
        System.out.println(password);

        String desFile = savePath + recordFile.getOriginalFilename();

        File file = new File(desFile);
        recordFile.transferTo(file);
        System.out.println(file);

        String textPath = "[word]abide";
        Record.callApi("read_word", file.toString(), textPath);

    }
}
