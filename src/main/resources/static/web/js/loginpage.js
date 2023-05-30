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
  methods: {
    signIn() {
      axios.post('/api/login',`email=${this.correo}&contraseña=${this.contraseña}`)
        .then(response => {
          Swal.fire({
            icon: 'success',
            text: 'Accediste correctamente a tu cuenta',
            showConfirmButton: false,
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
      axios.post('/api/clientes',`nombre=${this.nombre}&apellido=${this.apellido}&email=${this.correoNuevo}&contraseña=${this.contraseñaNuevo}`)
      .then(response => {
        Swal.fire({
          icon: 'success',
          text: 'Accediste correctamente a tu cuenta',
          showConfirmButton: false,
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