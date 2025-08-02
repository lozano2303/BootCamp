//Obtención de cartas 
const cartas = document.querySelectorAll("#cartaSeleccion");


cartas.forEach(cartas =>{
    cartas.addEventListener('click' , function(){
        let cantidad  = parseInt(cartas.getAttribute('data-id'));
        localStorage.setItem('cantidadJugadores' , cantidad);
        crearInputsJugadores(cantidad)
    })
})

//Funcion para crear los inputs
function crearInputsJugadores(cantidad) {
    // Elimina inputs anteriores si existen
    let container = document.getElementById('inputs-jugadores');
    if (container) {
        container.remove();
    }

    // Crea un nuevo contenedor
    container = document.createElement('div');
    container.id = 'inputs-jugadores';

    for (let i = 1; i <= cantidad; i++) {
        const inputDiv = document.createElement('div');
        inputDiv.className = 'mb-3';
        inputDiv.innerHTML = `
        <div class="inputLabel" >
            <label for="jugador${i}" class="form-label">Nombre del jugador ${i}:</label>
            <input type="text" class="form-control" id="jugador${i}" name="jugador${i}">
        <div>
        `;
        container.appendChild(inputDiv);
    }

    // Agrega el contenedor dentro de .salaJugadores
    document.querySelector('.salaJugadores').appendChild(container);
}
