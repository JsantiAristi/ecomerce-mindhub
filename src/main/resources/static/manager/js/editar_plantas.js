const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            plantas: [],
            plantas_filtadras: [],
            nombre: "",
            descripcion: "",
            stock: "",
            precio: "",
        }
    },
    created(){
        this.cargarDatos()
    },
    methods: {
        cargarDatos(){
            axios.get('/api/plantas')
            .then(respuesta => {
                this.plantas = respuesta.data.filter(planta => planta.activo);
                console.log(this.plantas);
            })
            .catch(error => console.log(error))
        },
        changeData(id,tipo){
            this.plantas_filtadras = this.plantas.filter( planta => planta.id==id)[0]
            this.nombre = this.plantas_filtadras.nombre;
            this.descripcion = this.plantas_filtadras.descripcion;
            this.stock = this.plantas_filtadras.stock;
            this.precio = this.plantas_filtadras.precio;
            Swal.fire({
                title: 'Modifica el contenido',
                input: 'text',
                showCancelButton: true,
                confirmButtonText: 'Edit',
                showLoaderOnConfirm: true,
                preConfirm: (edit) => {
                    if( this.nombre === tipo ){
                        this.nombre = edit;
                    } else if ( this.descripcion === tipo ){
                        this.descripcion = edit;
                    } else if( this.stock === tipo ){
                        this.stock = edit;
                    } else if ( this.precio === tipo ){
                        this.precio = edit;
                    }
                    axios.put('/api/clients/products',
            {
                "id": this.plantas_filtadras.id,
                "name": this.nombre,
                "description": this.descripcion,
                "type": this.plantas_filtadras.tipoPlanta,
                "quantity": this.stock,
                "image": this.plantas_filtadras.foto,
                "price": this.precio
            })
            .then(res => { Swal.fire({
                icon: 'success',
                text: 'Cambiaste correctamente el producto ' + this.nombre,
                showConfirmButton: false,
                timer: 2000
                }).then(() => window.location.href="/pages/change_product.html")
            })
            .catch(error => { Swal.fire({
                icon: 'error',
                text: error.response.data   
                })
            })
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        },
    }
}).mount("#app")