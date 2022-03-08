package org.atos.bankspringbatch;

import org.atos.bankspringbatch.dao.BankTransaction;
import org.atos.bankspringbatch.dao.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired
    private BankTransactionRepository bankTransactionRepository;
    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        bankTransactionRepository.saveAll(list);
    }
}
