package com.william.boss.cloud;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author john
 */
@Component
@Slf4j
public class AzureServiceImpl {

    private static String ACCOUNT_NAME;


    private static String ACCOUNT_KEY;


    private static String END_POINT;


    private static String PROTOCOL;


    private static String CONTAINER_NAME;


    private static String IMAGE_CATALOG;
    private static String baseUrl;
    private static String OSSTYPE;
    @Value("${resource-manage.ossType:test}")
    public void setOssType(String ossType) {
        OSSTYPE = ossType;
    }
    @Value("${azure.account-name}")
    public void setAccountName(String accountName) {
        ACCOUNT_NAME = accountName;
    }

    @Value("${azure.account-key}")
    public void setAccountKey(String accountKey) {
        ACCOUNT_KEY = accountKey;
    }

    @Value("${azure.end-point}")
    public void setEndPoint(String endPoint) {
        END_POINT = endPoint;
    }

    @Value("${azure.protocol}")
    public void setPROTOCOL(String protocol) {
        PROTOCOL = protocol;
    }

    @Value("${azure.container-name}")
    public void setContainerName(String containerName) {
        CONTAINER_NAME = containerName;
    }

    @Value("${azure.image-catalog}")
    public void setImageCatalog(String imageCatalog) {
        IMAGE_CATALOG = imageCatalog;
    }

    private static CloudBlobContainer container = null;
    public static String getOssType(){
        return OSSTYPE;
    }

    @PostConstruct
    public static void initAzure() {
        try {
            // 获得StorageAccount对象
            String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(MessageFormat.format(format, PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, END_POINT));
            // 由StorageAccount对象创建BlobClient
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // 根据传入的containerName, 获得container实例
            container = blobClient.getContainerReference(CONTAINER_NAME);
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            e.printStackTrace();
        }
    }

    public static String uploadFile(File file) {
        return uploadFile(file,"default","");
    }

