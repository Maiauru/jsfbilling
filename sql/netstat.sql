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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    uid integer NOT NULL,
    gid integer NOT NULL,
    description character varying(50) DEFAULT NULL::character varying,
    enabled boolean DEFAULT false,
    still boolean DEFAULT false,
    net smallint DEFAULT (0)::smallint NOT NULL,
    addr smallint DEFAULT (0)::smallint NOT NULL,
    ballans numeric(10,2) DEFAULT (0)::numeric,
    price numeric(10,2) DEFAULT (0)::numeric
);


--
-- Name: get_user(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_user(integer) RETURNS users
    LANGUAGE sql
    AS $_$
SELECT * FROM users WHERE uid = $1;
$_$;


--
-- Name: update_access(integer, integer, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION update_access(integer, integer, character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
DECLARE
  net_val    ALIAS FOR $1;
  addr_val   ALIAS FOR $2;
  host       ALIAS FOR $3;
  user_id    int4;
  host_id    int4;
  hit_count  int4;
BEGIN
  SELECT 0 INTO user_id;
  SELECT 0 INTO host_id;
  SELECT uid INTO user_id FROM users WHERE (net=net_val) AND (addr=addr_val);
  IF FOUND THEN
    SELECT hid INTO host_id FROM hosts WHERE (hname LIKE host);
    IF NOT FOUND THEN
        INSERT INTO hosts(hhit, hname) VALUES(1, host);
        SELECT MAX(hid) INTO host_id FROM hosts; 
    END IF;
    SELECT count INTO hit_count FROM access WHERE (uid=user_id) AND (hid=host_id) AND (adate = current_date);
    IF NOT FOUND THEN
        INSERT INTO access(uid, hid) VALUES(user_id, host_id);
    ELSE
      hit_count = hit_count + 1;
      UPDATE access SET count = hit_count WHERE (uid=user_id) AND (hid=host_id) AND (adate = current_date);
    END IF;
  ELSE
  RETURN 1;
  END IF;
  UPDATE hosts SET hhit=(SELECT COUNT(*) FROM access WHERE (access.hid = hosts.hid));
  RETURN 0;
END;
$_$;


--
-- Name: access; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE access (
    aid integer NOT NULL,
    uid integer NOT NULL,
    hid integer NOT NULL,
    adate date DEFAULT ('now'::text)::date NOT NULL,
    count integer DEFAULT 1 NOT NULL
);


--
-- Name: access_aid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE access_aid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: access_aid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE access_aid_seq OWNED BY access.aid;


--
-- Name: daily; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE daily (
    did integer NOT NULL,
    dtime time without time zone DEFAULT now() NOT NULL,
    uid integer NOT NULL,
    bytes numeric(12,0) DEFAULT (0)::numeric
);


--
-- Name: daily_did_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE daily_did_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: daily_did_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE daily_did_seq OWNED BY daily.did;


--
-- Name: groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE groups (
    gid integer NOT NULL,
    description character varying(50) DEFAULT NULL::character varying
);


--
-- Name: groups_gid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE groups_gid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: groups_gid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE groups_gid_seq OWNED BY groups.gid;


--
-- Name: hosts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE hosts (
    hid integer NOT NULL,
    hhit integer DEFAULT 0 NOT NULL,
    hname character varying(256)
);


--
-- Name: hosts_hid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hosts_hid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: hosts_hid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE hosts_hid_seq OWNED BY hosts.hid;


--
-- Name: mounthly; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mounthly (
    mid integer NOT NULL,
    mdate date DEFAULT now() NOT NULL,
    uid integer NOT NULL,
    bytes numeric(12,0) DEFAULT (0)::numeric
);


--
-- Name: mounthly_mid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mounthly_mid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: mounthly_mid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mounthly_mid_seq OWNED BY mounthly.mid;


--
-- Name: stats; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW stats AS
 SELECT users.uid,
    users.enabled,
    users.net,
    users.addr,
    users.ballans,
    users.gid,
    groups.description AS groupname,
    users.description AS username,
    sum(daily.bytes) AS daily,
    sum(mounthly.bytes) AS mounthly
   FROM users,
    groups,
    daily,
    mounthly
  WHERE (users.gid = groups.gid)
  GROUP BY users.uid, users.enabled, users.net, users.addr, users.ballans, users.gid, users.description, groups.description;


--
-- Name: users_uid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_uid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: users_uid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_uid_seq OWNED BY users.uid;


--
-- Name: yearly; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE yearly (
    yid integer NOT NULL,
    ynum integer DEFAULT 0 NOT NULL,
    uid integer NOT NULL,
    bytes numeric(12,0) DEFAULT (0)::numeric
);


--
-- Name: yearly_yid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE yearly_yid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: yearly_yid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE yearly_yid_seq OWNED BY yearly.yid;


--
-- Name: aid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY access ALTER COLUMN aid SET DEFAULT nextval('access_aid_seq'::regclass);


--
-- Name: did; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY daily ALTER COLUMN did SET DEFAULT nextval('daily_did_seq'::regclass);


--
-- Name: gid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups ALTER COLUMN gid SET DEFAULT nextval('groups_gid_seq'::regclass);


--
-- Name: hid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY hosts ALTER COLUMN hid SET DEFAULT nextval('hosts_hid_seq'::regclass);


--
-- Name: mid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY mounthly ALTER COLUMN mid SET DEFAULT nextval('mounthly_mid_seq'::regclass);


--
-- Name: uid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN uid SET DEFAULT nextval('users_uid_seq'::regclass);


--
-- Name: yid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY yearly ALTER COLUMN yid SET DEFAULT nextval('yearly_yid_seq'::regclass);


--
-- Name: access_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY access
    ADD CONSTRAINT access_pkey PRIMARY KEY (aid);


--
-- Name: daily_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY daily
    ADD CONSTRAINT daily_pkey PRIMARY KEY (did);


--
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (gid);


--
-- Name: hosts_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY hosts
    ADD CONSTRAINT hosts_pkey PRIMARY KEY (hid);


--
-- Name: mounthly_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mounthly
    ADD CONSTRAINT mounthly_pkey PRIMARY KEY (mid);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (uid);


--
-- Name: yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY yearly
    ADD CONSTRAINT yearly_pkey PRIMARY KEY (yid);


--
-- Name: access_hid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access
    ADD CONSTRAINT access_hid_fkey FOREIGN KEY (hid) REFERENCES hosts(hid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: access_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access
    ADD CONSTRAINT access_uid_fkey FOREIGN KEY (uid) REFERENCES users(uid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: daily_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY daily
    ADD CONSTRAINT daily_uid_fkey FOREIGN KEY (uid) REFERENCES users(uid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: mounthly_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mounthly
    ADD CONSTRAINT mounthly_uid_fkey FOREIGN KEY (uid) REFERENCES users(uid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: users_gid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_gid_fkey FOREIGN KEY (gid) REFERENCES groups(gid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: yearly_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY yearly
    ADD CONSTRAINT yearly_uid_fkey FOREIGN KEY (uid) REFERENCES users(uid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

