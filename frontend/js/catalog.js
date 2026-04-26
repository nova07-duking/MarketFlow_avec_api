import api from './api.js';

document.addEventListener('DOMContentLoaded', () => {
    const productGrid = document.getElementById('product-grid');
    const resultsCount = document.getElementById('results-count');
    const searchInput = document.getElementById('search-input');
    
    // Charger les produits au démarrage
    loadProducts();

    // Recherche
    let searchTimeout;
    searchInput.addEventListener('input', (e) => {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            loadProducts(e.target.value);
        }, 500);
    });

    async function loadProducts(query = '') {
        try {
            productGrid.innerHTML = '<div style="grid-column: 1/-1; text-align: center; padding: 50px;">Chargement...</div>';
            
            const endpoint = query ? `/products/search?q=${query}` : '/products';
            const data = await api.get(endpoint);
            
            displayProducts(data.content);
            resultsCount.textContent = `${data.totalElements} solutions logistiques trouvées`;
        } catch (error) {
            productGrid.innerHTML = `<div style="grid-column: 1/-1; text-align: center; color: red; padding: 50px;">${error.message}</div>`;
        }
    }

    function displayProducts(products) {
        if (products.length === 0) {
            productGrid.innerHTML = '<div style="grid-column: 1/-1; text-align: center; padding: 50px;">Aucun produit trouvé.</div>';
            return;
        }

        productGrid.innerHTML = products.map(product => `
            <div class="product-card">
                <div style="position: relative;">
                    <img src="${product.imageUrls && product.imageUrls[0] ? product.imageUrls[0] : 'https://via.placeholder.com/300'}" class="product-image" alt="${product.name}">
                    <div style="position: absolute; top: 10px; left: 10px;">
                        ${product.sourcingModel === 'LOCAL' 
                            ? '<span class="badge badge-local">Stock Local</span>' 
                            : '<span class="badge badge-groupage">Prêt Groupage</span>'}
                    </div>
                </div>
                <div style="padding: var(--space-md);">
                    <p style="font-size: 12px; color: var(--on-surface-variant); margin-bottom: 4px;">${product.categoryName}</p>
                    <h4 style="font-size: 16px; font-weight: 600; margin-bottom: 8px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${product.name}</h4>
                    <div class="flex items-center gap-sm" style="margin-bottom: var(--space-md);">
                        <span style="font-size: 18px; font-weight: 700; color: var(--primary);">${product.price} €</span>
                        ${product.originalPrice ? `<span style="text-decoration: line-through; color: var(--on-surface-variant); font-size: 14px;">${product.originalPrice} €</span>` : ''}
                    </div>
                    <button class="add-to-cart-btn" data-id="${product.id}" style="width: 100%; background: var(--primary); color: white; padding: 12px; border-radius: var(--radius-lg); font-weight: 600; display: flex; align-items: center; justify-content: center; gap: 8px;">
                        <span class="material-symbols-outlined" style="font-size: 20px;">shopping_cart</span>
                        Ajouter au panier
                    </button>
                </div>
            </div>
        `).join('');

        // Attacher les événements au clic sur "Ajouter au panier"
        document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const productId = btn.getAttribute('data-id');
                addToCart(productId);
            });
        });
    }

    async function addToCart(productId) {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                window.location.href = 'login.html';
                return;
            }

            await api.post(`/cart/items?productId=${productId}&quantity=1`);
            alert('Produit ajouté au panier !');
            // Mettre à jour le compteur du panier (à implémenter)
        } catch (error) {
            alert(error.message);
        }
    }
});
