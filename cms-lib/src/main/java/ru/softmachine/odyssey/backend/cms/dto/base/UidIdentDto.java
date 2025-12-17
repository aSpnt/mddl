package ru.softmachine.odyssey.backend.cms.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UidIdentDto {

    private UUID id;
}
