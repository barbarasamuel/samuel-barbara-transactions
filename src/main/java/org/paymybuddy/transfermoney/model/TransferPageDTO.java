package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class TransferPageDTO {
    private ConnectionDTO connectionDTO;
    private List<ConnectionDTO> allConnectionsDTOList;
    private List<RelationsConnection> relationsConnectionList;
    private List<TransactionsConnection> transactionsConnectionList;
    private List<BankAccountDTO> bankAccountDTOList;
}
