package uca.base.repository;

import org.jooq.Configuration;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import uca.base.domain.StdStrDomain;
import uca.platform.StdStringUtils;

public class StdStrRepository<R extends UpdatableRecord<R>, P extends StdStrDomain> extends AbstractStdRepository<R, P, String> {


    protected StdStrRepository(Table<R> table, Class<P> type, Configuration configuration) {
        super(table, type, configuration);
    }

    @Override
    protected String getId(P object) {
        return object.getId();
    }

    @Override
    protected void create(P object, String createdBy) {
        if(null == object.getId() || object.getId().isEmpty()) {
            object.setId(StdStringUtils.uuid());
        }
        super.create(object, createdBy);
    }
}
