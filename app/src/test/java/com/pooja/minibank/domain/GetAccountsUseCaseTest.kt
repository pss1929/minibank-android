import com.pooja.minibank.domain.model.account.Account
import com.pooja.minibank.domain.repository.AccountRepository
import com.pooja.minibank.domain.usecase.account.GetAccountUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class GetAccountUseCaseTest {

    private lateinit var repository: AccountRepository
    private lateinit var useCase: GetAccountUseCase

    @Before
    fun setup() {
        repository = mock(AccountRepository::class.java)
        useCase = GetAccountUseCase(repository)
    }

    @Test
    fun `invoke should return account list from repository`() {

        runBlocking {

            // Given
            val accounts = listOf(
                Account(10000.0, "Savings", "1", "", "", ""),
                Account(20000.0, "Current", "2", "", "", "")
            )

            Mockito.`when`(repository.getAccount()).thenReturn(accounts)

            // When
            val result = useCase()

            // Then
            assertEquals(accounts, result)
            Mockito.verify(repository).getAccount()
        }
    }
}