    /**
     * 文件上传
     * @param file 文件对象
     * @return 上传成功后文件在服务器上的地址
     */
    public static String uploadFile(File file,String path,String prefix) {
        try {
            // 构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference(IMAGE_CATALOG +path+"/"+ file.getName()+prefix);
            blob.getProperties().setContentType(getContentType(file));
            // 将本地文件上传到Azure Container
            blob.uploadFromFile(file.getPath());
            // 获得获得属性
            blob.downloadAttributes();
            // 获得上传后的文件大小
            long blobSize = blob.getProperties().getLength();
            // 获得本地文件大小
            long localSize = file.length();
            // 校验
            if (blobSize != localSize) {
                log.warn("文件 {} 上传失败", file.getName());
                // 删除blob
                blob.deleteIfExists();
            } else {
                log.info("文件 {} 上传成功", file.getName());
                if(file.exists()){
                    file.delete();
                }
            }
            return baseUrl!=null?blob.getUri().toString().replace(baseUrl,""):blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param inputStream 输入流
     * @param size 文件大小
     * @param path 文件路径
     * @param contentType mime 类型
     * @return 上传成功后文件在服务器上的地址
     */
    public static String uploadFile(InputStream inputStream, long size, String path, String contentType) {
        try {
            // 构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference(path);
            // 设置mime 类型
            blob.getProperties().setContentType(contentType);
            // 将本地文件上传到Azure Container
            blob.upload(inputStream, size);

            return baseUrl!=null?blob.getUri().toString().replace(baseUrl,""):blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void UploadLargeSizeFile(String videoPath, CloudBlobContainer container) throws URISyntaxException, StorageException, IOException {
        File source = new File(videoPath);
        String blobName = source.getName();
        FileInputStream inputStream = new FileInputStream(source);
        final int blockLength = 1024 * 1024;
        byte[] bufferBytes = new byte[blockLength];
        int blockCount = (int) (source.length() / blockLength) + 1;
        System.out.println("Total block count：" + blockCount + ", Total size: " + source.length());
        int currentBlockSize = 0;

        CloudBlockBlob blockBlobRef = container.getBlockBlobReference(blobName);

        System.out.println("===============Begin Uploading===============");
        ArrayList<BlockEntry> blockList = new ArrayList<BlockEntry>();

        for (int i = 0; i < blockCount; i++) {

            String blockId = String.format("%08d", i);
            currentBlockSize = blockLength;
            if (i == blockCount - 1) {
                currentBlockSize = (int) (source.length() - blockLength * i);
                bufferBytes = new byte[currentBlockSize];
            }

            inputStream.read(bufferBytes, 0, currentBlockSize);
            blockBlobRef.uploadBlock(blockId, getRandomDataStream(blockLength), blockLength, null, null, null);
            blockList.add(new BlockEntry(blockId, BlockSearchMode.LATEST));
            System.out.println("Submitted block index：" + i + ", BlockIndex:" + blockId);
        }
        blockBlobRef.commitBlockList(blockList);
        System.out.println("===============Uploading Done===============");


        System.out.println("Blob URL: " + blockBlobRef.getUri().toString());
    }

    public static void downloadFile(String blobPath, String targetPath) {
        String finalPath = targetPath.concat(blobPath).trim();

        String dir = finalPath.substring(0, finalPath.lastIndexOf("/"));

        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            if(!file.mkdirs()) {
                return;
            }
        }

        try {
            // 传入要blob的path
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath);
            // 传入目标path
            blob.downloadToFile(finalPath);
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void downloadFile(String blobPath,OutputStream outStream){
        try {
            // 传入要blob的path
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath);
            // 传入目标path
            blob.download(outStream);
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
    }
    public static void listBlobs(String perfix) {
        /**
         * 第一个参数, container中blob的前缀, 可以是文件夹的前缀, 也可以是blob的前缀
         * 第二个参数, 是否展开文件夹中的文件, 如container中无文件夹, 则会列出所有blob
         */
        Iterable<ListBlobItem> blobItems = container.listBlobs(null, true);
        for (ListBlobItem blobItem : blobItems) {
            String uri = blobItem.getUri().toString();
            System.out.println(uri);
        }
    }

    private static String getContentType(File file) {
        //利用nio提供的类判断文件ContentType
        Path path = Paths.get(file.getPath());
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            log.error("Read File ContentType Error");
        }
        //若失败则调用另一个方法进行判断
        if (contentType == null) {
            contentType = new MimetypesFileTypeMap().getContentType(file);
        }
        return contentType;
    }

    private static ByteArrayInputStream getRandomDataStream(int length) {
        final Random randGenerator = new Random();
        final byte[] buff = new byte[length];
        randGenerator.nextBytes(buff);
        return new ByteArrayInputStream(buff);
    }

    public static void main(String[] args) {
        File file = new File("D:\\data\\照片\\照片");
        for (File file1 : file.listFiles()) {
            if(file1.isDirectory()){
                String temp=file1.getName();
                for (File file2 : file1.listFiles()) {
                    test(file2,temp);
                }
            }
        }


    }

    private static void test(File file,String temp){
        try {
            System.out.println(getContentType(file));
//            // 获得StorageAccount对象
            String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(MessageFormat.format(format, "https", "dtcnew", "MnpvYw99GiKx3PjPZio4ZI97YhD1j8NLutL5zHCuSDTqA2wItsu1wsNecTk+EY6MUxme3rpJ+dFytFjjv5gkYw==", "core.chinacloudapi.cn"));
//            // 由StorageAccount对象创建BlobClient
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
//            // 根据传入的containerName, 获得container实例
            container = blobClient.getContainerReference("dtc");
//             构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference("static-images/"+temp+"/" + file.getName());
            String contentType = new MimetypesFileTypeMap().getContentType(file);
            blob.getProperties().setContentType(contentType);
            // 将本地文件上传到Azure Container
            blob.uploadFromFile(file.getPath());
            // 获得获得属性
            blob.downloadAttributes();
            // 获得上传后的文件大小
            long blobSize = blob.getProperties().getLength();
            // 获得本地文件大小
            long localSize = file.length();
            // 校验
            if (blobSize != localSize) {
                log.warn("文件 {} 上传失败", file.getName());
                // 删除blob
                blob.deleteIfExists();
            } else {
                log.info("文件 {} 上传成功", file.getName());
            }
            System.out.println(blob.getUri().toString());
            listBlobs("dtc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}