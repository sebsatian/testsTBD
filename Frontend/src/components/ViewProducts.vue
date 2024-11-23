<template>
  <div class="products-container">
    <h2 class="text-center mb-4">Productos Disponibles</h2>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Nombre del Producto</th>
          <th>Stock Disponible</th>
          <th>Precio</th>
          <th>Cantidad a Comprar</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.product_id">
          <td>{{ product.product_name }}</td>
          <td>{{ product.stock }}</td>
          <td>{{ formatCurrency(product.price) }}</td>
          <td>
            <div class="quantity-selector">
              <button
                class="btn btn-danger btn-sm"
                @click="decreaseQuantity(product)"
                :disabled="product.quantity === 0"
              >
                -
              </button>
              <span class="quantity">{{ product.quantity }}</span>
              <button
                class="btn btn-success btn-sm"
                @click="increaseQuantity(product)"
                :disabled="product.quantity >= product.stock"
              >
                +
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <button class="btn btn-primary w-100 mt-3" @click="createOrder">Crear Orden</button>
  </div>
</template>

<script>
import ProductService from "@/services/product.service";
import OrderService from "@/services/order.service";

export default {
  name: "ViewProducts",
  data() {
    return {
      products: [],
    };
  },
  methods: {
    async fetchProducts() {
      try {
        const fetchedProducts = await ProductService.getAllProducts();
        this.products = fetchedProducts.map((product) => ({
          ...product,
          quantity: 0,
        }));
      } catch (error) {
        console.error("Error al obtener los productos:", error.response?.data || error.message);
        alert("Error al cargar los productos. Intenta nuevamente más tarde.");
      }
    },
    increaseQuantity(product) {
      if (product.quantity < product.stock) {
        product.quantity++;
      }
    },
    decreaseQuantity(product) {
      if (product.quantity > 0) {
        product.quantity--;
      }
    },
    async createOrder() {
      const selectedProducts = this.products.filter((product) => product.quantity > 0);

      if (selectedProducts.length === 0) {
        alert("No has seleccionado productos para la orden.");
        return;
      }

      const total = selectedProducts.reduce((acc, item) => acc + item.quantity * item.price, 0);

      // Convertir client_id a Long
      const clientId = parseInt(localStorage.getItem("clientId"), 10);

      const orderData = {
        date: new Date().toISOString().slice(0, 19).replace("T", " "),
        status: "pendiente",
        total: total,
        client_id: clientId,
      };

      try {
        await OrderService.createOrder(orderData);
        alert("Orden creada exitosamente.");
        this.$router.push("/clientpage/orders");
      } catch (error) {
        console.error("Error al crear la orden:", error.response?.data || error.message);
        alert("Hubo un error al crear la orden. Intenta nuevamente más tarde.");
      }
    },
    formatCurrency(value) {
      return `$${value.toFixed(2)}`;
    },
  },
  mounted() {
    this.fetchProducts();
  },
};
</script>
