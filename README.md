# 🚀 MarketFlow — Plateforme E-commerce & Groupage Professionnelle

MarketFlow est une solution e-commerce complète, moderne et sécurisée, conçue pour offrir une expérience d'achat fluide tout en intégrant des fonctionnalités avancées de logistique et de groupage.

![Banner](https://images.unsplash.com/photo-1557821552-17105176677c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80)

## ✨ Fonctionnalités Clés

### 🛍️ Pour les Acheteurs
- **Authentification Sécurisée** : Connexion par code OTP envoyé par email (Gmail).
- **Catalogue Dynamique** : Navigation fluide, recherche par mots-clés et filtres par catégories/prix.
- **Panier Interactif** : Gestion en temps réel des articles, quantités, tailles et couleurs.
- **Checkout Optimisé** : Processus de paiement simulé avec choix du mode de livraison.
- **Suivi de Commande** : Interface dédiée pour suivre l'état de ses achats en temps réel.

### 🏪 Pour les Vendeurs
- **Dashboard Analytique** : Vue d'ensemble des ventes totales, visites et produits actifs.
- **Gestion des Produits** : Interface complète (CRUD) pour ajouter, modifier ou supprimer des produits avec images.
- **Logistique de Groupage** : Système innovant pour regrouper les expéditions par secteur et optimiser les coûts.

### 🛡️ Sécurité & Performance
- **Sécurité JWT** : Authentification stateless via JSON Web Tokens.
- **Protection des Données** : Isolation des secrets via variables d'environnement (`.env`).
- **Design System "Stitch"** : Interface premium, responsive et animée (Vanilla CSS).

## 🛠️ Stack Technique

- **Backend** : Java 17, Spring Boot 3, Spring Security, Hibernate/JPA.
- **Frontend** : HTML5, Vanilla JavaScript, CSS3 (Design System Stitch).
- **Base de données** : MySQL 8.
- **Conteneurisation** : Docker & Docker Compose.
- **Envoi d'Emails** : SMTP Gmail.

## 🚀 Installation & Lancement

### Pré-requis
- [Docker Desktop](https://www.docker.com/products/docker-desktop) installé et lancé.
- Un fichier `.env` à la racine (déjà préparé).

### Lancement Rapide
1. Clonez le dépôt (si ce n'est pas déjà fait).
2. Lancez l'infrastructure complète avec une seule commande :
   ```bash
   docker-compose up --build
   ```
3. Accédez à l'application :
   - **Frontend** : `http://localhost` (ou ouvrez `index.html` directement).
   - **Backend API** : `http://localhost:8080`.
   - **Swagger UI** : `http://localhost:8080/swagger-ui/index.html`.

## 📦 Structure du Projet
```text
├── backend/            # Code source Spring Boot (API)
├── frontend/           # Interface client (HTML/JS/CSS)
├── docker-compose.yml  # Orchestration des conteneurs
├── .env                # Secrets (non versionné sur Git)
└── data.sql           # Données de démonstration
```

## 📝 Auteur
Développé avec passion pour l'écosystème **MarketFlow**.
