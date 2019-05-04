package uca.common.repository;

import org.jooq.Configuration;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DAOImpl;
import uca.common.domain.StdDomain;
import uca.platform.exception.StdFileNotFoundException;

import java.time.LocalDateTime;

/**
 * Created by andy.lv
 * on: 2019/1/24 17:23
 */
public abstract class StdRepository<R extends UpdatableRecord<R>, P extends StdDomain, T> extends DAOImpl<R, P, T> {

    protected StdRepository(Table<R> table, Class<P> type, Configuration configuration) {
        super(table, type, configuration);
    }


    @Override
    public void insert(P object) {
        create(object);
    }

    protected void create(P object) {
        LocalDateTime now = LocalDateTime.now();
        object.setCreatedOn(now);
        object.setUpdatedOn(now);
        super.insert(object);
    }

    @Override
    public void update(P object) {
        object.setUpdatedOn(LocalDateTime.now());
        super.update(object);
    }

    public P forceFindById(T id) {
        P result = findById(id);
        if(null == result)
            throw new StdFileNotFoundException("can not find document by id: " + id);
        return result;
    }
}
