package hamid.msv.springboot.demo.datasource.mock

import hamid.msv.springboot.demo.datasource.BankDataSource
import hamid.msv.springboot.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource{

    private val banks = listOf(
        Bank("1234",1.0,1),
        Bank("1235",2.0,2),
        Bank("1236",3.0,3)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.first { it.accountNumber == accountNumber }
}