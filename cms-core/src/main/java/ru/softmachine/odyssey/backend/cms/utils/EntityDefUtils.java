package ru.softmachine.odyssey.backend.cms.utils;

import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class EntityDefUtils {

    public Stream<FieldDef> getAllFieldDefs(FieldDefContainer container) {
        return Stream.concat(
                container.getFields().stream(),
                container.getChildContainers().stream()
                        .flatMap(this::getAllFieldDefs)
                        // сортировка важна для расчетных полей
                        .sorted(Comparator.comparing(fd -> Optional.ofNullable(fd.getSeq()).orElse(0)))
        );
    }
}
