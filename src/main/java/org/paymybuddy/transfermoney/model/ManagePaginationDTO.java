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
    List< Transactions > transactionsList;
    List<ConnectionDTO> allConnectionsList;
    List<RelationsConnection> connectionsList;
    List<BankAccountDTO> bankAccountDTOList;

}
