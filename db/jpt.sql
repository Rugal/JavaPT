--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: jpt; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA jpt;


ALTER SCHEMA jpt OWNER TO postgres;

SET search_path = jpt, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: admin; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE admin (
    aid integer NOT NULL,
    uid integer,
    grantee integer,
    since bigint,
    level integer
);


ALTER TABLE jpt.admin OWNER TO postgres;

--
-- Name: admin_aid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE admin_aid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.admin_aid_seq OWNER TO postgres;

--
-- Name: admin_aid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE admin_aid_seq OWNED BY admin.aid;


--
-- Name: client; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE client (
    cid integer NOT NULL,
    name character varying(50),
    version character varying(10),
    enabled boolean
);


ALTER TABLE jpt.client OWNER TO postgres;

--
-- Name: client_cid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE client_cid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.client_cid_seq OWNER TO postgres;

--
-- Name: client_cid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE client_cid_seq OWNED BY client.cid;


--
-- Name: invitation; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE invitation (
    iid integer NOT NULL,
    uid integer,
    issue_time bigint
);


ALTER TABLE jpt.invitation OWNER TO postgres;

--
-- Name: invitation_iid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE invitation_iid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.invitation_iid_seq OWNER TO postgres;

--
-- Name: invitation_iid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE invitation_iid_seq OWNED BY invitation.iid;


--
-- Name: level; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE level (
    lid integer NOT NULL,
    minimum integer,
    name character varying(50)
);


ALTER TABLE jpt.level OWNER TO postgres;

--
-- Name: level_lid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE level_lid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.level_lid_seq OWNER TO postgres;

--
-- Name: level_lid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE level_lid_seq OWNED BY level.lid;


--
-- Name: post; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE post (
    pid integer NOT NULL,
    uid integer,
    title character varying(50),
    content text,
    torrent character varying(50),
    post_time bigint,
    size integer,
    enabled boolean,
    visible boolean
);


ALTER TABLE jpt.post OWNER TO postgres;

--
-- Name: post_pid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE post_pid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.post_pid_seq OWNER TO postgres;

--
-- Name: post_pid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE post_pid_seq OWNED BY post.pid;


--
-- Name: post_tags; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE post_tags (
    ptid integer NOT NULL,
    tid integer,
    pid integer
);


ALTER TABLE jpt.post_tags OWNER TO postgres;

--
-- Name: post_tags_ptid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE post_tags_ptid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.post_tags_ptid_seq OWNER TO postgres;

--
-- Name: post_tags_ptid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE post_tags_ptid_seq OWNED BY post_tags.ptid;


--
-- Name: signin_log; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE signin_log (
    slid integer NOT NULL,
    uid integer,
    signin_time bigint,
    ip character varying(30)
);


ALTER TABLE jpt.signin_log OWNER TO postgres;

--
-- Name: signin_log_slid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE signin_log_slid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.signin_log_slid_seq OWNER TO postgres;

--
-- Name: signin_log_slid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE signin_log_slid_seq OWNED BY signin_log.slid;


--
-- Name: tag; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE tag (
    tid integer NOT NULL,
    name character varying(50),
    icon character varying(50)
);


ALTER TABLE jpt.tag OWNER TO postgres;

--
-- Name: tag_tid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE tag_tid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.tag_tid_seq OWNER TO postgres;

--
-- Name: tag_tid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE tag_tid_seq OWNED BY tag.tid;


--
-- Name: thread; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE thread (
    tid integer NOT NULL,
    pid integer,
    uid integer,
    content text,
    post_time bigint
);


ALTER TABLE jpt.thread OWNER TO postgres;

--
-- Name: thread_tid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE thread_tid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.thread_tid_seq OWNER TO postgres;

--
-- Name: thread_tid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE thread_tid_seq OWNED BY thread.tid;


--
-- Name: user; Type: TABLE; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE TABLE "user" (
    uid integer NOT NULL,
    password character varying(100),
    username character varying(100),
    email character varying(100),
    passkey character varying(50),
    upload_byte bigint DEFAULT 0,
    download_byte bigint DEFAULT 0,
    last_report bigint,
    credit integer DEFAULT 0,
    referee integer,
    register_time bigint,
    status integer
);


