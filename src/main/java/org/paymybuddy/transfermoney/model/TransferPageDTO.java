package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class TransferPageDTO {
    private ConnectionDTO connectionDTO;
    private List<ConnectionDTO> allConnectionsList;
    private List<RelationsConnection> connectionsList;
    private List<TransactionsConnection> transactionsList;
    private List<BankAccountDTO> bankAccountDTOList;
}
