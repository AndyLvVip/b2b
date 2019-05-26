package uca.plat.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import uca.plat.file.util.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddingWatermarkTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void addWatermark() throws IOException {
        Resource srcFileResource = resourceLoader.getResource("classpath:srcfile.png");
        assertTrue(srcFileResource.exists());
        Resource watermarkResource = resourceLoader.getResource("classpath:watermark.png");
        assertTrue(watermarkResource.exists());

        File srcFile = srcFileResource.getFile();
        File watermarkFile = watermarkResource.getFile();
        Path destFilePath = Paths.get(srcFile.getAbsolutePath()).getParent().resolve("destfile.png");
        System.out.println(srcFile.getAbsolutePath());
        System.out.println(destFilePath);
        System.out.println(watermarkFile.getAbsolutePath());
        File destFile = destFilePath.toFile();
        ImageUtils.addImageWatermark(watermarkFile, srcFile, destFile);
        assertTrue(destFile.delete());
    }
}
