package uca.plat.file.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import uca.plat.file.PlatFileConfiguration;
import uca.plat.file.StoreLocationFlag;
import uca.platform.StdDateUtils;
import uca.platform.StdStringUtils;
import uca.platform.exception.InternalServerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private final PlatFileConfiguration platFileConfiguration;

    public FileUploadService(PlatFileConfiguration platFileConfiguration) {
        this.platFileConfiguration = platFileConfiguration;
    }

    public String store(MultipartFile file, StoreLocationFlag flag) {
        String relativeFilePath = buildRelativeFilePath(file, flag);
        Path filePath = Paths.get(platFileConfiguration.getRealFilePath(), relativeFilePath);
        try {
            File dest = new File(filePath.toString());
            if(!dest.getParentFile().exists())
                Assert.isTrue(dest.getParentFile().mkdirs(), "can not mkdirs for: " + dest.getParentFile().getAbsolutePath());
            Assert.isTrue(dest.createNewFile(), "can not mkdirs for: " + dest.getAbsolutePath());
            file.transferTo(dest);
            return relativeFilePath;
        } catch (IOException e) {
            throw new InternalServerException(e);
        }
    }

    public String buildFilePath(String relativeFilePath) {
        return this.platFileConfiguration.getUrl()
                + this.platFileConfiguration.getRealFilePath().replace(this.platFileConfiguration.getExposedLocation(), "")
                + "/"
                + relativeFilePath.replace("\\", "/");
    }

    public File asFile(String filePath) {
        return new File(Paths.get(
                this.platFileConfiguration.getExposedLocation(),
                filePath.replace(this.platFileConfiguration.getUrl(), "")
        ).toString());
    }

    private String buildRelativeFilePath(MultipartFile file, StoreLocationFlag flag) {
        String filename = file.getOriginalFilename();
        String fileExtension = "";
        int position;
        if (-1 != (position = filename.lastIndexOf("."))) {
            fileExtension = filename.substring(position);
        }
        return Paths.get(flag.randomDir(), StdDateUtils.now2yyyyMMdd(),
                StdStringUtils.uuid() + fileExtension).toString();
    }

    private String extName(String filename) {
        String ext = "";
        if (filename.contains(".")) {
            ext = filename.substring(filename.lastIndexOf("."));
        }
        return ext;
    }

    private String filenameWithoutExt(String filename) {
        String filenameWithoutExt = filename;
        if(filename.contains(".")) {
            filenameWithoutExt = filename.substring(0, filename.lastIndexOf("."));
        }
        return filenameWithoutExt;
    }

    public String appendSuffix2Filename(String filename, String suffix) {
        return filenameWithoutExt(filename) + suffix + extName(filename);
    }
}
