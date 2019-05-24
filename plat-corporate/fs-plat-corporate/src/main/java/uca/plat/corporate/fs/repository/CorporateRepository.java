package uca.plat.corporate.fs.repository;

import jooq.generated.corporate.tables.records.CorporateRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.corporate.domain.Corporate;

import static jooq.generated.corporate.Tables.CORPORATE;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 15:08
 */
@Repository
public class CorporateRepository extends StdStrRepository<CorporateRecord, Corporate> {

    protected CorporateRepository(Configuration configuration) {
        super(CORPORATE, Corporate.class, configuration);
    }

}
