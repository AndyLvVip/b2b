package uca.plat.file;

import jooq.generated.plat.file.tables.records.FileSetInfoRecord;
import org.jooq.DSLContext;
import org.jooq.exception.DataChangedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uca.plat.file.domain.FileSetInfo;
import uca.plat.file.service.FileSetInfoService;

import static jooq.generated.plat.file.Tables.FILE_SET_INFO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by andy.lv
 * on: 2019/1/24 17:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class FileSetInfoServiceTest {

    @Autowired
    private FileSetInfoService fileSetInfoService;

    @Autowired
    private DSLContext dsl;

    @Test
    public void newRecord() {
        FileSetInfo fileSetInfo = new FileSetInfo();
        fileSetInfo.setFileSrcRemark("TEST_FILE");
        FileSetInfoRecord record = dsl.newRecord(FILE_SET_INFO, fileSetInfo);
        assertEquals(fileSetInfo.getFileSrcRemark(), record.getFileSrcRemark());
    }

    @Test
    public void create() {
        FileSetInfo fileSetInfo = new FileSetInfo();
        fileSetInfo.setFileSrcRemark("TEST_CREATE");
        FileSetInfo result = fileSetInfoService.create(fileSetInfo, "test");
        FileSetInfo dbResult = fileSetInfoService.fetch(result.getId());
        System.out.println(dbResult);
        assertNotNull(dbResult);
        assertEquals(result.getId(), dbResult.getId());
        assertEquals(result.getFileSrcRemark(), dbResult.getFileSrcRemark());
        assertEquals(result.getCreatedOn().getYear(), dbResult.getCreatedOn().getYear());
        assertEquals(result.getCreatedOn().getMonth(), dbResult.getCreatedOn().getMonth());
        assertEquals(result.getCreatedOn().getDayOfMonth(), dbResult.getCreatedOn().getDayOfMonth());
        assertEquals(result.getCreatedOn().getHour(), dbResult.getCreatedOn().getHour());
        assertEquals(result.getCreatedOn().getMinute(), dbResult.getCreatedOn().getMinute());
        assertEquals(result.getCreatedOn().getSecond(), dbResult.getCreatedOn().getSecond());
        assertEquals(result.getCreatedBy(), dbResult.getCreatedBy());
        assertEquals(result.getUpdatedBy(), dbResult.getUpdatedBy());
        assertEquals(fileSetInfo.getFileSrcRemark(), result.getFileSrcRemark());
    }

    @Test(expected = DataChangedException.class)
    public void checkDirtyWrite() {
        FileSetInfo fileSetInfo = new FileSetInfo();
        fileSetInfo.setFileSrcRemark("TEST_EDIT");
        FileSetInfo result = fileSetInfoService.create(fileSetInfo, "test");
        FileSetInfo dbResult = fileSetInfoService.fetch(result.getId());
        System.out.println(dbResult);
        dbResult = fileSetInfoService.edit(dbResult, "test");
        System.out.println(dbResult);
        dbResult.setVersion(dbResult.getVersion() - 1);
        dbResult = fileSetInfoService.edit(dbResult, "test");
        System.out.println(dbResult);
    }
}
