<template>
  <div class="client-page-container">
    <h2>Bienvenido.</h2>
    <p>Selecciona una acción:</p>
    <!-- Botones -->
    <button @click="handleViewProductsClick" class="btn btn-primary">Ver Productos</button>
    <button @click="handleLogout" class="btn btn-danger">Cerrar Sesión</button>
  </div>
</template>

<script>
export default {
  name: 'ClientPage',
  methods: {
    // Navega a la página de productos
    handleViewProductsClick() {
      this.$router.push('/products');
    },
    // Cierra sesión y redirige
    handleLogout() {
      // Limpia el token almacenado y cualquier dato de sesión
      localStorage.removeItem('jwtToken'); // Borra el token JWT
      sessionStorage.clear(); // Limpia cualquier otro dato almacenado en la sesión
      
      // Redirige al login
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.client-page-container {
  text-align: center;
  padding: 40px;
}

h2 {
  color: #333;
}

button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 1rem;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

button:hover {
  opacity: 0.8;
}
</style>
