import api from './api.js';

document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('inventory-table-body');
    const modal = document.getElementById('product-modal');
    const addBtn = document.getElementById('add-product-btn');
    const closeBtn = document.getElementById('close-modal-btn');
    const productForm = document.getElementById('product-form');
    const logoutBtn = document.getElementById('logout-btn');

    // Vérifier l'auth
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || user.role !== 'SELLER') {
        window.location.href = 'login.html';
        return;
    }

    loadDashboard();

    async function loadDashboard() {
        try {
            // Stats
            const stats = await api.get('/dashboard/stats');
            document.getElementById('stat-sales').textContent = `${stats.totalSales.toFixed(2)} €`;
            document.getElementById('stat-views').textContent = stats.productViews;
            document.getElementById('stat-listings').textContent = stats.activeListings;

            // Produits
            const data = await api.get('/products'); // Normalement filtré par vendeur, mais pour la démo on prend tout
            displayProducts(data.content);
        } catch (error) {
            console.error(error);
        }
    }

    function displayProducts(products) {
        tableBody.innerHTML = products.map(p => `
            <tr>
                <td class="flex items-center gap-md">
                    <img src="${p.imageUrls[0] || 'https://via.placeholder.com/40'}" style="width: 40px; height: 40px; border-radius: 4px; object-fit: cover;">
                    <span>${p.name}</span>
                </td>
                <td><span class="badge badge-local" style="background: #e6f4ea; color: #1e7e34;">Actif</span></td>
                <td>${p.price} €</td>
                <td style="color: ${p.stock < 10 ? '#ba1a1a' : 'inherit'}; font-weight: ${p.stock < 10 ? '700' : '400'};">
                    ${p.stock} unités
                </td>
                <td>${p.totalSales}</td>
                <td>
                    <button class="material-symbols-outlined" style="color: var(--on-surface-variant); font-size: 20px;">edit</button>
                    <button class="material-symbols-outlined" style="color: #ba1a1a; font-size: 20px; margin-left: 10px;">delete</button>
                </td>
            </tr>
        `).join('');
    }

    // Modal
    addBtn.onclick = () => modal.style.display = 'flex';
    closeBtn.onclick = () => modal.style.display = 'none';

    // Ajout Produit
    productForm.onsubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData(productForm);
        
        // On crée un blob JSON pour le DTO (ProductCreateRequest)
        const productDto = {
            name: formData.get('name'),
            price: parseFloat(formData.get('price')),
            stock: parseInt(formData.get('stock')),
            sourcingModel: formData.get('sourcingModel'),
            categoryId: 1 // Temporaire pour la démo
        };

        const finalFormData = new FormData();
        finalFormData.append('product', new Blob([JSON.stringify(productDto)], { type: 'application/json' }));
        
        const files = formData.getAll('images');
        files.forEach(file => {
            if (file.size > 0) finalFormData.append('images', file);
        });

        try {
            await api.upload('/products', finalFormData);
            alert('Produit ajouté !');
            modal.style.display = 'none';
            loadDashboard();
        } catch (error) {
            alert(error.message);
        }
    };

    logoutBtn.onclick = () => {
        localStorage.clear();
        window.location.href = 'index.html';
    };
});
