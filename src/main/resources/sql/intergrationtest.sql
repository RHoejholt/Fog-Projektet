CREATE TABLE IF NOT EXISTS test.orders
(
    order_id serial NOT NULL,
    date_placed date DEFAULT NOW(),
    status character varying(64) NOT NULL,
    user_id integer default NULL,
    PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS test.users
(
    user_id serial NOT NULL,
    username character varying(64) NOT NULL UNIQUE,
    password character varying(64) NOT NULL,
    role character varying(12) NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS test.orders
    ADD CONSTRAINT fk FOREIGN KEY (user_id)
        REFERENCES test.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

CREATE TABLE IF NOT EXISTS test.tag_materiale
(
    id serial NOT NULL,
    materiale character varying(64) NOT NULL,
    tag_type character varying(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS test.dimensioner_bredde
(
    bredde_id serial NOT NULL,
    bredde integer NOT NULL,
    PRIMARY KEY (bredde_id)
);

CREATE TABLE IF NOT EXISTS test.dimensioner_længde
(
    længde_id serial NOT NULL,
    længde integer NOT NULL,
    PRIMARY KEY (længde_id)
);

CREATE TABLE IF NOT EXISTS test.spær_og_rem
(
    spær_og_rem_id serial NOT NULL,
    materiale character varying(64) NOT NULL,
    PRIMARY KEY (spær_og_rem_id)
);

INSERT INTO test.spær_og_rem (spær_og_rem_id, materiale)
VALUES
    (1,'Benders sort'),
    (2,'Benders brun'),
    (3,'Benders tegldrød'),
    (4,'Benders rød'),
    (5,'Eternit grå B6');



CREATE TABLE test.product
(
     product_id SERIAL PRIMARY KEY,
     name VARCHAR(100),
     unit VARCHAR,
     price INTEGER
);

-- Table: test.product_variant
CREATE TABLE test.product_variant
(
     product_variant_id SERIAL PRIMARY KEY,
     length INTEGER,
     product_id INTEGER REFERENCES test.product(product_id)
);

-- Table: test.order_item
CREATE TABLE test.order_item
(
     order_item_id SERIAL PRIMARY KEY,
     order_id INTEGER,
     product_variant_id INTEGER REFERENCES test.product_variant(product_variant_id),
     quantity INTEGER,
     description VARCHAR(100)
);

INSERT INTO test.product (product_id, name, unit, price)
VALUES
    (1, 'Pillar', '', 10),
    (2, 'Rafter', '', 10),
    (3, 'Beam', '', 10);


INSERT INTO test.product_variant (product_variant_id, length, product_id)
VALUES
    (1, '300',  1),
    (2, '480', 2),
    (3, '600', 2),
    (4, '600', 3);

INSERT INTO test.dimensioner_bredde (bredde, bredde_id)
VALUES
    (270, 1),
    (300, 2),
    (330, 3),
    (360, 4),
    (390, 5),
    (420, 6),
    (450, 7),
    (480, 8),
    (510, 9),
    (540, 10),
    (570, 11),
    (600, 12);

INSERT INTO test.dimensioner_længde (længde, længde_id)
VALUES
    (270, 1),
    (300, 2),
    (330, 3),
    (360, 4),
    (390, 5),
    (420, 6),
    (450, 7),
    (480, 8),
    (510, 9),
    (540, 10),
    (570, 11),
    (600, 12),
    (630, 13),
    (660, 14),
    (690, 15),
    (720, 16),
    (750, 17),
    (780, 18);

INSERT INTO test.tag_materiale (materiale, tag_type)
VALUES
    ('Sunlux 1300K', 'Fladt'),
    ('Sunlux 1200N', 'Fladt'),
    ('Benders sort', 'Skrå'),
    ('Benders brun', 'Skrå'),
    ('Benders tegldrød', 'Skrå'),
    ('Benders rød', 'Skrå'),
    ('Eternit grå B6', 'Skrå');

/*
INSERT INTO test.skur (id,skur)
VALUES
    (1,Ja),
    (2,Nej);
*/

