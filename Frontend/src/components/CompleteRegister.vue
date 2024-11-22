<template>
    <div class="register-container">
      <h2 class="text-center mb-4">Completar Registro</h2>
      <form @submit.prevent="submitDetails" class="register-form">
        <div class="mb-3">
          <label for="address" class="form-label">Dirección</label>
          <input 
            type="text" 
            class="form-control" 
            id="address" 
            v-model="address" 
            placeholder="Ingresa tu dirección" 
            required 
          />
        </div>
        <div class="mb-3">
          <label for="phone" class="form-label">Número de Teléfono</label>
          <input 
            type="text" 
            class="form-control" 
            id="phone" 
            v-model="phoneNumber" 
            placeholder="Ingresa tu número de teléfono" 
            required 
          />
        </div>
        <button type="submit" class="btn btn-primary w-100">Guardar</button>
      </form>
    </div>
  </template>
  
  <script>
  import { clientService } from "@/services/client.service";
  
  export default {
    name: "CompleteRegister",
    data() {
      return {
        address: "",
        phoneNumber: "",
      };
    },
    methods: {
      async submitDetails() {
        try {
          const clientId = localStorage.getItem("clientId");
          await clientService.completeRegistration(clientId, this.address, this.phoneNumber);
          alert("Registro completado exitosamente.");
          this.$router.push("/main-page"); // Redirigir después de completar el registro
        } catch (error) {
          console.error("Error al completar el registro:", error);
          alert("Hubo un problema al guardar los datos.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .register-container {
    max-width: 400px; /* Compacta el formulario */
    margin: 0 auto;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
  }
  
  h2 {
    color: #333;
  }
  
  .register-form .form-label {
    font-weight: 500;
    color: #555;
  }
  
  .register-form .form-control {
    border-radius: 6px;
  }
  
  .register-form .form-control:focus {
    border-color: #007bff;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
  }
  
  .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    transition: background-color 0.3s ease, box-shadow 0.3s ease;
  }
  
  .btn-primary:hover {
    background-color: #0056b3;
    box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
  }
  </style>
  