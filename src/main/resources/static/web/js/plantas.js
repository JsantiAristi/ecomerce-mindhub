const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
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
            categoria: new URLSearchParams(location.search).get("categoria")
        }
    },
    created() {
        this.cargarDatos()
        this.cargarCliente()
    },
    methods: {
        cargarDatos() {
            axios.get('/api/productos/'+this.categoria)
                .then(respuesta => {
                    this.plantas = respuesta.data.filter(planta => planta.activo);
                    console.log(this.categoria);
                    
                    for(planta of this.plantas){
                        planta.contador = 1
                    }

                    this.plantas_filtradas = this.plantas;
                    this.cantidad_plantas = this.plantas.length;
                    this.tipo_plantas = Array.from(new Set(this.plantas.map(planta => planta.tipoProducto)));
                })
                .catch(error => console.log(error))
            this.carrito = JSON.parse(localStorage.getItem("carrito")) || [];
            this.totalCompra = JSON.parse(localStorage.getItem("totalCompra")) || 0;
        },
        cargarCliente() {
            axios.get('/api/clientes/actual')
              .then(respuesta => {
                this.cliente = respuesta.data;
                console.log(this.cliente);
              })
              .catch(error => {
                this.cliente = []
                console.log(this.cliente);
                console.log(error)
              })
          },
        filtro_tipo(planta) {
            return planta.tipoProducto.includes(this.tipo_planta);
        },
        filtro_precio(planta) {
            return planta.precio <= this.rango_precio;
        },
        filtros() {
            this.plantas_filtradas = this.plantas.filter(planta => {
                return (this.filtro_tipo(planta) && this.filtro_precio(planta));
            })
        },
        filtro_plantas() {
            if (this.filtros_arreglo == 2) {
                this.plantas_filtradas.sort((planta1, planta2) => {
                    return (planta1.precio - planta2.precio);
                })
            } else if (this.filtros_arreglo == 1) {
                this.plantas_filtradas.sort((planta1, planta2) => {
                    return (planta2.precio - planta1.precio);
                })
            }
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
                    text: 'Se ha agregado a su carrito de comptras',
                    background:' rgb(238 243 236)',
                    confirmButtonColor: " #324545",
                    iconColor:"#324545",
                    
                  })
                this.carrito.push(this.filtro_planta_carrito);
                this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
            }else{
                Swal.fire({
                    icon: 'info',
                    title: 'Usted ya añadio este producto al carrito de compras',
                    text: 'Para añadir más unidades diríjase al carrito de compras!',
                    background:' rgb(238 243 236)',
                    confirmButtonColor: " #324545",
                    iconColor:"#324545",
                  })
            }                
        },
        sumar(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    if (planta.stock === planta.contador) {
                        planta.contador += 0
                    } else {
                        planta.contador += 1
                    }                    
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        resta(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    if (planta.contador === 1) {
                        planta.contador -= 0
                    } else {
                        planta.contador -= 1
                    }                 
                }
            })
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        eliminar(id){
            this.carrito.map(planta => {
                if(planta.id == id){
                    planta.contador = 1             
                }
            })
            this.carrito = this.carrito.filter(planta => !(planta.id === id))
            this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
            localStorage.setItem("carrito", JSON.stringify(this.carrito));
            localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
        },
        crearOrden(){
            axios.post("/api/cliente/orden")
            .then(response => {
                console.log(response);
                
                for( producto of this.carrito ){
                    this.añadirProducto(producto)
                }   

                this.carrito = [];
                this.totalCompra = this.carrito.reduce((acumulador, prod)=> acumulador += (prod.precio * prod.contador), 0)
                localStorage.setItem("carrito", JSON.stringify(this.carrito));
                localStorage.setItem("totalCompra", JSON.stringify(this.totalCompra))
                window.location.href="/web/paginas/pedidos.html"   
            })
            .catch(error => console.log(error))
            
        },
        añadirProducto(producto){
            axios.post("/api/cliente/carrito",{
                "id":producto.id,
                "unidadesSeleccionadas":producto.contador
            })
            .then(respuesta => console.log(respuesta))
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
    }
}).mount("#app")