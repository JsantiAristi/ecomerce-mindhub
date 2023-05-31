const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            firstName: "",
            secondName: "",
            colorCard: "",
            typeCard: "",
            thruDate: "",
            cvv: "",
            number1: "",
            number2: "",
            number3: "",
            number4: "",
            amount: "",
            emailClient: "",
        }
    },
    methods: {
        createNumberCard(){
            this.cardNumber = this.number1 + "-" + this.number2 + "-" + this.number3 + "-" + this.number4;
        },
        payCard(){
            console.log(this.typeCard); 
            console.log(this.colorCard); 
            console.log(this.cardNumber); 
            console.log(this.cvv); 
            console.log(this.thruDate); 
            console.log(this.email); 
            console.log(this.amount); 
            Swal.fire({
                title: 'Please, confirm that you want to pay with the card ' + this.cardNumber,
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Sure',
                confirmButtonColor: "#7c601893",
                preConfirm: () => {
                    return axios.post('http://localhost:8080/api/clients/current/cards/postnet',
                    {
                        "type": this.typeCard,
                        "color": this.colorCard,
                        "number": this.cardNumber,
                        "cvv": this.cvv,
                        "thruDate": this.thruDate,
                        "email" : this.emailClient,
                        "amount" : this.amount

                    })
                    .then(response => {  
                        Swal.fire({
                            icon: 'success',
                            text: 'succes paid',
                            showConfirmButton: false,
                            timer: 3000,
                        }).then( () => window.location.href="/postnet.html")
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            text: error.response.data,
                            confirmButtonColor: "#7c601893",
                        })
                        console.log(error);
                    })    
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        }
    },
    computed: {
        mayus(){
            this.firstName = this.firstName.charAt(0).toUpperCase() + this.firstName.slice(1);
            this.secondName = this.secondName.charAt(0).toUpperCase() + this.secondName.slice(1)
        }      
    }
}).mount("#app");

window.onload = function(){
    $('#onload').fadeOut();
    $('body').removeClass("hidden");
}