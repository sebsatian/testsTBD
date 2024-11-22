import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class RegisterService {
  async register(name, email, password) {
    try {
      const response = await axios.post(
        `${API_URL}/auth/register`,
        {
          name,
          email,
          password
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al registrar el usuario:", error);
      throw error; // Esto permitirá manejar el error desde donde se llama este método
    }
  }
  
  async completeRegistration(clientId, address, phoneNumber) {
    try {
      const response = await axios.put(
        `${API_URL}/client/complete-registration/${clientId}`,
        {
          address,
          phoneNumber,
        },
        {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`,
            "Content-Type": "application/json",
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al completar el registro:", error);
      throw error;
    }
  }
}

class LoginService {
  async login(clientname, password) {
    // Crear el objeto de datos a enviar
    const data = { clientname, password };

    // Imprimir el JSON antes de enviarlo
    console.log("Datos enviados al backend:", JSON.stringify(data, null, 2));

    try {
      // Realizar la solicitud al backend
      const response = await axios.post(`${API_URL}/auth/login`, data);

      // Extraer token y clientId desde la respuesta
      const { token, clientId } = response.data;

      // Guardar en localStorage
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("clientId", clientId);

      // Retornar los datos si se necesitan en el componente
      return { token, clientId };
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
      throw error;
    }
  }

  async checkToken() {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.post(
        `${API_URL}/auth/check-token`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data; // Si el token es válido, devuelve los datos
    } catch (error) {
      console.error("Error al verificar el token:", error);
      throw error;
    }
  }
}

// Función independiente para validar la sesión
async function validateSession() {
  const loginService = new LoginService();
  try {
    await loginService.checkToken(); // Llama al método para validar el token
    return true; // Si el token es válido, continúa
  } catch (error) {
    alert("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
    localStorage.removeItem("jwtToken"); // Limpia el token si es inválido
    localStorage.removeItem("clientId"); // Limpia el clientId si es inválido
    window.location.href = "/"; // Redirige a la página principal
    return false; // Bloquea el acceso
  }
}

export const registerService = new RegisterService();
export const loginService = new LoginService();
export { validateSession };
