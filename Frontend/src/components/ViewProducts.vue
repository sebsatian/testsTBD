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
    <button class="btn btn-primary w-100 mt-3" @click="createOrderAndDetails">Crear Orden</button>
  </div>
</template>

<script>
import ProductService from "@/services/product.service";
import OrderService from "@/services/order.service";

export default {
  name: "ViewProducts",
  data() {
    return {
      products: [], // Lista de productos
    };
  },
  methods: {
    
    async fetchProducts() {
      try {
        const fetchedProducts = await ProductService.getAllProducts();
        this.products = fetchedProducts.map((product) => ({
          ...product,
          quantity: 0, // Inicializar la cantidad a comprar en 0
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
    async createOrderAndDetails() {
      const selectedProducts = this.products.filter((product) => product.quantity > 0);

      if (selectedProducts.length === 0) {
        alert("No has seleccionado productos para la orden.");
        return;
      }

      // Calcular el total
      //const total = selectedProducts.reduce((acc, item) => acc + item.quantity * item.price, 0);

      // Convertir client_id a Long
      const clientId = parseInt(localStorage.getItem("clientId"), 10);

      const orderData = {
        date: new Date().toISOString().slice(0, 19).replace("T", " "),
        status: "zzz",
        total: 0,
        client_id: clientId,
      };

      try {
        // Crear la orden y obtener el ID generado
        const orderId = await OrderService.createOrder(orderData);

        console.log("ID de la orden creada:", orderId);

        // Crear detalles de la orden para cada producto seleccionado
        for (const product of selectedProducts) {
          const orderDetail = {
            order_id: orderId,
            product_id: product.product_id,
            quantity: product.quantity,
            price: product.price,
          };

          await OrderService.createOrderDetail(orderDetail); // Llamar al servicio de detalles
        }
        const newStatus = "Pendiente"; 
        await OrderService.updateOrderStatus(orderId, newStatus);

        alert("Orden y detalles creados exitosamente.");
        this.$router.push("/clientpage/orders"); // Redirigir a la página de órdenes
      } catch (error) {
        console.error("Error al crear la orden o sus detalles:", error.response?.data || error.message);
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

<style scoped>
.products-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

.table th,
.table td {
  text-align: center;
  vertical-align: middle;
}

.quantity-selector {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.quantity {
  font-size: 1.2rem;
  font-weight: bold;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-success {
  background-color: #28a745;
  border-color: #28a745;
}
</style>
