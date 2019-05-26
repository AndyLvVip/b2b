package uca.plat.file.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uca.plat.file.domain.FileItemInfo;
import uca.platform.StdStringUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class FileItemInfoRepositoryTest {

    @Autowired
    FileItemInfoRepository fileItemInfoRepository;

    @Test
    public void fetchFirstFileInEachGroup() {
        FileItemInfo fileItemInfo1 = new FileItemInfo();
        fileItemInfo1.setId(StdStringUtils.uuid());
        fileItemInfo1.setSequence(2);
        fileItemInfo1.setFileSetInfoId(StdStringUtils.uuid());
        fileItemInfo1.setFilePath("Test file path");
        fileItemInfo1.setFileName("Test file name");
        fileItemInfo1.setSize(0L);
        fileItemInfoRepository.insert(fileItemInfo1, "Unit Test");
        String expectedId = StdStringUtils.uuid();
        fileItemInfo1.setId(expectedId);
        fileItemInfo1.setSequence(1);
        fileItemInfoRepository.insert(fileItemInfo1, "Unit Test");
        List<FileItemInfo> fileItems = fileItemInfoRepository.fetchFirstFileInEachGroup(Arrays.asList(
                fileItemInfo1.getFileSetInfoId()
        ));
        assertTrue(!fileItems.isEmpty());
        assertEquals(1, fileItems.size());
        assertEquals(expectedId, fileItems.get(0).getId());
        System.out.println(fileItems);
    }


    @Test
    public void fetchByFileSetIds() {
        FileItemInfo fileItemInfo1 = new FileItemInfo();
        fileItemInfo1.setId(StdStringUtils.uuid());
        fileItemInfo1.setSequence(2);
        fileItemInfo1.setFileSetInfoId(StdStringUtils.uuid());
        fileItemInfo1.setFilePath("Test file path");
        fileItemInfo1.setFileName("Test file name");
        fileItemInfo1.setSize(0L);
        fileItemInfoRepository.insert(fileItemInfo1, "Unit Test");
        String expectedId = StdStringUtils.uuid();
        fileItemInfo1.setId(expectedId);
        fileItemInfo1.setSequence(1);
        fileItemInfoRepository.insert(fileItemInfo1, "Unit Test");
        List<FileItemInfo> fileItems = fileItemInfoRepository.fetchByFileSetIds(Arrays.asList(
                fileItemInfo1.getFileSetInfoId()
        ));
        assertTrue(!fileItems.isEmpty());
        assertEquals(2, fileItems.size());
        System.out.println(fileItems);
    }
}
