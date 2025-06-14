-- Struktur tabel untuk produk
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT,
    photo VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Struktur tabel untuk riwayat pembelian
CREATE TABLE IF NOT EXISTS purchase_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT,
    product_name VARCHAR(255) NOT NULL,
    product_price DOUBLE NOT NULL,
    product_category VARCHAR(50) NOT NULL,
    product_photo VARCHAR(255),
    buyer_name VARCHAR(100) NOT NULL,
    buyer_email VARCHAR(100) NOT NULL,
    buyer_phone VARCHAR(20) NOT NULL,
    buyer_address TEXT NOT NULL,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indeks untuk mempercepat pencarian
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_purchase_history_user_id ON purchase_history(user_id);
CREATE INDEX idx_purchase_history_date ON purchase_history(purchase_date);
