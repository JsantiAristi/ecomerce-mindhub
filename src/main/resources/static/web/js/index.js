const { createApp } = Vue

createApp({
    data(){
        return {
          isAsideInactivo: true,
        }
    },
      methods: {
        aparecerCuenta() {
          this.isAsideInactivo = !this.isAsideInactivo;
      },
    }
}).mount("#app")