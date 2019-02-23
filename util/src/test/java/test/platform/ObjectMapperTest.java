package test.platform;

import lombok.Data;
import org.junit.Test;
import uca.platform.factory.StdObjectFactory;
import uca.platform.json.StdObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class ObjectMapperTest {

    @Data
    static class AllTypes {
        private LocalDate localDate;

        private LocalTime localTime;

        private LocalDateTime localDateTime;
    }

    @Test
    public void serialize() {
        AllTypes allTypes = new AllTypes();
        allTypes.setLocalDate(LocalDate.now());
        allTypes.setLocalTime(LocalTime.now());
        allTypes.setLocalDateTime(LocalDateTime.now());
        StdObjectMapper stdObjectMapper = new StdObjectMapper(StdObjectFactory.objectMapper());

        String json = stdObjectMapper.toJson(allTypes);
        System.out.println(json);
        allTypes = stdObjectMapper.fromJson(json, AllTypes.class);
        String json2 = stdObjectMapper.toJson(allTypes);
        System.out.println(json2);
        assertEquals(json, json2);
    }
}
