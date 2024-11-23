<template>
    <div class="orders-container">
      <h2 class="text-center mb-4">Mis Órdenes</h2>
  
      <!-- Tabla de órdenes -->
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Total</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.order_id">
            <td>{{ order.order_id }}</td>
            <td>{{ order.date }}</td>
            <td>{{ formatCurrency(order.total) }}</td>
            <td>{{ order.status }}</td>
            <td>
              <button
                class="btn btn-primary btn-sm me-2"
                @click="viewOrderDetails(order.order_id)"
              >
                Ver Detalles
              </button>
              <button
                class="btn btn-danger btn-sm"
                @click="deleteOrder(order.order_id)"
              >
                Eliminar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- Modal para mostrar detalles de la orden -->
      <div
        v-if="selectedOrderDetails"
        class="modal fade show"
        tabindex="-1"
        role="dialog"
        style="display: block; background: rgba(0,0,0,0.5);"
      >
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Detalles de la Orden</h5>
              <button type="button" class="btn-close" @click="clearDetails"></button>
            </div>
            <div class="modal-body">
              <table class="table table-bordered">
                <thead>
                  <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                    <th>Total</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="detail in selectedOrderDetails" :key="detail.id">
                    <td>{{ detail.product_name }}</td>
                    <td>{{ detail.quantity }}</td>
                    <td>{{ formatCurrency(detail.price) }}</td>
                    <td>{{ formatCurrency(detail.quantity * detail.price) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="clearDetails">Cerrar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import OrderService from "@/services/order.service";
  
  export default {
    name: "ViewOrders",
    data() {
      return {
        orders: [], // Lista de órdenes
        selectedOrderDetails: null, // Detalles de la orden seleccionada
      };
    },
    methods: {
      async fetchOrders() {
        try {
          const clientId = localStorage.getItem("clientId");
          if (!clientId) {
            alert("No se encontró el ID del cliente en el localStorage.");
            return;
          }
          const orders = await OrderService.getOrdersByClientId(clientId);
          this.orders = orders.map((order) => ({
            ...order,
            total: order.total || 0,
          }));
        } catch (error) {
          console.error("Error al obtener las órdenes:", error.response?.data || error.message);
          alert("Hubo un error al cargar las órdenes. Intenta nuevamente más tarde.");
        }
      },
      async viewOrderDetails(orderId) {
        try {
          const details = await OrderService.getOrderDetailsByOrderId(orderId);
          this.selectedOrderDetails = details;
        } catch (error) {
          console.error("Error al obtener los detalles de la orden:", error.response?.data || error.message);
          alert("Hubo un error al cargar los detalles de la orden. Intenta nuevamente más tarde.");
        }
      },
      async deleteOrder(orderId) {
        try {
          const confirmation = confirm(`¿Estás seguro de que deseas eliminar la orden con ID: ${orderId}?`);
          if (!confirmation) return;
  
          await OrderService.deleteOrderById(orderId);
          this.fetchOrders(); // Actualizar la lista después de eliminar
        } catch (error) {
          console.error("Error al eliminar la orden:", error.response?.data || error.message);
          alert("Hubo un error al intentar eliminar la orden. Intenta nuevamente más tarde.");
        }
      },
      clearDetails() {
        this.selectedOrderDetails = null;
      },
      formatCurrency(value) {
        if (value === null || value === undefined) return "$0.00";
        return `$${value.toFixed(2)}`;
      },
    },
    mounted() {
      this.fetchOrders();
    },
  };
  </script>
  
  <style scoped>
/* Estilo general para contenedor */
.orders-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Títulos */
h2 {
  color: #333;
  margin-bottom: 20px;
}

/* Estilo general para botones */
button.btn {
  font-size: 14px;
  font-weight: 600;
  padding: 8px 12px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
}
button.btn:not(:last-child) {
  margin-right: 15px; /* Aumenta el margen a la derecha de todos los botones menos el último */
}

/* Botón primario (Ver detalles) */
button.btn-primary {
  background-color: #007bff;
  color: #fff;
}

button.btn-primary:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Botón peligro (Eliminar) */
button.btn-danger {
  background-color: #dc3545;
  color: #fff;
}

button.btn-danger:hover {
  background-color: #a71d2a;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Botón cerrar en el modal */
button.btn-secondary {
  background-color: #6c757d;
  color: #fff;
}

button.btn-secondary:hover {
  background-color: #5a6268;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Espaciado entre botones en la tabla */
button.me-2 {
  margin-right: 8px;
}

/* Tabla */
.table th,
.table td {
  text-align: center;
  vertical-align: middle;
}

/* Modal */
.modal-content {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px;
}
</style>

  