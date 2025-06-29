-- DROP DATABASE IF EXISTS shadowmail;

CREATE DATABASE shadowmail

CREATE TABLE IF NOT EXISTS public.disposable_addresses
(
    id uuid NOT NULL,
    email_address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    expires_at timestamp without time zone NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    CONSTRAINT disposable_addresses_pkey PRIMARY KEY (id),
    CONSTRAINT disposable_addresses_email_address_key UNIQUE (email_address)
)


CREATE TABLE IF NOT EXISTS public.received_email
(
    id bigint NOT NULL DEFAULT nextval('received_email_id_seq'::regclass),
    disposable_id uuid,
    from_user character varying(255) COLLATE pg_catalog."default" NOT NULL,
    to_user character varying(255) COLLATE pg_catalog."default" NOT NULL,
    subject character varying(255) COLLATE pg_catalog."default",
    body character varying(255) COLLATE pg_catalog."default",
    received_at timestamp without time zone NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT received_email_pkey PRIMARY KEY (id),
    CONSTRAINT emails_disposable_id_fkey FOREIGN KEY (disposable_id)
        REFERENCES public.disposable_addresses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
