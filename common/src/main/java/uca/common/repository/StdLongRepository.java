package uca.common.repository;

import org.jooq.Configuration;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import uca.common.domain.StdLongDomain;

public class StdLongRepository<R extends UpdatableRecord<R>, P extends StdLongDomain> extends StdRepository<R, P, Long> {
    protected StdLongRepository(Table<R> table, Class<P> type, Configuration configuration) {
        super(table, type, configuration);
    }

    @Override
    protected Long getId(P object) {
        return object.getId();
    }

    @Override
    protected void create(P object) {
        super.create(object);
    }
}
