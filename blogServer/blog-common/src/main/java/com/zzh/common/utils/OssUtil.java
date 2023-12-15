package com.zzh.common.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.base.FilePathEnum;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zzh
 * @description 腾讯oos上次工具类
 *
 * @date 2022/3/8 22:38
 */

@Component
public class OssUtil {
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String buckName;

    @Value("${cos.secret_id}")
    public void setAccessKeyId(String accessKeyId) {
        OssUtil.accessKeyId = accessKeyId;
    }

    @Value("${cos.secret_key}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssUtil.accessKeySecret = accessKeySecret;
    }

    @Value("${cos.bucket_name}")
    public void setBuckName(String buckName) {
        OssUtil.buckName = buckName;
    }

    /**
     * 上传对象至Oss
     *
     * @param file       文件
     * @param targetAddr 目标地址
     * @return 返回服务器的图片url
     */
    public static String upload(MultipartFile file, String targetAddr) {
        if(Objects.isNull(file) || file.isEmpty()){
            throw  new SaveOrUpdateException(HttpStatus.BAD_REQUEST,"文件内容为空！");
        }
        // 获取不重复的随机名
        String fileName = String.valueOf(UUID.randomUUID());
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + fileName + extension;
        // 登录控制台
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        // OSS地区
        Region region = new Region("ap-chongqing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 使用http协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定文件将要存放的存储桶
        String bucketName = buckName;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度
            objectMetadata.setContentLength(file.getSize());
            cosClient.putObject(bucketName, relativeAddr, file.getInputStream(), objectMetadata);
            // 关闭客户端
            cosClient.shutdown();
            return FilePathEnum.OSS_URL.getPath() + relativeAddr;
        } catch (IOException e) {
            throw new CommonWriteException(BaseErrorInfo.SERVER_BUSY);
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 删除oss服务器上的相关内容
     *
     * @param key 对象键
     */
    public static void delete(String key) {
        // 登录控制台
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        // OSS地区
        Region region = new Region("ap-chongqing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 使用http协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        key = key.replace(FilePathEnum.OSS_URL.getPath(), "");
        cosClient.deleteObject(buckName, key);
    }

    /**
     * 批量删除
     *
     * @param keys 删除对象List
     */
    public static void deleteList(List<String> keys) {
        // 登录控制台
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        // OSS地区
        Region region = new Region("ap-chongqing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 使用http协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 删除请求
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(buckName);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<DeleteObjectsRequest.KeyVersion>();
        for (String key : keys) {
            // 防止删除默认图片
            if (Objects.isNull(key) || "".equals(key) || key.equals(FilePathEnum.DefaultCover.getPath()) || key.equals(FilePathEnum.DefaultAvatar.getPath())){
                continue;
            }
            key = key.replace(FilePathEnum.OSS_URL.getPath(), "");
            keyList.add(new DeleteObjectsRequest.KeyVersion(key));
        }
        if (keyList.isEmpty()) {
            return;
        }
        // 设置请求值
        deleteObjectsRequest.setKeys(keyList);
        // 批量删除文件
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) { // 如果部分删除成功部分失败, 返回MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        } catch (CosClientException e) { // 如果是其他错误，例如参数错误， 身份验证不过等会抛出 CosServiceException
            e.printStackTrace();
            throw e;
        }

    }
}
