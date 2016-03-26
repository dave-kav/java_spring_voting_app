 CREATE TABLE candidates(
  First_name	VARCHAR(13),
  Second_name	VARCHAR(20),
  Party			VARCHAR(29),
  Constituency	VARCHAR(20)
);

 CREATE TABLE votes(
  First_name	VARCHAR(13),
  Second_name	VARCHAR(20),
  score			int(11),
  Constituency	VARCHAR(20),
  PRIMARY KEY (First_name,Second_name)
);