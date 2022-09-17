package hamid.msv.springboot.demo.datasource.mock

import hamid.msv.springboot.demo.datasource.BankDataSource
import hamid.msv.springboot.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource{

    private val banks = listOf(Bank("1234",1.0,1))

    override fun retrieveBanks(): Collection<Bank> = banks

}