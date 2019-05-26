package uca.plat.file;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThumbnailatorTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void createThumbnail() throws IOException {
        Resource srcFileResource = resourceLoader.getResource("classpath:srcfile.png");
        assertTrue(srcFileResource.exists());

        File srcFile = srcFileResource.getFile();

        Thumbnails.of(srcFile)
                .size(100, 100)
                .toFiles(new Rename() {
                    @Override
                    public String apply(String name, ThumbnailParameter param) {
                        return appendSuffix(name, ".100x100");
                    }
                });

        Path thumbnail = Paths.get(srcFile.getParent()).resolve("srcfile.100x100.png");
        File thumbnailFile = new File(thumbnail.toString());
        assertTrue(thumbnailFile.exists());
        assertTrue(thumbnailFile.delete());
    }
}
