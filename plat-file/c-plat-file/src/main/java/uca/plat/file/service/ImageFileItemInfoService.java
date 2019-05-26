package uca.plat.file.service;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.StoreLocationFlag;
import uca.plat.file.domain.FileItemInfo;
import uca.plat.file.util.ImageUtils;
import uca.platform.exception.InternalServerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageFileItemInfoService {

    private final FileItemInfoService fileItemInfoService;

    private final ResourceLoader resourceLoader;

    private final FileUploadService fileUploadService;

    public static final String SUFFIX_SRC = ".src";

    public static final String SUFFIX_100_100 = ".100x100";

    public static final String SUFFIX_800_800 = ".800x800";

    public ImageFileItemInfoService(FileItemInfoService fileItemInfoService
            , ResourceLoader resourceLoader
            , FileUploadService fileUploadService
    ) {
        this.fileUploadService = fileUploadService;
        this.fileItemInfoService = fileItemInfoService;
        this.resourceLoader = resourceLoader;
    }

    public FileItemInfo upload(
            String fileSetInfoId,
            MultipartFile file,
            String fileSrcRemark,
            String createdBy
    ) {
        FileItemInfo fileItemInfo = this.fileItemInfoService.upload(StoreLocationFlag.IMAGE, fileSetInfoId, file, fileSrcRemark, createdBy);
        File srcFile = this.fileUploadService.asFile(fileItemInfo.getFilePath());
        try {
            createThumbnails(srcFile);
            addWatermark(srcFile);
        } catch (IOException e) {
            throw new InternalServerException(e);
        }
        return fileItemInfo;
    }

    public void createThumbnails(File srcFile) throws IOException {
        Thumbnails.of(srcFile)
                .size(100, 100)
                .toFiles(new Rename() {
                    @Override
                    public String apply(String name, ThumbnailParameter param) {
                        return appendSuffix(name, SUFFIX_100_100);
                    }
                });
        Thumbnails.of(srcFile)
                .size(800, 800)
                .toFiles(new Rename() {
                    @Override
                    public String apply(String name, ThumbnailParameter param) {
                        return appendSuffix(name, SUFFIX_800_800);
                    }
                });

    }

    private void addWatermark(File srcFile) throws IOException {
        String srcFilePath = srcFile.getAbsolutePath();
        File bakSrcFile = Paths.get(srcFile.getAbsolutePath()).getParent()
                .resolve(this.fileUploadService.appendSuffix2Filename(srcFile.getName(), SUFFIX_SRC)).toFile();
        FileCopyUtils.copy(srcFile, bakSrcFile);
        File watermarkFile = resourceLoader.getResource("classpath:watermark.png").getFile();
        ImageUtils.addImageWatermark(watermarkFile, bakSrcFile, new File(srcFilePath));
    }
}
