package uca.plat.file.repository;

import jooq.generated.plat.file.tables.records.FileSetInfoRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.plat.file.domain.FileSetInfo;

import static jooq.generated.plat.file.Tables.FILE_SET_INFO;

/**
 * Created by andy.lv
 * on: 2019/1/24 16:20
 */
@Repository
public class FileSetInfoRepository extends StdStrRepository<FileSetInfoRecord, FileSetInfo> {

    public FileSetInfoRepository(Configuration configuration, DSLContext dsl) {
        super(FILE_SET_INFO, FileSetInfo.class, configuration);
    }

}
