package SQLDataBase;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.mockito.Mockito.*;

class DatabaseConnectorTest {

    private MockedStatic<DriverManager> mockedDriverManager;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private DatabaseConnector databaseConnector;

    @BeforeEach
    void setUp() throws SQLException {
        // Моки
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Мокінг DriverManager
        mockedDriverManager = mockStatic(DriverManager.class);
        mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                .thenReturn(mockConnection);

        databaseConnector = new DatabaseConnector();
    }

    @AfterEach
    void tearDown() throws Exception {
        databaseConnector.close();
        verify(mockConnection, atLeastOnce()).close(); // Перевірка закриття з'єднання
        mockedDriverManager.close(); // Скидання статичного мокінгу
    }

    @Test
    void testExecuteUpdate() throws SQLException {
        String query = "INSERT INTO TestTable (name) VALUES (?)";
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = databaseConnector.executeUpdate(query, "TestName");

        verify(mockPreparedStatement).setObject(1, "TestName");
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        Assertions.assertEquals(1, result);
    }

    @Test
    void testExecuteQuery() throws SQLException {
        String query = "SELECT * FROM TestTable WHERE name = ?";
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        ResultSet resultSet = databaseConnector.executeQuery(query, "TestName");

        verify(mockPreparedStatement).setObject(1, "TestName");
        verify(mockPreparedStatement).executeQuery();
        Assertions.assertNotNull(resultSet);
    }

    @Test
    void testCloseConnection() throws SQLException {
        databaseConnector.close();

        verify(mockConnection).close();
    }

    @Test
    void testGetConnection() {
        Connection connection = databaseConnector.getConnection();
        Assertions.assertNotNull(connection);
        Assertions.assertEquals(mockConnection, connection);
    }
}
