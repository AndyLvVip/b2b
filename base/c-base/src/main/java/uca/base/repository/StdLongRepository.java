package uca.base.repository;

import org.jooq.Configuration;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import uca.base.domain.StdLongDomain;

public class StdLongRepository<R extends UpdatableRecord<R>, P extends StdLongDomain> extends AbstractStdRepository<R, P, Long> {
    protected StdLongRepository(Table<R> table, Class<P> type, Configuration configuration) {
        super(table, type, configuration);
    }

    @Override
    protected Long getId(P object) {
        return object.getId();
    }
}
