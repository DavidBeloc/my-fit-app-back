package ru.yandex.myfitappback.util;

import java.util.List;

public record PagedResponse<T>(List<T> content,
                            Metadata metadata) {

    public record Metadata(int page,
                           int size,
                           long totalElements,
                           int totalPages,
                           boolean last) {
    }
}
