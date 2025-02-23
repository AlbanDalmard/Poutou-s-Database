- 2 entreprises de e-commerce fusionnent
- 2 tables clients à fusionner
- A -> MySQL
- B -> Oracle

# Opérations
- Ajouter client
- Recherche client
  - Par nom
  - Par email
  - Par téléphone
  - Par pays
  - ville
- Supprimer client
- Ajouter transaction
- Recherche transaction

# Attributs de chaque table
- Nom
- Email
- Téléphone
- Pays
- Ville
- Id
- Historique_de_transactions


# Création de la table sous mySQL

CREATE TABLE Client (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Nom VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Téléphone VARCHAR(15) NOT NULL,
    Pays VARCHAR(50) NOT NULL,
    Ville VARCHAR(50) NOT NULL,
    Historique_de_transactions TEXT,
    UNIQUE (Email)
);


# Création de la table sous Oracle

CREATE TABLE Client (
    Id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    Nom VARCHAR2(100) NOT NULL,
    Email VARCHAR2(100) NOT NULL,
    Téléphone VARCHAR2(15) NOT NULL,
    Pays VARCHAR2(50) NOT NULL,
    Ville VARCHAR2(50) NOT NULL,
    Historique_de_transactions CLOB,
    CONSTRAINT unique_email UNIQUE (Email)
);

# Remplissage des tables

#MySQL

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('John Doe', 'john.doe@example.com', '123-456-7890', 'USA', 'New York', 'Transaction1: 1000 USD, Transaction2: 500 USD');

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('Jane Smith', 'jane.smith@example.com', '234-567-8901', 'Canada', 'Toronto', 'Transaction1: 2000 CAD, Transaction2: 1500 CAD');

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('Alice Johnson', 'alice.johnson@example.com', '345-678-9012', 'UK', 'London', 'Transaction1: 1000 GBP, Transaction2: 700 GBP');

# Oracle

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('Robert Brown', 'robert.brown@example.com', '456-789-0123', 'Australia', 'Sydney', 'Transaction1: 3000 AUD, Transaction2: 2500 AUD');

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('Maria Garcia', 'maria.garcia@example.com', '567-890-1234', 'Spain', 'Madrid', 'Transaction1: 1500 EUR, Transaction2: 1200 EUR');

INSERT INTO Client (Nom, Email, Téléphone, Pays, Ville, Historique_de_transactions)
VALUES ('Li Wei', 'li.wei@example.com', '678-901-2345', 'China', 'Beijing', 'Transaction1: 8000 CNY, Transaction2: 7000 CNY');

---
# lancer scalardb
```
sudo docker-compose up -d
java -jar scalardb-schema-loader-3.9.6.jar --config database.properties --schema-file schema.json --coordinator
```
pour fermer mysql
```
sudo systemctl stop  mysql
```
pour fermer scalardb
```
sudo docker-compose down
```
pour voir les processus qui utilisent le port 3306
```
sudo lsof -i :3306
```