package com.aspnt.mddl.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdResponse<T> {
    private T id;
}