ALTER TABLE jpt."user" OWNER TO postgres;

--
-- Name: user_uid_seq; Type: SEQUENCE; Schema: jpt; Owner: postgres
--

CREATE SEQUENCE user_uid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jpt.user_uid_seq OWNER TO postgres;

--
-- Name: user_uid_seq; Type: SEQUENCE OWNED BY; Schema: jpt; Owner: postgres
--

ALTER SEQUENCE user_uid_seq OWNED BY "user".uid;


--
-- Name: aid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY admin ALTER COLUMN aid SET DEFAULT nextval('admin_aid_seq'::regclass);


--
-- Name: cid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY client ALTER COLUMN cid SET DEFAULT nextval('client_cid_seq'::regclass);


--
-- Name: iid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY invitation ALTER COLUMN iid SET DEFAULT nextval('invitation_iid_seq'::regclass);


--
-- Name: lid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY level ALTER COLUMN lid SET DEFAULT nextval('level_lid_seq'::regclass);


--
-- Name: pid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY post ALTER COLUMN pid SET DEFAULT nextval('post_pid_seq'::regclass);


--
-- Name: ptid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY post_tags ALTER COLUMN ptid SET DEFAULT nextval('post_tags_ptid_seq'::regclass);


--
-- Name: slid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY signin_log ALTER COLUMN slid SET DEFAULT nextval('signin_log_slid_seq'::regclass);


--
-- Name: tid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY tag ALTER COLUMN tid SET DEFAULT nextval('tag_tid_seq'::regclass);


--
-- Name: tid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY thread ALTER COLUMN tid SET DEFAULT nextval('thread_tid_seq'::regclass);


--
-- Name: uid; Type: DEFAULT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN uid SET DEFAULT nextval('user_uid_seq'::regclass);


--
-- Data for Name: admin; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY admin (aid, uid, grantee, since, level) FROM stdin;
8	1	\N	1439004612212	4
9	6	6	1439782679993	3
10	7	7	1439782680104	3
\.


--
-- Name: admin_aid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('admin_aid_seq', 16, true);


--
-- Data for Name: client; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY client (cid, name, version, enabled) FROM stdin;
1	Utorrent	*	t
2	transmit	*	t
\.


--
-- Name: client_cid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('client_cid_seq', 10, true);


--
-- Data for Name: invitation; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY invitation (iid, uid, issue_time) FROM stdin;
\.


--
-- Name: invitation_iid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('invitation_iid_seq', 6, true);


--
-- Data for Name: level; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY level (lid, minimum, name) FROM stdin;
1	0	Basic
2	100	Rudimentary
6	10000	Grand Master
5	8000	Master
4	5000	Advanced
3	1000	Enhanced
7	100000	Super
8	500000	Funky
\.


--
-- Name: level_lid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('level_lid_seq', 17, true);


--
-- Data for Name: post; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY post (pid, uid, title, content, torrent, post_time, size, enabled, visible) FROM stdin;
\.


--
-- Name: post_pid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('post_pid_seq', 18, true);


--
-- Data for Name: post_tags; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY post_tags (ptid, tid, pid) FROM stdin;
\.


--
-- Name: post_tags_ptid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('post_tags_ptid_seq', 6, true);


--
-- Data for Name: signin_log; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY signin_log (slid, uid, signin_time, ip) FROM stdin;
\.


--
-- Name: signin_log_slid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('signin_log_slid_seq', 1, false);


--
-- Data for Name: tag; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY tag (tid, name, icon) FROM stdin;
1	Movie	icon/movie.gif
2	Animation	icon/anim.gif
3	FLAC	icon/flac.gif
4	BLUE-RAY	icon/blue-ray.gif
5	Touchless	icon/touchless.gif
\.


