package com.william.boss.auth;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author john
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JwtInfo implements Serializable{
    private String username;
}
