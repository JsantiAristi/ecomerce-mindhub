const { createApp } = Vue

createApp({
  data() {
    return {
      isCuentaInactivo: true,
      isCarritoInactivo: true,
      carrito: [],
    }
  },
  created() {
    this.cargarDatosCarrito()
  },
  methods: {
    cargarDatosCarrito(){
      axios.get("/api/carrito")
      .then(response => {
        this.carrito = response.data;
        console.log(this.carrito);
      })
      .catch(error => console.log(error))
    },
    aparecerCuenta() {
      if (this.isCarritoInactivo) {
        this.isCuentaInactivo = !this.isCuentaInactivo;
      } else {
        this.isCarritoInactivo = !this.isCarritoInactivo;
        this.isCuentaInactivo = !this.isCuentaInactivo;
      }

    },
    aparecerCarrito() {
      if (this.isCuentaInactivo) {
        this.isCarritoInactivo = !this.isCarritoInactivo;
      } else {
        this.isCuentaInactivo = !this.isCuentaInactivo;
        this.isCarritoInactivo = !this.isCarritoInactivo;
      }
    },
  }
}).mount("#app")