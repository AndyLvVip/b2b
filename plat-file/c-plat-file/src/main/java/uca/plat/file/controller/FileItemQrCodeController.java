package uca.plat.file.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.service.QrCodeFileItemInfoService;
import uca.plat.file.vo.QrCodeUploadVo;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileItemQrCodeController {

    private final QrCodeFileItemInfoService qrCodeFileItemInfoService;

    public FileItemQrCodeController(QrCodeFileItemInfoService qrCodeFileItemInfoService) {
        this.qrCodeFileItemInfoService = qrCodeFileItemInfoService;
    }

    @GetMapping("/fileItem/qrCode")
    public Map<String, String> initQrCode(@AuthenticationPrincipal User user,
                             QrCodeUploadVo qrCodeUploadVo
    ) {
        qrCodeUploadVo.setCreatedBy(user.getUsername());
        Map<String, String> map = new HashMap<>();
        map.put("qrCode", qrCodeFileItemInfoService.initQrCode(qrCodeUploadVo));
        return map;
    }

    @PostMapping("/fileItem/qrCode/file/{qrCode}")
    public FileItemInfo upload(@PathVariable("qrCode") String qrCode, MultipartFile file) {
        return qrCodeFileItemInfoService.upload(qrCode, file);
    }

    @PostMapping("/fileItem/qrCode/img/{qrCode}")
    public FileItemInfo uploadImg(@PathVariable("qrCode") String qrCode, MultipartFile file) {
        return qrCodeFileItemInfoService.uploadImg(qrCode, file);
    }
}
