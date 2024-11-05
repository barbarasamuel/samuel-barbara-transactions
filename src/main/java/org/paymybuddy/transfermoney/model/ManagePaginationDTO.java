package org.paymybuddy.transfermoney.model;

import lombok.Builder;
import lombok.Data;
import org.paymybuddy.transfermoney.entity.Transactions;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
public class ManagePaginationDTO {

    ConnectionDTO connectionDTO;
    Page<Transactions> page;
    List<TransactionDTO> transactionsDTOList;
    List<ConnectionDTO> allConnectionsDTOList;
    List<RelationsConnection> relationsConnectionList;
    List<BankAccountDTO> bankAccountDTOList;

}
