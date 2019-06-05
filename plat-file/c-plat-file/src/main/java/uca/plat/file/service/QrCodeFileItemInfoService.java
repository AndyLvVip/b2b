package uca.plat.file.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.StoreLocationFlag;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.vo.QrCodeUploadVo;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
public class QrCodeFileItemInfoService {

    private final StringRedisTemplate stringRedisTemplate;

    private final StdObjectMapper stdObjectMapper;

    private final FileItemInfoService fileItemInfoService;

    private final ImageFileItemInfoService imageFileItemInfoService;

    public QrCodeFileItemInfoService(StringRedisTemplate stringRedisTemplate
            , StdObjectMapper stdObjectMapper
            , FileItemInfoService fileItemInfoService
            , ImageFileItemInfoService imageFileItemInfoService

    ) {
        this.fileItemInfoService = fileItemInfoService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.stdObjectMapper = stdObjectMapper;
        this.imageFileItemInfoService = imageFileItemInfoService;
    }

    public String initQrCode(QrCodeUploadVo qrCodeUploadVo) {
        String qrCode = StdStringUtils.uuid();
        String jsonFileItemInfo = stdObjectMapper.toJson(qrCodeUploadVo);
        stringRedisTemplate.opsForValue().set(qrCode, jsonFileItemInfo, 4 * 60 * 60, TimeUnit.SECONDS);
        return qrCode;
    }

    public FileItemInfo upload(String qrCode, MultipartFile file) {
        QrCodeUploadVo qrCodeUploadVo = fetchQrCodeUploadVo(qrCode);
        return fileItemInfoService.upload(StoreLocationFlag.FILE
                , qrCodeUploadVo.getFileSetInfoId()
                , file
                , qrCodeUploadVo.getFileSrcRemark(),
                qrCodeUploadVo.getCreatedBy());
    }

    private QrCodeUploadVo fetchQrCodeUploadVo(String qrCode) {
        String jsonFileItemInfo = stringRedisTemplate.opsForValue().get(qrCode);
        QrCodeUploadVo qrCodeUploadVo = stdObjectMapper.fromJson(jsonFileItemInfo, QrCodeUploadVo.class);
        if(null == qrCodeUploadVo) {
            throw new IllegalArgumentException("二维码已经失效，请重新扫码");
        }
        return qrCodeUploadVo;
    }

    public FileItemInfo uploadImg(String qrCode, MultipartFile file) {
        QrCodeUploadVo qrCodeUploadVo = fetchQrCodeUploadVo(qrCode);
        return imageFileItemInfoService.upload(
                qrCodeUploadVo.getFileSetInfoId()
                , file
                , qrCodeUploadVo.getFileSrcRemark(),
                qrCodeUploadVo.getCreatedBy());
    }
}
