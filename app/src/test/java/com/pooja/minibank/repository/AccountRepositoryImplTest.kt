import com.pooja.minibank.data.local.dao.AccountDao
import com.pooja.minibank.data.remote.ApiService
import com.pooja.minibank.data.remote.dto.account.AccountResponseDto
import com.pooja.minibank.data.repository.AccountRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class AccountRepositoryImplTest {

    private lateinit var repository: AccountRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var accountDao: AccountDao

    @Before
    fun setup() {
        apiService = Mockito.mock(ApiService::class.java)
        accountDao = Mockito.mock(AccountDao::class.java)
        repository = AccountRepositoryImpl(apiService, accountDao)
    }

    @Test
    fun `getAccount should return data from API when API success`() {

        runBlocking {

            // Given
            val dtoList = listOf(
                AccountResponseDto (1000.0, "","Savings", "1000","","")
            )

            val response = Response.success(dtoList)

            Mockito.`when`(apiService.getAccounts()).thenReturn(response)

            // When
            val result = repository.getAccount()

            // Then
            assertEquals(1, result.size)
            Mockito.verify(accountDao).insertAccount(Mockito.anyList())
        }
    }
}