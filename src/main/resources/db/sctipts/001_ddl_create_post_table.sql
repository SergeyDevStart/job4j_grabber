CREATE TABLE post (
	id serial PRIMARY KEY,
	name text,
	text text,
	link text UNIQUE,
	created timestamp
);