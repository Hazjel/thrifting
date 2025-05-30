DROP TABLE IF EXISTS produk;

CREATE TABLE produk (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    kategori VARCHAR(255) NOT NULL,
    jumlah INT NOT NULL,
    harga DECIMAL(10, 1) NOT NULL
);
 
INSERT INTO produk (nama, kategori, jumlah, harga)
VALUES 
    ('Laptop', 'Elektronik', 10, 4500000),
    ('Mousepad', 'Elektronik', 30, 300000),
    ('Mouse', 'Elektronik', 15, 1000000);
    
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    passwords VARCHAR(255) NOT NULL
);
 
INSERT INTO users (nama, email, username, passwords)
VALUES 
    ('Rusdi', "rusdiganteng@email.com", "dirusdi", SHA2("12345", 256)),
    ('Mamat', "mamatresing@email.com", "matmamat", SHA2("12345", 256)),
    ('Imut', "imutimoetz@email.com", "mutimut", SHA2("12345", 256));    
    
select * from users;
select * from produk;