--
-- Name: tag_tid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('tag_tid_seq', 21, true);


--
-- Data for Name: thread; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY thread (tid, pid, uid, content, post_time) FROM stdin;
\.


--
-- Name: thread_tid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('thread_tid_seq', 6, true);


--
-- Data for Name: user; Type: TABLE DATA; Schema: jpt; Owner: postgres
--

COPY "user" (uid, password, username, email, passkey, upload_byte, download_byte, last_report, credit, referee, register_time, status) FROM stdin;
1	123456	Rugal	ryujin@163.com	123456	\N	\N	1438837127628	\N	\N	1438837127628	\N
2	123456	Spooky	null@163.com	123456	\N	\N	1438965572744	\N	\N	1438965572744	0
3	123456	Tiger	null@123.com	123456	\N	\N	1438965604092	\N	\N	1438965604092	2
6	test	test	test@123.com	test	\N	\N	1439782679846	\N	\N	1439782679846	2
7	test	test	test@123.com	test	\N	\N	1439782680097	\N	\N	1439782680097	2
\.


--
-- Name: user_uid_seq; Type: SEQUENCE SET; Schema: jpt; Owner: postgres
--

SELECT pg_catalog.setval('user_uid_seq', 35, true);


--
-- Name: admin_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (aid);


--
-- Name: client_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (cid);


--
-- Name: invitation_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invitation
    ADD CONSTRAINT invitation_pkey PRIMARY KEY (iid);


--
-- Name: level_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY level
    ADD CONSTRAINT level_pkey PRIMARY KEY (lid);


--
-- Name: post_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_pkey PRIMARY KEY (pid);


--
-- Name: post_tags_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY post_tags
    ADD CONSTRAINT post_tags_pkey PRIMARY KEY (ptid);


--
-- Name: signin_log_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY signin_log
    ADD CONSTRAINT signin_log_pkey PRIMARY KEY (slid);


--
-- Name: tag_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (tid);


--
-- Name: thread_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY thread
    ADD CONSTRAINT thread_pkey PRIMARY KEY (tid);


--
-- Name: user_pkey; Type: CONSTRAINT; Schema: jpt; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (uid);


--
-- Name: unq_level_minimum; Type: INDEX; Schema: jpt; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX unq_level_minimum ON level USING btree (minimum);


--
-- Name: admin_grantee_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY admin
    ADD CONSTRAINT admin_grantee_fkey FOREIGN KEY (grantee) REFERENCES "user"(uid);


--
-- Name: admin_uid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY admin
    ADD CONSTRAINT admin_uid_fkey FOREIGN KEY (uid) REFERENCES "user"(uid);


--
-- Name: invitation_uid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY invitation
    ADD CONSTRAINT invitation_uid_fkey FOREIGN KEY (uid) REFERENCES "user"(uid);


--
-- Name: post_tags_pid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY post_tags
    ADD CONSTRAINT post_tags_pid_fkey FOREIGN KEY (pid) REFERENCES post(pid);


--
-- Name: post_tags_tid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY post_tags
    ADD CONSTRAINT post_tags_tid_fkey FOREIGN KEY (tid) REFERENCES tag(tid);


--
-- Name: post_uid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_uid_fkey FOREIGN KEY (uid) REFERENCES "user"(uid);


--
-- Name: signin_log_uid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY signin_log
    ADD CONSTRAINT signin_log_uid_fkey FOREIGN KEY (uid) REFERENCES "user"(uid);


--
-- Name: thread_pid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY thread
    ADD CONSTRAINT thread_pid_fkey FOREIGN KEY (pid) REFERENCES post(pid);


--
-- Name: thread_uid_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY thread
    ADD CONSTRAINT thread_uid_fkey FOREIGN KEY (uid) REFERENCES "user"(uid);


--
-- Name: user_referee_fkey; Type: FK CONSTRAINT; Schema: jpt; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_referee_fkey FOREIGN KEY (referee) REFERENCES "user"(uid);


--
-- PostgreSQL database dump complete
--

