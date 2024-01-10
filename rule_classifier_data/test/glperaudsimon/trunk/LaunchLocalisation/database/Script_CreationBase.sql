CREATE TABLE Pays(
id INT NOT NULL IDENTITY,
nom VARCHAR(50) NOT NULL,
CONSTRAINT PkPays PRIMARY KEY(id))

CREATE TABLE Ville(
id INT NOT NULL IDENTITY,
nom VARCHAR(50) NOT NULL,
pays_id INT NOT NULL,
CONSTRAINT PkVille PRIMARY KEY(id),
CONSTRAINT FkVillePays FOREIGN KEY (pays_id) REFERENCES Pays(Id))