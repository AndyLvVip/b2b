package uca.plat.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import uca.plat.file.domain.FileSetInfo;
import uca.platform.StdStringUtils;
import uca.platform.json.StdObjectMapper;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by andy.lv
 * on: 2019/1/25 12:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StdObjectMapper stdObjectMapper;

    @Test
    public void save() {
        FileSetInfo fileSetInfo = new FileSetInfo();
        fileSetInfo.setId(StdStringUtils.uuid());
        fileSetInfo.setFileSrcRemark("TEST_REDIS");
        fileSetInfo.setCreatedBy("test");
        fileSetInfo.setCreatedOn(LocalDateTime.now());
        redisTemplate.opsForValue().set(fileSetInfo.getId(), stdObjectMapper.toJson(fileSetInfo));

        FileSetInfo result = stdObjectMapper.fromJson(redisTemplate.opsForValue().get(fileSetInfo.getId()), FileSetInfo.class);
        System.out.println(result);
        assertEquals(fileSetInfo.getId(), result.getId());
        assertEquals(fileSetInfo.getFileSrcRemark(), result.getFileSrcRemark());
    }
}
