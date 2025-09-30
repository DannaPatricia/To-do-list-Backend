--
-- PostgreSQL database dump
--

\restrict 340Jj4kpweNYtnbPQU78P0LHoVWJC7tQ4Zsil1gXcLqgM0NTQuDAoIXKYuCGhds

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-09-30 17:07:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 226 (class 1259 OID 41269)
-- Name: list_shared; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.list_shared (
    list_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.list_shared OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 41175)
-- Name: lists; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lists (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    user_id bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.lists OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 41179)
-- Name: lists_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lists_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lists_id_seq OWNER TO postgres;

--
-- TOC entry 4962 (class 0 OID 0)
-- Dependencies: 218
-- Name: lists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lists_id_seq OWNED BY public.lists.id;


--
-- TOC entry 219 (class 1259 OID 41180)
-- Name: tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tags (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    user_id bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tags OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 41184)
-- Name: tags_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tags_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tags_id_seq OWNER TO postgres;

--
-- TOC entry 4963 (class 0 OID 0)
-- Dependencies: 220
-- Name: tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tags_id_seq OWNED BY public.tags.id;


--
-- TOC entry 221 (class 1259 OID 41188)
-- Name: task_tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.task_tags (
    task_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE public.task_tags OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 41191)
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(255),
    status character varying(255) DEFAULT 'PENDING'::character varying,
    due_date date,
    user_id bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    list_id bigint,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 41198)
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tasks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tasks_id_seq OWNER TO postgres;

--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 223
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.id;


--
-- TOC entry 224 (class 1259 OID 41199)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    role character varying(255) DEFAULT 'USER'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 41206)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 225
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4765 (class 2604 OID 41207)
-- Name: lists id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lists ALTER COLUMN id SET DEFAULT nextval('public.lists_id_seq'::regclass);


--
-- TOC entry 4767 (class 2604 OID 41208)
-- Name: tags id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags ALTER COLUMN id SET DEFAULT nextval('public.tags_id_seq'::regclass);


--
-- TOC entry 4769 (class 2604 OID 41209)
-- Name: tasks id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);


--
-- TOC entry 4773 (class 2604 OID 41210)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4956 (class 0 OID 41269)
-- Dependencies: 226
-- Data for Name: list_shared; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.list_shared (list_id, user_id) FROM stdin;
2	4
2	5
\.


--
-- TOC entry 4947 (class 0 OID 41175)
-- Dependencies: 217
-- Data for Name: lists; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lists (id, name, user_id, created_at) FROM stdin;
1	ListaPrueba	4	2025-09-11 20:20:50.278853
2	ListaPrueba2Compartir	5	2025-09-15 20:32:11.363378
4	Películas pendientes	\N	2025-09-16 19:29:58.006146
5	Películas pendientes	4	2025-09-16 19:45:30.429288
6	Películas pendientes2	4	2025-09-16 19:46:01.882161
7	Películas para Samuel	5	2025-09-16 19:53:55.354751
8	Tarea con seccion	5	2025-09-16 20:38:07.382461
11	Prueba de borrar sin permisos tareas	4	2025-09-29 20:36:01.218811
\.


--
-- TOC entry 4949 (class 0 OID 41180)
-- Dependencies: 219
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tags (id, name, user_id, created_at) FROM stdin;
5	Proyecto X	1	2025-09-01 21:21:10.879569
6	Casa	2	2025-09-01 21:21:10.879569
8	Segunda etiqueta	4	2025-09-23 20:06:24.875219
7	Etiqueta modificada	4	2025-09-23 20:00:46.741719
9	FYQ	5	2025-09-29 20:41:10.242156
10	MATE	5	2025-09-29 20:41:10.256265
\.


--
-- TOC entry 4951 (class 0 OID 41188)
-- Dependencies: 221
-- Data for Name: task_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.task_tags (task_id, tag_id) FROM stdin;
3	6
5	9
5	10
\.


--
-- TOC entry 4952 (class 0 OID 41191)
-- Dependencies: 222
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (id, title, description, status, due_date, user_id, created_at, list_id, updated_at) FROM stdin;
3	Comprar comida	Para la semana	PENDING	2025-09-05	4	2025-09-01 21:21:10.879569	1	\N
4	prueba compartida	prueba	PENDING	\N	5	2025-09-15 20:52:29.096729	2	\N
6	Prueba create tarea 2 lista compartida	Estqa es la tarea numero no se	PENDING	2025-09-22	4	2025-09-23 17:20:43.059128	2	2025-09-23 17:20:43.059128
8	Prueba de borrar sin permisos	Estqa es la tarea numero no se	PENDING	2025-09-22	4	2025-09-29 20:37:24.455204	11	2025-09-29 20:37:24.455204
5	ESTA ES LA TAREA DE SAM	ESTA ES LA DESCRIPCION ACTUALIZADA	DONE	2025-09-24	5	2025-09-23 17:18:30.588165	2	2025-09-29 20:41:10.260352
\.


