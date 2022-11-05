create database predict_game;

use predict_game;

create table users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id UUID unique not null,
    username text unique not null,
    email text unique not null,
    first_name text not null,
    last_name text not null,
    created_at timestamptz not null default now()
);

BEGIN TRANSACTION;
ALTER TABLE "public"."predict_game" ADD CONSTRAINT "user_fk" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;
