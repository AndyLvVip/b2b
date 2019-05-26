package uca.plat.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.PlatFileConfiguration;
import uca.plat.file.StoreLocationFlag;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.repository.FileItemInfoRepository;
import uca.platform.StdStringUtils;

/**
 * Created by andy.lv
 * on: 2019/1/25 17:31
 */
@Service
public class FileItemInfoService {

    private final PlatFileConfiguration platFileConfiguration;
    private final FileItemInfoRepository fileItemInfoRepository;
    private final FileSetInfoService fileSetInfoService;
    private final FileUploadService fileUploadService;

    public FileItemInfoService(PlatFileConfiguration platFileConfiguration
            , FileItemInfoRepository fileItemInfoRepository
            , FileSetInfoService fileSetInfoService
            , FileUploadService fileUploadService
    ) {
        this.fileUploadService = fileUploadService;
        this.fileItemInfoRepository = fileItemInfoRepository;
        this.platFileConfiguration = platFileConfiguration;
        this.fileSetInfoService = fileSetInfoService;
    }

    private FileItemInfo store(MultipartFile file, StoreLocationFlag flag) {
        String relativeFilePath = fileUploadService.store(file, flag);
        FileItemInfo fileItemInfo = new FileItemInfo();
        fileItemInfo.setId(StdStringUtils.uuid());
        fileItemInfo.setFilePath(this.fileUploadService.buildFilePath(relativeFilePath));
        fileItemInfo.setFileName(file.getOriginalFilename());
        fileItemInfo.setSize(file.getSize());
        return fileItemInfo;
    }

    public FileItemInfo upload(
            StoreLocationFlag flag
            , String fileSetInfoId
            , MultipartFile file
            , String fileSrcRemark
            , String createdBy
    ) {
        fileSetInfoService.initFileSetInfoIfNotExists(fileSetInfoId, fileSrcRemark, createdBy);
        FileItemInfo fileItemInfo = store(file, flag);
        fileItemInfo.setFileSetInfoId(fileSetInfoId);
        fileItemInfoRepository.insert(fileItemInfo, createdBy);
        return fileItemInfo;
    }

    public void delete(FileItemInfo fileItemInfo) {
        FileItemInfo result = fileItemInfoRepository.forceFindById(fileItemInfo.getId());
        result.setVersion(fileItemInfo.getVersion());
        fileItemInfoRepository.delete(result);
    }

}
