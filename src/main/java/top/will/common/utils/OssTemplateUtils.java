package top.will.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Desc: AliYunOss util
 * @author panhao
 */
@Component
public class OssTemplateUtils {

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.bucketDomain}")
    private String bucketDomain;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;


    /**
     * 上传文件返回url*
     */
    public String upload(InputStream inputStream, String fileName) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStream.available());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);

        //日期归档
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //文件夹名称
        String folderName = simpleDateFormat.format(new Date());

        //oss客户端对象
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //上传
        oss.putObject(bucketName, "wisdomSite/" + folderName + "/" + fileName, inputStream, objectMetadata);

        inputStream.close();

        oss.shutdown();

        return bucketDomain + "/wisdomSite/" + folderName + "/" + fileName;
    }

    public void deleteFileFromAliyun(String fileName){
            //oss客户端对象
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //删除oss内文件
            oss.deleteObject(bucketName, fileName);
    }

    public String getContentType(String filenameExtension) {
        if (".bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(filenameExtension) ||
                ".jpg".equalsIgnoreCase(filenameExtension) ||
                ".png".equalsIgnoreCase(filenameExtension) ||
                ".dwg".equals(filenameExtension)) {
            return "image/jpg";
        }
        if (".html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(filenameExtension)) {
            return "application/download";
        }
        if (".vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(filenameExtension) ||
                ".ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(filenameExtension) ||
                ".doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        if (".pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        return "image/jpg";

    }


}
