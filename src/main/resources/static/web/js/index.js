const { createApp } = Vue

createApp({
  data() {
    return {
      isLoading: true,
      plantas: [],
            cliente: [],
            plantas_filtradas: [],
            tipo_plantas: [],
            tipo_planta: "",
            cantidad_plantas: 0,
            rango_precio: 5000,
            filtros_arreglo: "",
            isCuentaInactivo: true,
            isCarritoInactivo: true,
            carrito: [],
            contadorCarrito: 0,
            filtro_planta_carrito: [],
            cantidad: "",
            plantaId: [],
            totalCompra: 0,
            plantafilter: [],
            favoritos:[],
            categoria: new URLSearchParams(location.search).get("categoria")
    }
  },
  
  created() {
    this.cargarDatos();
    this.cargarCliente();
    this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
    this.favoritos = JSON.parse(localStorage.getItem("favoritos")) || [];
  },
  //LOADER
  mounted() {
    window.onload = function() {
      var loader = document.getElementById('loader');
      loader.style.display = 'none'; // Ocultar el loader una vez que la página haya cargado completamente
    }
  },
  methods: {
    cargarDatos() {
      axios.get('/api/productos')
        .then(respuesta => {
          this.plantas = respuesta.data.filter(producto => producto.activo && producto.stock > 0 );

          for(planta of this.plantas){
            planta.contador = 1
        }
        
        })
        .catch(error => console.log(error))
    },
    cargarCliente() {
      axios.get('/api/clientes/actual')
        .then(respuesta => {
          this.cliente = respuesta.data;
        })
        .catch(error => {
          this.cliente = []
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
    añadirCarrito(id){
      this.filtro_planta_carrito = this.plantas.filter(planta => planta.id == id)[0];  
      if (!(this.carrito.some(planta => planta.id == id))) {
        Swal.fire({
          icon: 'success',
          title: 'Producto añadido',
          text: 'Se ha agregado a su carrito de compras',
          background:' rgb(238 243 236)',
          confirmButtonColor: "#324545",
          iconColor:"#324545",
          
        })
          this.carrito.push(this.filtro_planta_carrito);
          this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
          localStorage.setItem("carrito", JSON.stringify(this.carrito));
          localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
      } else{
        Swal.fire({
          icon: 'info',
          title: 'Usted ya añadió este producto al carrito de compras',
          text: 'Para añadir más unidades diríjase al carrito de compras!',
          background:' rgb(238 243 236)',
          confirmButtonColor: " #324545",
          iconColor:"#324545",
          
        })
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
    crearOrden() {
      axios.post("/api/cliente/orden", `idCliente=${1}`)
        .then(response => {
          for (producto of this.carrito) {
            axios.post("/api/cliente/carrito",
              {
                "id": producto.id,
                "idCliente": 1,
                "unidadesSeleccionadas": producto.contador,
              })
              .then(respuesta => {
                this.carrito = [];
                this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                window.location.href = "/web/paginas/pedidos.html"
              })
              .catch(error => {
                Swal.fire({
                  icon: 'error',
                  text: error.response.data,
                  background:' rgb(238 243 236)',
                  confirmButtonColor: " #324545",
                  iconColor:"#324545",                  
                })
              })
          }
        })
        .catch(error => console.log(error))
    },
    logout() {
      Swal.fire({
        title: 'Esta seguro de cerrar sesión?',
        text: "Confirmar",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#324545',
        cancelButtonColor: '#db3939',
        background:' rgb(238 243 236)',
        iconColor:"#324545",
        confirmButtonText: 'Si, cerrar sesión!',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.post('/api/logout')
          .then(response => {
            this.carrito = [];
            this.totalCompra = this.carrito.reduce((acumulador, prod) => acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
            window.location.href = '/index.html'
          })
          .catch(error => console.log(error))
        }
      })
    },
    borrarFavoritos(){
      this.favoritos = []
      localStorage.setItem("favoritos", JSON.stringify(this.favoritos))
    },
    handleFav(){
      localStorage.setItem("favoritos", JSON.stringify(this.favoritos))
    },
  }
}).mount("#app")