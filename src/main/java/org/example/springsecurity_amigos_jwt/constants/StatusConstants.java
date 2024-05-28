package org.example.springsecurity_amigos_jwt.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusConstants {

    ACTIVE(1) , DE_ACTIVE(0);

    private final Integer status;

}
