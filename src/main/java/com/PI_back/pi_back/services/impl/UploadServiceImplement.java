package com.PI_back.pi_back.services.impl;

import com.PI_back.pi_back.services.UploadService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImplement implements UploadService {


    @Autowired
    private final Cloudinary cloudinary;

    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImplement.class);
    // Se llama al metodo upload y se pasa los argumentos multipartFile.getBytes() y Map.of ...
    // EL primer argumento settea el contenido del archivo y el segundo argumento le añade un id random segun el nombre del archivo
    // Una vez que es enviado el archivo a cloud, se recibe un Map de respuesta y se llama al metodo get y se pasa el argumento url
    // que retorna la url del archivo
    // Se llama al toString() en el get() para traer la representacion en String del objeto que retorna.
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException, RuntimeException {
        try{
            File file = convertMultipartFile(multipartFile);
            Map uploadResult = cloudinary
                    .uploader()
                    .upload(file, ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        }
        catch(Exception e){
            logger.info("Ocurrio un error en el upload file");
            throw new RuntimeException(e);
        }


    }
    private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertFile;
    }


}
