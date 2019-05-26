package uca.plat.file.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.StoreLocationFlag;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.service.FileUploadService;

@RestController
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/file")
    public FileItemInfo upload(MultipartFile file) {
        String relativeFilePath = fileUploadService.store(file, StoreLocationFlag.RICH_TEXT_FILE);
        String filePath = fileUploadService.buildFilePath(relativeFilePath);
        FileItemInfo result = new FileItemInfo();
        result.setFilePath(filePath);
        return result;
    }
}
