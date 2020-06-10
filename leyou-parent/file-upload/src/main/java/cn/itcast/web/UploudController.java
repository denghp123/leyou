package cn.itcast.web;

import cn.itcast.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author dhp
 * @Date 2020/6/10 11:16
 * @Version 1.0
 */

@RestController
@RequestMapping("upload")
public class UploudController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传的接口
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){

        return ResponseEntity.ok(uploadService.uploadFile(file));
    }
}
