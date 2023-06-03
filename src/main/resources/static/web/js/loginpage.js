const { createApp } = Vue

createApp({
  data() {
    return {
      isCuentaInactivo: true,
      isCarritoInactivo: true,
      carrito: [],
      totalCompra: "",
      plantas: [],
      correo: "",
      contraseña: "",
      nombre: "",
      apellido: "",
      correoNuevo: "",
      contraseñaNuevo:"",
    }
  },
  created() {
    this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
  },
  //LOADER
  mounted() {
    window.onload = function() {
      var loader = document.getElementById('loader');
      loader.style.display = 'none'; // Ocultar el loader una vez que la página haya cargado completamente
    }
  },
  methods: {
    signIn() {
      axios.post('/api/login',`email=${this.correo}&contraseña=${this.contraseña}`)
        .then(response => {
          Swal.fire({
            icon: 'success',
            text: 'Accediste correctamente a tu cuenta',
            showConfirmButton: false,
            iconColor:"#324545",
            timer: 2000,
          }).then(() => window.location.href = "/index.html")
        })
        .catch(error => {
          Swal.fire({
            icon: 'error',
            text: 'Wrong password',
            confirmButtonColor: "#7c601893",
          })
          console.log(error);
        })
    },
    crearCuenta(){
      console.log(this.correoNuevo);
      console.log(this.contraseñaNuevo);
      axios.post('/api/clientes',`nombre=${this.nombre}&apellido=${this.apellido}&email=${this.correoNuevo}&contraseña=${this.contraseñaNuevo}`)
      .then(response => 
          axios.post('/api/login',`email=${this.correoNuevo}&contraseña=${this.contraseñaNuevo}`)
          .then(response => {
            Swal.fire({
              icon: 'success',
              text: 'Creaste correctamente tu cuenta',
              showConfirmButton: false,
              iconColor:"#324545",
              timer: 2000,
            }).then(() => window.location.href = "/index.html")
          })
          .catch(error => console.log(error)))
        .catch(error => {
          Swal.fire({
            icon: 'error',
            text: error.response.data,
            confirmButtonColor: "#324545",
          })
      })
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
    sumar(id) {
      this.carrito.map(planta => {
        if (planta.id == id) {
          if (planta.stock === planta.contador) {
            planta.contador += 0
          } else {
            planta.contador += 1
          }
        }
      })
      this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
      localStorage.setItem("carrito", JSON.stringify(this.carrito));
      localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
    },
    resta(id) {
      this.carrito.map(planta => {
        if (planta.id == id) {
          if (planta.contador === 1) {
            planta.contador -= 0
          } else {
            planta.contador -= 1
          }
        }
      })
      this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
      localStorage.setItem("carrito", JSON.stringify(this.carrito));
      localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
    },
    eliminar(id) {
      this.carrito.map(planta => {
        if (planta.id == id) {
          planta.contador = 1
        }
      })
      this.carrito = this.carrito.filter(planta => !(planta.id === id))
      this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
      localStorage.setItem("carrito", JSON.stringify(this.carrito));
      localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
    },
  }
}).mount("#app")

$(function () {
  $(".btn").click(function () {
    $(".form-signin").toggleClass("form-signin-left");
    $(".form-signup").toggleClass("form-signup-left");
    $(".frame").toggleClass("frame-long");
    $(".signup-inactive").toggleClass("signup-active");
    $(".signin-active").toggleClass("signin-inactive");
    $(".forgot").toggleClass("forgot-left");
    $(this).removeClass("idle").addClass("active");
  });
});