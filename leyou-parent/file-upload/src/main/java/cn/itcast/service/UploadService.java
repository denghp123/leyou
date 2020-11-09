package cn.itcast.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/10 11:22
 * @Version 1.0
 */

@Service
@Slf4j
public class UploadService {
    //支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg","image/jpg");

    public String uploadFile(MultipartFile file) {


//        try {
//            //获取文件名称
//            String filename = file.getOriginalFilename();
//
//            //文件的存储路径
//            File destfile = new File("D:\\sofiware\\nginx-1.8.0\\html\\", filename);
//            //文件存储
//            file.transferTo(destfile);
//            //返回图片的地址
//
//            String url ="http://image.leyou.com/"+filename;
//            return url;
//        } catch (Exception e) {
//            log.error("【上传微服务】文件上传失败",e);
//            throw new LyException(ExceptionEnum.FILE_UPLOAD_ERROR);
//
//
//        }





        try {
            // 1、图片信息校验
            // 1)校验文件类型
            String contentType = file.getContentType();
            if (!suffixes.contains(contentType)){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }



            // 2、保存图片
            // 2.1、生成保存目录,保存到nginx所在目录的html下，这样可以直接通过nginx来访问到
            File dir = new File("D:\\sofiware\\nginx-1.8.0\\html\\");
            if (!dir.exists()){
                dir.mkdirs();
            }
            // 2.2、保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 2.3、拼接图片地址
            String url = "http://image.leyou.com/" + file.getOriginalFilename();
            return url;
        } catch (IOException e) {
            log.error("【上传微服务】文件上传失败",e);
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }




    }
}
