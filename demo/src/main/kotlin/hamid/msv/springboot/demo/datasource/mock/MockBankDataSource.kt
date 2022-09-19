package hamid.msv.springboot.demo.datasource.mock

import hamid.msv.springboot.demo.datasource.BankDataSource
import hamid.msv.springboot.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository("mock")
class MockBankDataSource : BankDataSource{

    private val banks = mutableListOf(
        Bank("1234",1.0,1),
        Bank("1235",2.0,2),
        Bank("1236",3.0,3)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull{ it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(newBank: Bank): Bank {
        if (banks.any { it.accountNumber == newBank.accountNumber }){
            throw IllegalArgumentException("Bank with account number ${newBank.accountNumber} already exists!")
        }

        banks.add(newBank)
        return newBank
    }

    override fun updateBank(updatedBank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == updatedBank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number ${updatedBank.accountNumber}")

        banks.remove(currentBank)
        banks.add(updatedBank)

        return updatedBank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

        banks.remove(currentBank)
    }
}