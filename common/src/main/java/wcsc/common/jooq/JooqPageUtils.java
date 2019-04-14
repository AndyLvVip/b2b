package wcsc.common.jooq;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitStep;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

/**
 * Created by andy.lv
 * on: 2019/3/12 10:45
 */
public class JooqPageUtils {

    public static <T> PageImpl<T> page(Class<T> returnType, Query query, Counter count, Pageable pageable) {
        int total = count.handle().fetchOne(0, int.class);
        if(total == 0)
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        else
            return new PageImpl<>(query.handle()
                    .limit(pageable.getPageSize())
                    .offset((int) pageable.getOffset())
                    .fetchInto(returnType)
                    , pageable
                    , total
            )
                    ;
    }

    public interface Query<T> {
        SelectLimitStep<?> handle();
    }

    public interface Counter {
        SelectConditionStep<? extends Record1<Integer>> handle();
    }
}
