BEGIN;

CREATE TABLE IF NOT EXISTS public.dimensioner_bredde
(
    bredde_id serial NOT NULL,
    bredde integer NOT NULL,
    CONSTRAINT dimensioner_bredde_pkey PRIMARY KEY (bredde_id),
    CONSTRAINT unique_bredde UNIQUE (bredde)
);

CREATE TABLE IF NOT EXISTS public.dimensioner_laengde
(
    laengde_id serial NOT NULL,
    laengde integer NOT NULL,
    CONSTRAINT dimensioner_laengde_pkey PRIMARY KEY (laengde_id),
    CONSTRAINT uniqe_laengde UNIQUE (laengde)
);


CREATE TABLE IF NOT EXISTS public.orders
(
    order_id serial NOT NULL,
    date_placed date DEFAULT now(),
    status character varying(64) COLLATE pg_catalog."default" NOT NULL,
    bredde integer,
    laengde integer,
    spaer_og_rem_materiale character varying COLLATE pg_catalog."default",
    tag_materiale character varying COLLATE pg_catalog."default",
    tag_type character varying COLLATE pg_catalog."default",
    skur character varying COLLATE pg_catalog."default",
    username character varying COLLATE pg_catalog."default",
    CONSTRAINT orders_pkey PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS public.order_item
(
    order_item_id serial NOT NULL,
    order_id integer,
    product_variant_id integer,
    quantity integer,
    description character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT order_item_pkey PRIMARY KEY (order_item_id),
    CONSTRAINT order_item_order_id_fkey FOREIGN KEY (order_id)
        REFERENCES public.orders (order_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS public.product
(
    product_id serial NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    unit character varying COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL DEFAULT 1,
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS public.product_variant
(
    product_variant_id serial NOT NULL,
    product_id integer NOT NULL,
    length integer NOT NULL,
    CONSTRAINT product_variant_pkey PRIMARY KEY (product_variant_id),
    CONSTRAINT product_variant_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.product (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.skur
(
    id serial NOT NULL,
    skur character varying COLLATE pg_catalog."default",
    CONSTRAINT skur_pkey PRIMARY KEY (id),
    CONSTRAINT fk_skur UNIQUE (skur)
);

CREATE TABLE IF NOT EXISTS public.spaer_og_rem
(
    spaer_og_rem_id serial NOT NULL,
    materiale character varying(64) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT spaer_og_rem_pkey PRIMARY KEY (spaer_og_rem_id),
    CONSTRAINT unique_spaer_og_rem UNIQUE (materiale)
);

CREATE TABLE IF NOT EXISTS public.tag_materiale
(
    id serial NOT NULL,
    materiale character varying(64) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tag_materiale_pkey PRIMARY KEY (id),
    CONSTRAINT unique_tag_materiale UNIQUE (materiale)
);

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    username character varying(64) COLLATE pg_catalog."default" NOT NULL,
    password character varying(64) COLLATE pg_catalog."default" NOT NULL,
    role character varying(12) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_username_key UNIQUE (username)
);

ALTER TABLE IF EXISTS public.order_item
    ADD CONSTRAINT order_item_product_variant_id_fkey FOREIGN KEY (product_variant_id)
        REFERENCES public.product_variant (product_variant_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_bredde FOREIGN KEY (bredde)
        REFERENCES public.dimensioner_bredde (bredde) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_laengde FOREIGN KEY (laengde)
        REFERENCES public.dimensioner_laengde (laengde) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_skur FOREIGN KEY (skur)
        REFERENCES public.skur (skur) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_spaer_og_rem_materiale FOREIGN KEY (spaer_og_rem_materiale)
        REFERENCES public.spaer_og_rem (materiale) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_tag_materiale FOREIGN KEY (tag_materiale)
        REFERENCES public.tag_materiale (materiale) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fk_username FOREIGN KEY (username)
        REFERENCES public.users (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

INSERT INTO public.product (product_id, name, unit, price)
VALUES
    (1, 'Pillar', '', 10),
    (2, 'Rafter', '', 10),
    (3, 'Beam', '', 10);


INSERT INTO public.product_variant (product_variant_id, length, product_id)
VALUES
    (1, '300',  1),
    (2, '480', 2),
    (3, '600', 2),
    (4, '600', 3);

INSERT INTO public.dimensioner_bredde (bredde, bredde_id)
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

INSERT INTO public.dimensioner_laengde (laengde, laengde_id)
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

INSERT INTO public.tag_materiale (materiale)
VALUES
    ('Sunlux 1300K'),
    ('Sunlux 1200N'),
    ('Benders sort'),
    ('Benders brun'),
    ('Benders tegldrød'),
    ('Benders rød'),
    ('Eternit grå B6');

INSERT INTO public.skur (id, skur)
VALUES
    (1, 'Ja'),
    (2, 'Nej');

INSERT INTO public.spaer_og_rem (spaer_og_rem_id, materiale)
VALUES
    (1, 'Benders sort'),
    (2, 'Benders brun'),
    (3, 'Benders tegldrød'),
    (4, 'Benders rød'),
    (5, 'Eternit grå B6');

END;
