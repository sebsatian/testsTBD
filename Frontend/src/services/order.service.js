import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class OrderService {
  async createOrder(orderDetails) {
    try {
      const clientId = localStorage.getItem("clientId");
      if (!clientId) throw new Error("No se encontró el clientId en el localStorage");

      // Crear la orden
      const orderData = {
        client_id: clientId,
        estado: "pendiente",
        total: orderDetails.reduce((acc, item) => acc + item.quantity * item.price, 0), // Calcula el total
      };

      const orderResponse = await axios.post(`${API_URL}/order/create`, orderData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      const orderId = orderResponse.data.id_orden;

      // Crear los detalles de la orden
      const detailsRequests = orderDetails.map((detail) =>
        axios.post(
          `${API_URL}/order-detail/create`,
          {
            id_orden: orderId,
            id_producto: detail.product_id,
            cantidad: detail.quantity,
            precio_unitario: detail.price,
          },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
            },
          }
        )
      );

      // Esperar que todas las solicitudes de detalles se completen
      await Promise.all(detailsRequests);

      return { orderId };
    } catch (error) {
      console.error("Error al crear la orden con detalles:", error.response?.data || error.message);
      throw error;
    }
  }
}

export default new OrderService();
