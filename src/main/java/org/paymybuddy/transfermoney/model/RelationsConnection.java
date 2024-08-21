package org.paymybuddy.transfermoney.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * DTO design pattern to collect the information about RelationsConnection
 *
 */
@Getter
@AllArgsConstructor
public class RelationsConnection {
    private Long id;
    private String connectionName;
}
