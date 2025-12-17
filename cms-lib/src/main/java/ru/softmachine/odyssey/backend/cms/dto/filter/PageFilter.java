package ru.softmachine.odyssey.backend.cms.dto.filter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageFilter {

    @Max(100)
    @Min(1)
    private Integer pageSize = 10;

    @Min(0)
    private Integer pageNumber = 0;

    public Pageable getAsPage() {
        return Pageable.ofSize(pageSize);
    }
}
