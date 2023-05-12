--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-12 09:03:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16703)
-- Name: reminds; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reminds (
    id character varying(50) NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(300),
    "time" character varying(10) NOT NULL,
    date character varying(10) NOT NULL,
    alarm_id character varying(50) NOT NULL,
    login character varying(25) NOT NULL
);


ALTER TABLE public.reminds OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16679)
-- Name: tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tokens (
    id character varying(50) NOT NULL,
    login character varying(25) NOT NULL,
    token character varying(50) NOT NULL
);


ALTER TABLE public.tokens OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16684)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    login character varying(25) NOT NULL,
    password character varying(25) NOT NULL,
    email character varying(25)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3330 (class 0 OID 16703)
-- Dependencies: 216
-- Data for Name: reminds; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.reminds (id, title, description, "time", date, alarm_id, login) VALUES ('28a9c7be-7102-4d37-8906-39c4e041978d', 'Some title', 'Some description', '20:33', '20.03.2002', '4cb08a67-278e-4710-9348-f327b876e9e3', 'Test');
INSERT INTO public.reminds (id, title, description, "time", date, alarm_id, login) VALUES ('ae8f7914-ddc5-4c6f-8d24-fda5a97e7497', 'Some title 3', 'Some description 3', '20:55', '20.03.2023', '63f8ad8e-4d8c-41c4-98ea-4d71104b3228', 'Test1');


--
-- TOC entry 3328 (class 0 OID 16679)
-- Dependencies: 214
-- Data for Name: tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tokens (id, login, token) VALUES ('f70eb0da-337c-49c9-80e4-180a61d444b0', 'Test', '283132e9-a787-483f-a223-e489c2647611');
INSERT INTO public.tokens (id, login, token) VALUES ('29584f29-f295-4e51-8b30-2d2e2168de85', 'Test', '6ce6af70-bc08-4050-9b06-a45504ce8b48');
INSERT INTO public.tokens (id, login, token) VALUES ('2ab7a0ed-2122-4ad7-81e6-7f2784fafcfd', 'Test', 'dc15cb7b-2798-4b52-9246-3d644268a295');
INSERT INTO public.tokens (id, login, token) VALUES ('e25ee112-3bea-4b4f-8f2a-38a78a68c07c', 'Test', '201fa1cf-e5b8-4f9d-b0c3-49899e1f8c3b');
INSERT INTO public.tokens (id, login, token) VALUES ('23c2b3ba-655e-4de6-943f-464a6ddcfebf', 'Test', 'eb443e03-3258-49f2-a168-d2bbe68663ed');
INSERT INTO public.tokens (id, login, token) VALUES ('e7870044-d1fe-4782-ae6f-dda6e7082b9d', 'Test', '77286ff4-f88a-4d73-8ff3-b66b49243e8d');
INSERT INTO public.tokens (id, login, token) VALUES ('18fb217c-d143-401d-9b79-62e18edb00e5', 'Test', 'af75ce11-3768-417f-8ca9-19d2415b2205');
INSERT INTO public.tokens (id, login, token) VALUES ('e262972d-1eb7-4168-8ae6-0fd00f5bffed', 'Test', '9dfdd26f-590d-46f2-a146-40f3ae38ffb3');
INSERT INTO public.tokens (id, login, token) VALUES ('63cba1d7-ce94-4102-95f5-8f2329663a6c', 'Test', 'bcaf6fed-1df2-4969-ba95-153a4bad4659');
INSERT INTO public.tokens (id, login, token) VALUES ('42ad4cba-4da6-48d0-98e9-52eb8e6acddf', 'Test', 'e602abf0-d313-4e77-b026-43b0cf8732f2');
INSERT INTO public.tokens (id, login, token) VALUES ('a5f6cdd1-199e-4c0a-9f82-84f54de88307', 'mylogin', '89d8a828-57e3-4802-bc20-12825e6ef61f');
INSERT INTO public.tokens (id, login, token) VALUES ('56785616-5503-4a47-9f36-a8c0ad10f2ef', 'mylogin', '74721cae-cb96-4ef0-9d75-4cf24c45c057');
INSERT INTO public.tokens (id, login, token) VALUES ('f8543f13-99d8-480f-9df2-4e1ecfd74dda', 'mylogin', 'bd6e5304-dc9f-4164-a02a-58e667fc7999');
INSERT INTO public.tokens (id, login, token) VALUES ('c7dff76e-44cd-4c01-8362-ff1a2ac74a9a', 'mylogin', 'ddc6c484-8e49-4584-8e7f-9afe2913d5c2');


--
-- TOC entry 3329 (class 0 OID 16684)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (login, password, email) VALUES ('Test', 'Test', 'text@mail.ru');
INSERT INTO public.users (login, password, email) VALUES ('mylogin', 'pass', 'mail@mail.ru');


--
-- TOC entry 3185 (class 2606 OID 16707)
-- Name: reminds reminds_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminds
    ADD CONSTRAINT reminds_pkey PRIMARY KEY (id);


--
-- TOC entry 3181 (class 2606 OID 16690)
-- Name: tokens tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_pkey PRIMARY KEY (id);


--
-- TOC entry 3183 (class 2606 OID 16688)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (login);


-- Completed on 2023-05-12 09:03:22

--
-- PostgreSQL database dump complete
--

