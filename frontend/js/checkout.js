import api from './api.js';

document.addEventListener('DOMContentLoaded', async () => {
    const summaryItems = document.getElementById('cart-summary-items');
    const summarySubtotal = document.getElementById('summary-subtotal');
    const summaryShipping = document.getElementById('summary-shipping');
    const summaryTotal = document.getElementById('summary-total');
    const placeOrderBtn = document.getElementById('place-order-btn');
    const paymentOptions = document.querySelectorAll('.payment-option');
    
    let selectedMethod = 'CARD';

    // Charger le panier
    try {
        const cartData = await api.get('/cart');
        displaySummary(cartData);
    } catch (error) {
        alert('Veuillez vous connecter pour voir votre panier');
        window.location.href = 'login.html';
    }

    // Sélection du mode de paiement
    paymentOptions.forEach(opt => {
        opt.addEventListener('click', () => {
            paymentOptions.forEach(o => o.classList.remove('active'));
            opt.classList.add('active');
            selectedMethod = opt.getAttribute('data-method');
        });
    });

    function displaySummary(data) {
        summaryItems.innerHTML = data.items.map(item => `
            <div class="flex gap-md" style="margin-bottom: var(--space-md);">
                <img src="${item.imageUrl || 'https://via.placeholder.com/60'}" style="width: 50px; height: 50px; border-radius: 4px; object-fit: cover;">
                <div style="flex: 1;">
                    <p style="font-size: 14px; font-weight: 600; margin-bottom: 2px;">${item.productName}</p>
                    <p style="font-size: 12px; color: var(--on-surface-variant);">Quantité: ${item.quantity}</p>
                </div>
                <span style="font-size: 14px; font-weight: 600;">${item.lineTotal} €</span>
            </div>
        `).join('');

        summarySubtotal.textContent = `${data.subtotal.toFixed(2)} €`;
        const shipping = data.subtotal > 100 ? 0 : 4.99;
        summaryShipping.textContent = shipping === 0 ? 'Gratuit' : `${shipping.toFixed(2)} €`;
        summaryTotal.textContent = `${(data.subtotal + shipping).toFixed(2)} €`;
    }

    // Passer la commande
    placeOrderBtn.addEventListener('click', async () => {
        const form = document.getElementById('checkout-form');
        const formData = new FormData(form);
        const address = Object.fromEntries(formData.entries());

        // Validation simple
        if (!address.fullName || !address.street || !address.city) {
            alert('Veuillez remplir les informations de livraison');
            return;
        }

        const requestBody = {
            paymentMethod: selectedMethod,
            deliveryType: 'HOME',
            address: address
        };

        try {
            placeOrderBtn.disabled = true;
            placeOrderBtn.textContent = 'Traitement...';
            
            const order = await api.post('/orders', requestBody);
            
            alert(`Commande #${order.orderNumber} confirmée !`);
            window.location.href = 'index.html'; // Rediriger vers accueil ou confirmation
        } catch (error) {
            alert(error.message);
            placeOrderBtn.disabled = false;
            placeOrderBtn.textContent = 'Payer maintenant';
        }
    });
});
