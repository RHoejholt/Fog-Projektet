package persistence;

import app.entities.Product;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ProductMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Intergration tests
//@Disabled
class ProductMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "postgres";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    private static ProductMapper productMapper;

    @BeforeAll
    public static void setUpClass() {
        try {

            productMapper = new ProductMapper(connectionPool);
            try (Connection testConnection = connectionPool.getConnection())
            {
                try (Statement stmt = testConnection.createStatement())
                {
                    // The test schema is already created, so we only need to delete/create test tables
                    stmt.execute("DROP TABLE IF EXISTS test.orders");
                    stmt.execute("DROP TABLE IF EXISTS test.users");
                    stmt.execute("DROP TABLE IF EXISTS test.tag_materials");
                    stmt.execute("DROP TABLE IF EXISTS test.dimensioner_bredde");
                    stmt.execute("DROP TABLE IF EXISTS test.dimensioner_længde");
                    stmt.execute("DROP TABLE IF EXISTS test.spær_og_rem");
                    stmt.execute("DROP TABLE IF EXISTS test.product");
                    stmt.execute("DROP TABLE IF EXISTS test.product_variant");
                    stmt.execute("DROP TABLE IF EXISTS test.order_item");
                    // stmt.execute("DROP SEQUENCE IF EXISTS test.member_member_id_seq CASCADE;");

                  //// stmt.execute("DROP SEQUENCE IF EXISTS test.sport_sport_id_seq CASCADE;");

                    // The test schema is already created, so we only need to delete/create test tables
                    stmt.execute("DROP TABLE IF EXISTS test.orders");
                    stmt.execute("DROP TABLE IF EXISTS test.users");
                    stmt.execute("DROP TABLE IF EXISTS test.tag_materials");
                    stmt.execute("DROP TABLE IF EXISTS test.dimensioner_bredde");
                    stmt.execute("DROP TABLE IF EXISTS test.dimensioner_længde");
                    stmt.execute("DROP TABLE IF EXISTS test.spær_og_rem");
                    stmt.execute("DROP TABLE IF EXISTS test.product");
                    stmt.execute("DROP TABLE IF EXISTS test.product_variant");
                    stmt.execute("DROP TABLE IF EXISTS test.order_item");
// stmt.execute("DROP SEQUENCE IF EXISTS test.member_member_id_seq CASCADE;");

// Create tables as copy of original public schema structure
                    stmt.execute("CREATE TABLE test.orders AS (SELECT * from public.orders) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.tag_materials AS (SELECT * from public.tag_materials) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.dimensioner_bredde AS (SELECT * from public.dimensioner_bredde) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.dimensioner_længde AS (SELECT * from public.dimensioner_længde) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.spær_og_rem AS (SELECT * from public.spær_og_rem) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.produc AS (SELECT * from public.product) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.product_variant AS (SELECT * from public.product_variant) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.order_item AS (SELECT * from public.order_item) WITH NO DATA");

                }
            }
            catch (SQLException throwables)
            {
                System.out.println(throwables.getMessage());
                fail("Database connection failed");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = db.connect()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.registration");
                stmt.execute("DELETE FROM test.team");
                stmt.execute("DELETE FROM test.sport");
                stmt.execute("DELETE FROM test.member");
                stmt.execute("DELETE FROM test.zip");

                // Reset the sequence number
                stmt.execute("SELECT setval('test.member_member_id_seq', 1)");

                // Insert rows
                stmt.execute("INSERT INTO test.zip VALUES " +
                        "(3700, 'Rønne'), (3730, 'Nexø'), (3740, 'Svanneke'), " +
                        "(3760, 'Gudhjem'), (3770, 'Allinge'), (3782, 'Klemmensker')");

                stmt.execute("INSERT INTO test.member (member_id, name, address, zip, gender, year) VALUES " +
                        "(1, 'Hans Sørensen', 'Agernvej 3', 3700, 'm', 2000), " +
                        "(2, 'Jens Kofoed', 'Agrevej 5', 3700, 'm', 2001), " +
                        "(3, 'Peter Hansen', 'Ahlegårdsvejen 7', 3700, 'm', 2002)");

                // Set sequence to continue from the largest member_id
                stmt.execute("SELECT setval('test.member_member_id_seq', COALESCE((SELECT MAX(member_id)+1 FROM test.member), 1), false)");
            }
        } catch (SQLException throwables) {
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(db.connect());
    }

    @Test
    void getAllMembers() throws DatabaseException {
        List<Member> members = memberMapper.getAllMembers();
        assertEquals(3, members.size());
        assertEquals(members.get(0), new Member(1,"Hans Sørensen", "Agernvej 3",3700, "Rønne","m",2000));
        assertEquals(members.get(1), new Member(2, "Jens Kofoed","Agrevej 5",3700,"Rønne","m",2001));
        assertEquals(members.get(2), new Member(3, "Peter Hansen","Ahlegårdsvejen 7",3700,"Rønne","m",2002));
    }

    @Test
    void getMemberById() throws DatabaseException {
        assertEquals(new Member(3, "Peter Hansen","Ahlegårdsvejen 7",3700,"Rønne","m",2002), memberMapper.getMemberById(3));
    }

    @Test
    void deleteMember() throws DatabaseException {
        assertTrue(memberMapper.deleteMember(2));
        assertEquals(2, memberMapper.getAllMembers().size());
    }

    @Test
    void insertMember() throws DatabaseException, IllegalInputException {
        Member m1 = memberMapper.insertMember(new Member("Jon Snow","Wintherfell 3", 3760, "Gudhjem", "m", 1992));
        assertNotNull(m1);
        assertEquals(4, memberMapper.getAllMembers().size());
        assertEquals(m1, memberMapper.getMemberById(4));
    }

    @Test
    void updateMember() throws DatabaseException {
        boolean result = memberMapper.updateMember(new Member(2, "Jens Kofoed","Agrevej 5",3760,"Gudhjem","m",1999));
        assertTrue(result);
        Member m1 = memberMapper.getMemberById(2);
        assertEquals(1999,m1.getYear());
        assertEquals(3, memberMapper.getAllMembers().size());
    }

}