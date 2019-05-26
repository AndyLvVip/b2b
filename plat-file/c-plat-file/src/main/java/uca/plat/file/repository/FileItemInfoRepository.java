package uca.plat.file.repository;

import jooq.generated.plat.file.tables.records.FileItemInfoRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import uca.base.jooq.JooqUtils;
import uca.base.repository.StdStrRepository;
import uca.plat.file.domain.FileItemInfo;

import java.util.Collections;
import java.util.List;

import static jooq.generated.plat.file.Tables.FILE_ITEM_INFO;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.iif;

/**
 * Created by andy.lv
 * on: 2019/1/28 13:39
 */
@Repository
public class FileItemInfoRepository extends StdStrRepository<FileItemInfoRecord, FileItemInfo> {

    private final DSLContext dsl;

    public FileItemInfoRepository(Configuration configuration, DSLContext dsl) {
        super(FILE_ITEM_INFO, FileItemInfo.class, configuration);
        this.dsl = dsl;
    }

    public void insert(FileItemInfo fileItemInfo, String createdBy) {
        if (null == fileItemInfo.getSequence()) {
            fileItemInfo.setSequence(1);
        }
        super.insert(fileItemInfo, createdBy);
    }

    public List<FileItemInfo> fetchByFileSetIds(List<String> fileSetInfoIds) {
        if(CollectionUtils.isEmpty(fileSetInfoIds))
            return Collections.emptyList();

        return dsl.selectFrom(FILE_ITEM_INFO)
                .where(FILE_ITEM_INFO.FILE_SET_INFO_ID.in(fileSetInfoIds))
                .fetchInto(FileItemInfo.class);
    }

    public List<FileItemInfo> fetchFirstFileInEachGroup(List<String> fileSetInfoIds) {
        if(CollectionUtils.isEmpty(fileSetInfoIds))
            return Collections.emptyList();
        DSLContext newDsl = DSL.using(dsl.configuration()
                .derive(JooqUtils.initUserVar4FetchTopNInEachGroup())
        );

        Field<String> varPrev = field("@prev", String.class);
        Field<String> prev = field("prev", String.class);
        Field<Integer> num = field("num", Integer.class);
        Table innerTable = newDsl.select(FILE_ITEM_INFO.fields())
                .select(iif(varPrev.eq(FILE_ITEM_INFO.FILE_SET_INFO_ID),
                        field("@num := @num + 1", Integer.class),
                        field("@num := 1", Integer.class)).as(num))
                .select(field("@prev := " + FILE_ITEM_INFO.FILE_SET_INFO_ID.toString().replace("\"", "`")).as(prev))
                .from(FILE_ITEM_INFO)
                .where(FILE_ITEM_INFO.FILE_SET_INFO_ID.in(fileSetInfoIds))
                .orderBy(FILE_ITEM_INFO.FILE_SET_INFO_ID.asc(), FILE_ITEM_INFO.SEQUENCE.asc(), FILE_ITEM_INFO.CREATED_ON.asc())
                .asTable("innerTable")
                ;
        Results results = newDsl.selectFrom(innerTable)
                .where(innerTable.field(num).le(1))
                .fetchMany()
                ;
        if(null == results || results.isEmpty())
            return Collections.EMPTY_LIST;

        return results.get(0)
                .into(FileItemInfo.class)
                ;

    }
}
