const { createApp } = Vue

createApp({
  data() {
    return {
      isCuentaInactivo: true,
      isCarritoInactivo: true,
      carrito: [],
      totalCompra: "",
      plantas: [],
    }
  },
  created() {
    this.cargarDatos();
    this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
  },
  methods: {
    cargarDatos() {
      axios.get('/api/plantas')
          .then(respuesta => {
              this.plantas = respuesta.data.filter(planta => planta.activo);
              console.log(this.plantas);
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



// $(function() {
//     $(".btn").click(function() {
//        $(".form-signin").toggleClass("form-signin-left");
//      $(".form-signup").toggleClass("form-signup-left");
//      $(".frame").toggleClass("frame-long");
//      $(".signup-inactive").toggleClass("signup-active");
//      $(".signin-active").toggleClass("signin-inactive");
//      $(".forgot").toggleClass("forgot-left");   
//      $(this).removeClass("idle").addClass("active");
//     });
//  });
 
//  $(function() {
//     $(".btn-signup").click(function() {
//    $(".nav").toggleClass("nav-up");
//    $(".form-signup-left").toggleClass("form-signup-down");
//    $(".success").toggleClass("success-left"); 
//    $(".frame").toggleClass("frame-short");
//     });
//  });
 
//  $(function() {
//     $(".btn-signin").click(function() {
//    $(".btn-animate").toggleClass("btn-animate-grow");
//    $(".welcome").toggleClass("welcome-left");
//    $(".cover-photo").toggleClass("cover-photo-down");
//    $(".frame").toggleClass("frame-short");
//    $(".profile-photo").toggleClass("profile-photo-down");
//    $(".btn-goback").toggleClass("btn-goback-up");
//    $(".forgot").toggleClass("forgot-fade");
//     });
//  });