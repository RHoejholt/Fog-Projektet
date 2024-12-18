package app.persistence;

import app.entities.Product;
import app.entities.ProductVariant;
import app.exception.DatabaseException;
//import app.exception.IllegalInputException;
import app.persistence.ConnectionPool;
import app.persistence.ProductMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Intergration tests
class ProductMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "postgres";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    private static ProductMapper productMapper;

    @BeforeAll
    public static void setUpClass() {

        productMapper = new ProductMapper(connectionPool);
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
// Drop existing tables in the test schema
                stmt.execute("DROP TABLE IF EXISTS test.order_item");
                stmt.execute("DROP TABLE IF EXISTS test.product_variant");
                stmt.execute("DROP TABLE IF EXISTS test.product");
                stmt.execute("DROP TABLE IF EXISTS test.spaer_og_rem");
                stmt.execute("DROP TABLE IF EXISTS test.dimensioner_laengde");
                stmt.execute("DROP TABLE IF EXISTS test.dimensioner_bredde");
                stmt.execute("DROP TABLE IF EXISTS test.tag_materiale");
                stmt.execute("DROP TABLE IF EXISTS test.users");
                stmt.execute("DROP TABLE IF EXISTS test.orders");


// Drop sequences if they exist
                stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.tag_materiale_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.dimensioner_bredde_bredde_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.dimensioner_laengde_laengde_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.spaer_og_rem_spaer_og_rem_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.product_product_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.product_variant_product_variant_id_seq CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_item_order_item_id_seq CASCADE");

// Create tables in the test schema as a copy of the public schema structure
                stmt.execute("CREATE TABLE test.orders AS (SELECT * FROM public.orders) WITH NO DATA");
                stmt.execute("CREATE TABLE test.users AS (SELECT * FROM public.users) WITH NO DATA");
                stmt.execute("CREATE TABLE test.tag_materiale AS (SELECT * FROM public.tag_materiale) WITH NO DATA");
                stmt.execute("CREATE TABLE test.dimensioner_bredde AS (SELECT * FROM public.dimensioner_bredde) WITH NO DATA");
                stmt.execute("CREATE TABLE test.dimensioner_laengde AS (SELECT * FROM public.dimensioner_laengde) WITH NO DATA");
                stmt.execute("CREATE TABLE test.spaer_og_rem AS (SELECT * FROM public.spaer_og_rem) WITH NO DATA");
                stmt.execute("CREATE TABLE test.product AS (SELECT * FROM public.product) WITH NO DATA");
                stmt.execute("CREATE TABLE test.product_variant AS (SELECT * FROM public.product_variant) WITH NO DATA");
                stmt.execute("CREATE TABLE test.order_item AS (SELECT * FROM public.order_item) WITH NO DATA");
// Recreate sequences for auto-increment columns

                stmt.execute("CREATE SEQUENCE test.orders_order_id_seq");
                stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq')");
                stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq')");
                stmt.execute("CREATE SEQUENCE test.tag_materiale_id_seq");
                stmt.execute("ALTER TABLE test.tag_materiale ALTER COLUMN id SET DEFAULT nextval('test.tag_materiale_id_seq')");
                stmt.execute("CREATE SEQUENCE test.dimensioner_bredde_bredde_id_seq");
                stmt.execute("ALTER TABLE test.dimensioner_bredde ALTER COLUMN bredde_id SET DEFAULT nextval('test.dimensioner_bredde_bredde_id_seq')");
                stmt.execute("CREATE SEQUENCE test.dimensioner_laengde_laengde_id_seq");
                stmt.execute("ALTER TABLE test.dimensioner_laengde ALTER COLUMN laengde_id SET DEFAULT nextval('test.dimensioner_laengde_laengde_id_seq')");
                stmt.execute("CREATE SEQUENCE test.spaer_og_rem_spaer_og_rem_id_seq");
                stmt.execute("ALTER TABLE test.spaer_og_rem ALTER COLUMN spaer_og_rem_id SET DEFAULT nextval('test.spaer_og_rem_spaer_og_rem_id_seq')");
                stmt.execute("CREATE SEQUENCE test.product_product_id_seq");
                stmt.execute("ALTER TABLE test.product ALTER COLUMN product_id SET DEFAULT nextval('test.product_product_id_seq')");
                stmt.execute("CREATE SEQUENCE test.product_variant_product_variant_id_seq");
                stmt.execute("ALTER TABLE test.product_variant ALTER COLUMN product_variant_id SET DEFAULT nextval('test.product_variant_product_variant_id_seq')");
                stmt.execute("CREATE SEQUENCE test.order_item_order_item_id_seq");
                stmt.execute("ALTER TABLE test.order_item ALTER COLUMN order_item_id SET DEFAULT nextval('test.order_item_order_item_id_seq')");

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }

    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.order_item");
                stmt.execute("DELETE FROM test.product_variant");
                stmt.execute("DELETE FROM test.product");
                stmt.execute("DELETE FROM test.spaer_og_rem");
                stmt.execute("DELETE FROM test.dimensioner_laengde");
                stmt.execute("DELETE FROM test.dimensioner_bredde");
                stmt.execute("DELETE FROM test.tag_materiale");
                // stmt.execute("DELETE FROM test.skur");
                stmt.execute("DELETE FROM test.users");
                stmt.execute("DELETE FROM test.orders");

                // Reset the sequence numbers
                stmt.execute("SELECT setval('test.orders_order_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.users_user_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.tag_materiale_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.dimensioner_bredde_bredde_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.dimensioner_laengde_laengde_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.spaer_og_rem_spaer_og_rem_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.product_product_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.product_variant_product_variant_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.order_item_order_item_id_seq', 1, false)");

                // Insert initial rows
                stmt.execute("INSERT INTO test.dimensioner_bredde (bredde_id, bredde) VALUES " +
                        "(1, 270), (2, 300), (3, 330), (4, 360), (5, 390), " +
                        "(6, 420), (7, 450), (8, 480), (9, 510), (10, 540)");

                stmt.execute("INSERT INTO test.dimensioner_laengde (laengde_id, laengde) VALUES " +
                        "(1, 270), (2, 300), (3, 330), (4, 360), (5, 390), " +
                        "(6, 420), (7, 450), (8, 480), (9, 510), (10, 540)");

                stmt.execute("INSERT INTO test.tag_materiale (id, materiale) VALUES " +
                        "(1, 'Sunlux 1300K'), (2, 'Sunlux 1200N'), (3, 'Benders sort')");

                // stmt.execute("INSERT INTO test.skur (id, skur) VALUES " +
                //       "(1, 'Ja'), (2, 'Nej')");

                stmt.execute("INSERT INTO test.spaer_og_rem (spaer_og_rem_id, materiale) VALUES " +
                        "(1, 'Benders sort'), (2, 'Benders brun')");

                stmt.execute("INSERT INTO test.users (user_id, username, password, role) VALUES " +
                        "(1, 'admin', 'password123', 'admin'), (2, 'salesperson', 'sales123', 'sales')");

                stmt.execute("INSERT INTO test.orders (order_id, date_placed, status, bredde, laengde, spaer_og_rem_materiale, tag_materiale, skur, username) VALUES " +
                        "(1, '2024-01-01', 'placed', 270, 300, 'Benders sort', 'Sunlux 1300K', 'Ja', 'salesperson')");

                stmt.execute("INSERT INTO test.product (product_id, name, unit, price) VALUES" +
                        "(1, 'Beam', 1, 10)");


                stmt.execute("INSERT INTO test.product_variant (product_variant_id, product_id, length) VALUES" +
                        "(1, 1, 600)");

                stmt.execute("INSERT INTO test.product_variant (product_variant_id, product_id, length) VALUES" +
                        "(2, 1, 780)");


            }
        } catch (SQLException throwables) {
            fail("Database connection failed: " + throwables.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        // Ensure a connection is obtained from the pool
        try (Connection connection = connectionPool.getConnection()) {
            assertNotNull(connection); // Ensure the connection is not null
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }


    @Test
    void testTablesAreCreated() throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT to_regclass('test.orders');");
            assertTrue(rs.next());
            assertNotNull(rs.getString(1));

        }
    }

    @Test
    void getVariantsByProductIdAndMinLengthTest1() throws DatabaseException {
        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(1, 1);
        assertNotNull(productVariant);
        assertEquals(600, productVariant.getLength());
        assertEquals(1, productVariant.getProductId());
    }

    @Test
    void getVariantsByProductIdAndMinLengthTest2() throws DatabaseException {
        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(1, 1800);
        assertNull(productVariant);
    }

    @Test
    void getVariantsByProductIdAndMinLengthTest3() throws DatabaseException {
        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(1, 650);
        assertNotNull(productVariant);
        assertEquals(780, productVariant.getLength());
        assertEquals(1, productVariant.getProductId());
    }


}
