package com.bekzataitymov.Service;

import com.bekzataitymov.Repository.UserRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.bouncycastle.asn1.x509.GeneralName.directoryName;

@Service
public class MinIOService {

    @Value("${spring.minio.endpoint}")
    private String endpoint;
    @Value("${spring.minio.accessKey}")
    private String accessKey;
    @Value("${spring.minio.secretKey}")
    private String secretKey;
    private final String folderPath = "C:\\Users\\Asus\\users_files\\";
    @Autowired
    private UserRepository userRepository;
    private MinioClient minioClient;
    private final String bucketName = "users-files";

    @PostConstruct
    private void minioClient(){
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public void createObject(String path) throws Exception{
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build());

    }

    public void deleteObject(String path) throws Exception{
        minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                .build());
    }

    public void downloadObject(String path) throws Exception{
        InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                .build());

        byte[] buf = new byte[1024];
        int length = 0;
        String filename = path.substring(path.lastIndexOf("/") + 1);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        OutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zipOutputStream.putNextEntry(new ZipEntry(filename));

        while ((length = inputStream.read(buf)) > 0){
            zipOutputStream.write(buf, 0, length);
        }
        zipOutputStream.close();
    }

    public void moveObject(String from, String to) throws Exception{
        createObject(to);
        deleteObject(from);
    }

    public Iterable<Result<Item>> getAllObjects(String path) throws Exception{
        Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(path)
                        .build());
        return objects;
    }

    public void uploadObject(String path, MultipartFile multipartFile) throws Exception{
        minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path + multipartFile.getOriginalFilename())
                        .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                        .contentType(multipartFile.getContentType())
                .build()
        );
    }

    public boolean ifObjectsExists(String path){
        boolean isFound = false;
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(path)
                .build());

        for(Result<Item> result : results){
            isFound = true;
            break;
        }
        return isFound;
    }

    public byte getSize(String path) throws Exception{
        long size = minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .build()).size();
        System.out.println("size: " + size);
        return (byte) size;
    }

    public boolean ifPathExists(String path){
        try {
            minioClient.statObject(StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path)
                    .build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteDirectoryOrFile(String fileName) throws Exception{
        String pathToFile = "/tests/" + fileName;
        minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(pathToFile)
                .build());
    }

}
