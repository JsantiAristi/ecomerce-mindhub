const { createApp } = Vue

createApp({
    data() {
        return {
            mostrarImagen: 1,
        };
    },
    created() {
    },
    methods: {
        mostrar1() {
            this.mostrarImagen = 1;
          },
          mostrar2() {
            this.mostrarImagen = 2;
          },
          mostrar3() {
            this.mostrarImagen = 3;
          }
    }

}).mount('#app')