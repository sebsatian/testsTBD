import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class OrderService {
  async createOrder(orderData) {
    try {
      console.log("Datos de la orden enviados al backend:", JSON.stringify(orderData, null, 2));

      const response = await axios.post(`${API_URL}/order/new-order`, orderData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      console.log("Respuesta del backend al crear la orden:", response.data);
    } catch (error) {
      console.error("Error al crear la orden:", error.response?.data || error.message);
      throw error;
    }
  }
}

export default new OrderService();
