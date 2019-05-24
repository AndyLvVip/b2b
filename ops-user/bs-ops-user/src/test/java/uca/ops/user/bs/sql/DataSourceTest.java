package uca.ops.user.bs.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;

import static jooq.generated.ops.user.Tables.USER;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class DataSourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void meta2Schema() throws SQLException {
        String tableName = USER.getName();
        Connection conn = dataSource.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + " limit 1");
        ResultSetMetaData rsmd = rs.getMetaData();

        DatabaseMetaData dbmd = conn.getMetaData();
        String schemaName = rsmd.getSchemaName(1);
        String catalogName = rsmd.getCatalogName(1);

        for (int i = 0; i < 10; i++) {
            ResultSet results = dbmd.getColumns(catalogName, schemaName, tableName, "%");
            assertTrue(results.next());
        }
    }
}
