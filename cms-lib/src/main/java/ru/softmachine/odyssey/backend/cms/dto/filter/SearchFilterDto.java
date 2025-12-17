package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterDto {

    private String search;

    private String ftsSearch;

    private List<BaseRef> fields;
}
