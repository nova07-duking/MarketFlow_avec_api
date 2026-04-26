-- Insertion des catégories de base
INSERT IGNORE INTO categories (name, slug, icon) VALUES ('Systèmes Industriels', 'industriel', 'factory');
INSERT IGNORE INTO categories (name, slug, icon) VALUES ('Matériel Informatique', 'tech', 'computer');
INSERT IGNORE INTO categories (name, slug, icon) VALUES ('Maintenance', 'maintenance', 'build');
INSERT IGNORE INTO categories (name, slug, icon) VALUES ('Équipement Sécurité', 'securite', 'shield');
INSERT IGNORE INTO categories (name, slug, icon) VALUES ('Emballage', 'emballage', 'package');

-- Création d'un utilisateur vendeur de test (Mot de passe: dummy car c'est OTP)
INSERT IGNORE INTO users (email, full_name, phone, role, verified, created_at) 
VALUES ('vendeur@test.com', 'Alex Rivers', '+33600000000', 'SELLER', true, NOW());

-- Création d'un utilisateur acheteur de test
INSERT IGNORE INTO users (email, full_name, phone, role, verified, created_at) 
VALUES ('acheteur@test.com', 'Jean Dupont', '+33611111111', 'BUYER', true, NOW());

-- Création des paniers pour les utilisateurs de test
INSERT IGNORE INTO carts (user_id, updated_at) VALUES (1, NOW());
INSERT IGNORE INTO carts (user_id, updated_at) VALUES (2, NOW());

-- Insertion de quelques produits de démo liés au vendeur de test (ID 1)
INSERT IGNORE INTO products (name, description, price, original_price, stock, sourcing_model, active, seller_id, category_id, total_sales, total_views, created_at)
VALUES ('Unités de rayonnage modulaires', 'Système de stockage industriel haute résistance.', 499.00, 620.00, 42, 'LOCAL', true, 1, 1, 128, 1500, NOW());

INSERT IGNORE INTO products (name, description, price, stock, sourcing_model, active, seller_id, category_id, total_sales, total_views, created_at)
VALUES ('Processeur Quantum Series', 'Puce de traitement haute performance pour serveurs.', 1250.00, 3, 'GROUPAGE', true, 1, 2, 8, 894, NOW());

INSERT IGNORE INTO products (name, description, price, stock, sourcing_model, active, seller_id, category_id, total_sales, total_views, created_at)
VALUES ('Kit de clés dynamométriques', 'Outils de précision pour maintenance industrielle.', 189.99, 15, 'LOCAL', true, 1, 3, 54, 450, NOW());