--
-- TOC entry 4954 (class 0 OID 41199)
-- Dependencies: 224
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, email, password_hash, role, created_at) FROM stdin;
1	juan	juan@mail.com	password_hash_demo	USER	2025-09-01 21:21:10.879569
2	maria	maria@mail.com	password_hash_demo	USER	2025-09-01 21:21:10.879569
3	SMEditorial	sm22@gmail.com	$2a$10$E1CiqzepWRM5aUbkSS505exH3dPq7WOr8Hnx8QPFrkRiF5iTjx5La	\N	\N
4	danna	dann@gmail.com	$2a$10$rBNv0zTcEawyNFpN4vEjRuTrcNhxeHo6TlWgFDG9OBpBMPHPDHUmG	\N	2025-09-04 21:58:36.107645
5	samuel	samuel@gmail.com	$2a$10$DiszM52oszV9kAuLHdbuWOHoDtt7xaOtTEDxVR3qOME3z0s2za01q	\N	2025-09-15 18:46:41.834935
6	Nuevo usuario web	usernew1@gmail.com	$2a$10$w2jWRgDV7N8Wo1gSIHEKKeJ4kGA/X6KFANxZsuJCX3su.7Mz/Ce9q	\N	2025-09-29 22:37:38.712871
\.


--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 218
-- Name: lists_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lists_id_seq', 11, true);


--
-- TOC entry 4967 (class 0 OID 0)
-- Dependencies: 220
-- Name: tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tags_id_seq', 10, true);


--
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 223
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tasks_id_seq', 8, true);


--
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 225
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- TOC entry 4793 (class 2606 OID 41273)
-- Name: list_shared list_shared_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.list_shared
    ADD CONSTRAINT list_shared_pkey PRIMARY KEY (list_id, user_id);


--
-- TOC entry 4777 (class 2606 OID 41212)
-- Name: lists lists_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_pkey PRIMARY KEY (id);


--
-- TOC entry 4779 (class 2606 OID 41214)
-- Name: tags tags_name_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_name_user_id_key UNIQUE (name, user_id);


--
-- TOC entry 4781 (class 2606 OID 41216)
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);


--
-- TOC entry 4783 (class 2606 OID 41220)
-- Name: task_tags task_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_tags
    ADD CONSTRAINT task_tags_pkey PRIMARY KEY (task_id, tag_id);


--
-- TOC entry 4785 (class 2606 OID 41222)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 4787 (class 2606 OID 41224)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4789 (class 2606 OID 41226)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4791 (class 2606 OID 41228)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4800 (class 2606 OID 41274)
-- Name: list_shared list_shared_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.list_shared
    ADD CONSTRAINT list_shared_list_id_fkey FOREIGN KEY (list_id) REFERENCES public.lists(id) ON DELETE CASCADE;


--
-- TOC entry 4801 (class 2606 OID 41279)
-- Name: list_shared list_shared_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.list_shared
    ADD CONSTRAINT list_shared_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4794 (class 2606 OID 41229)
-- Name: lists lists_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4795 (class 2606 OID 41234)
-- Name: tags tags_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4796 (class 2606 OID 41249)
-- Name: task_tags task_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_tags
    ADD CONSTRAINT task_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tags(id) ON DELETE CASCADE;


--
-- TOC entry 4797 (class 2606 OID 41254)
-- Name: task_tags task_tags_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_tags
    ADD CONSTRAINT task_tags_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(id) ON DELETE CASCADE;


--
-- TOC entry 4798 (class 2606 OID 41259)
-- Name: tasks tasks_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_list_id_fkey FOREIGN KEY (list_id) REFERENCES public.lists(id) ON DELETE SET NULL;


--
-- TOC entry 4799 (class 2606 OID 41264)
-- Name: tasks tasks_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


-- Completed on 2025-09-30 17:07:05

--
-- PostgreSQL database dump complete
--

\unrestrict 340Jj4kpweNYtnbPQU78P0LHoVWJC7tQ4Zsil1gXcLqgM0NTQuDAoIXKYuCGhds

