package test.platform;

import org.junit.Test;
import uca.platform.factory.StdObjectFactory;
import uca.platform.json.StdObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class ObjectMapperTest {

    static class AllTypes {
        private LocalDate localDate;

        private LocalTime localTime;

        private LocalDateTime localDateTime;

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public LocalTime getLocalTime() {
            return localTime;
        }

        public void setLocalTime(LocalTime localTime) {
            this.localTime = localTime;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }
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
