package uca.base.jooq;

import org.jooq.ExecuteContext;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultExecuteListener;

/**
 * Created by @author andy
 * On @date 19-1-25 上午12:29
 */
public class JooqUtils {

    public static Settings settings() {
        return new Settings()
                .withRenderNameStyle(RenderNameStyle.AS_IS)
                .withExecuteWithOptimisticLocking(true)
                .withExecuteWithOptimisticLockingExcludeUnversioned(true);
    }


    public static DefaultExecuteListener initUserVar4FetchTopNInEachGroup() {
        return new DefaultExecuteListener() {
            @Override
            public void renderEnd(ExecuteContext ctx) {
                ctx.sql("set @num = 0, @prev = '';" + ctx.sql() + ";");
            }
        };
    }

}
