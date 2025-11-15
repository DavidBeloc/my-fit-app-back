package ru.yandex.myfitappback.integration.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.myfitappback.date.dto.DateReadDto;
import ru.yandex.myfitappback.date.dto.DateRequestDto;
import ru.yandex.myfitappback.date.service.DateService;
import ru.yandex.myfitappback.integration.IntegrationTestBase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class DateServiceIT extends IntegrationTestBase {

    private final DateService dateService;

    private static final DateReadDto SAVED_DATE_READ_DTO_1 = new DateReadDto(1L, LocalDate.of(2025, 1, 1));
    private static final DateReadDto SAVED_DATE_READ_DTO_2 = new DateReadDto(2L, LocalDate.of(2025, 1, 2));

    @Test
    void findDate() {
        DateRequestDto dateRequestDto = new DateRequestDto(SAVED_DATE_READ_DTO_1.date());
        var actualResult = dateService.findDate(1L, dateRequestDto);
        assertTrue(actualResult.isPresent());
        assertThat(actualResult.get()).isEqualTo(SAVED_DATE_READ_DTO_1);
    }

    @Test
    void findAllByYearAndMonth() {
        List<DateReadDto> actualResult = dateService.findDatesByYearAndMonth(1L, 2025, 1);
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult.get(0)).isEqualTo(SAVED_DATE_READ_DTO_1);
        assertThat(actualResult.get(1)).isEqualTo(SAVED_DATE_READ_DTO_2);
    }

    @Nested
    @Tag("createDate")
    class CreateDateTest {

        @Test
        void createDate() {
            DateRequestDto dateRequestDto = new DateRequestDto(LocalDate.of(2025, 1, 3));
            DateReadDto actualResult = dateService.createDate(1L, dateRequestDto);
            assertThat(actualResult.id()).isNotNull();
            assertEquals(dateRequestDto.date(), actualResult.date());
        }

        @Test
        void throwExceptionOnCreateIfDateExists() {
            DateRequestDto dateRequestDto = new DateRequestDto(LocalDate.of(2025, 1, 1));
            assertThrows(EntityExistsException.class, () -> dateService.createDate(1L, dateRequestDto));
        }
    }

    @Nested
    @Tag("updateDate")
    class UpdateDateTest {

        @Test
        void updateDate() {
            DateRequestDto dateRequestDto = new DateRequestDto(LocalDate.of(2025, 1, 3));
            DateReadDto actualResult = dateService.updateDate(1L, dateRequestDto);
            assertEquals( dateRequestDto.date(), actualResult.date());
        }

        @Test
        void throwExceptionOnUpdateIfDateExists() {
            DateRequestDto dateRequestDto = new DateRequestDto(LocalDate.of(2025, 1, 2));
            assertThrows(EntityExistsException.class, () -> dateService.updateDate(2L, dateRequestDto));
        }
    }

    @Nested
    @Tag("deleteDate")
    class DeleteDateTest {

        @Test
        void deleteDate() {
            boolean actualResult = dateService.deleteDate(1L);
            assertTrue(actualResult);
        }

        @Test
        void deleteIsFailIfDateDoesNotExist() {
            boolean actualResult = dateService.deleteDate(10L);
            assertFalse(actualResult);
        }
    }
}
