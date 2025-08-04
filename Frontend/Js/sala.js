// Seleccionar las cartas por clase (no por ID duplicado)
const cartas = document.querySelectorAll(".carta");

cartas.forEach(carta => {
    carta.addEventListener('click', function () {
        let cantidad = parseInt(carta.getAttribute('data-id'));
        localStorage.setItem('cantidadJugadores', cantidad);
        crearInputsJugadores(cantidad);
    });
});

// Función para crear inputs dinámicos
function crearInputsJugadores(cantidad) {
    let container = document.getElementById('inputs-jugadores');
    if (container) container.remove();

    container = document.createElement('div');
    container.id = 'inputs-jugadores';

    for (let i = 1; i <= cantidad; i++) {
        const inputDiv = document.createElement('div');
        inputDiv.className = 'mb-3';
        inputDiv.innerHTML = `
            <div class="inputLabel">
                <label for="jugador${i}" class="form-label">Nombre del jugador ${i}:</label>
                <input type="text" class="form-control" id="jugador${i}" name="jugador${i}">
            </div>
        `;
        container.appendChild(inputDiv);
    }

    // Agrega el botón de Jugar
    const btnDiv = document.createElement('div');
    btnDiv.style.display = "flex";
    btnDiv.style.justifyContent = "center";
    btnDiv.style.marginTop = "20px";
    btnDiv.innerHTML = `
        <button id="btnJugar" style="
            background: #2EEF51;
            color: #222;
            font-weight: bold;
            border: 2px solid #222;
            border-radius: 8px;
            padding: 8px 32px;
            font-size: 1.2rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            cursor: pointer;
            transition: background 0.2s;
        ">Jugar</button>
    `;
    container.appendChild(btnDiv);

    document.querySelector('.salaJugadores').appendChild(container);

    // Asociar evento al botón
    document.getElementById('btnJugar').addEventListener('click', iniciarJuego);
}

// Función para iniciar el juego
async function iniciarJuego() {
    const cantidad = parseInt(localStorage.getItem('cantidadJugadores') || '0', 10);
    const nombres = [];

    for (let i = 1; i <= cantidad; i++) {
        const input = document.getElementById(`jugador${i}`);
        const nombre = input?.value.trim();
        if (!nombre) {
            alert(`Por favor ingresa el nombre del jugador ${i}.`);
            return;
        }
        nombres.push(nombre);
    }

    try {
        const gameData = await crearSala(cantidad);
        const gameId = gameData.id;
        console.log('Partida creada, ID =', gameId);

        const playerIds = [];
        for (const nombre of nombres) {
            const player = await crearJugador(nombre);
            playerIds.push(player.id);
        }

        const respuesta = await asignarJugadoresASala(gameId, playerIds);
        console.log("Jugadores asignados:", respuesta.data);

        localStorage.setItem('gameId', gameId);
        localStorage.setItem('jugadores', JSON.stringify(nombres));
        localStorage.setItem('playerIds', JSON.stringify(playerIds));

        window.location.href = 'Juego.html';

    } catch (err) {
        console.error(err);
        alert(`No se pudo iniciar la partida:\n${err.message}`);
    }
}

// Función para crear la sala
async function crearSala(cantidad) {
    const gameTime = "00:5:00"; // Tiempo fijo de 2 minutos (puedes hacerlo dinámico si quieres)
    const res = await fetch('http://localhost:8080/game/CreateGame', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            cuantityPlayers: cantidad,
            gameTime: gameTime
        })
    });

    let dto;
    try {
        dto = await res.json();
    } catch {
        throw new Error(`Respuesta inválida del servidor (no JSON). HTTP ${res.status}`);
    }

    if (!res.ok) {
        const msg = dto?.message || `Error desconocido al crear la partida`;
        throw new Error(`HTTP ${res.status}: ${msg}`);
    }

    if (!dto.data || typeof dto.data.id !== 'number') {
        throw new Error(`La respuesta no incluye data.id (payload: ${JSON.stringify(dto)})`);
    }

    return dto.data;
}

// Función para crear un jugador
async function crearJugador(playerName) {
    const res = await fetch('http://localhost:8080/Player/CreatePlayer', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ playerName })
    });

    let data;
    try {
        data = await res.json();
    } catch {
        throw new Error(`Respuesta inválida al crear jugador ${playerName}`);
    }

    if (!res.ok) {
        const msg = data?.message || '';
        throw new Error(`Error al crear jugador ${playerName}: HTTP ${res.status} ${msg}`);
    }

    return data;
}

// Función para asignar jugadores a la sala
async function asignarJugadoresASala(gameId, playerIds) {
    const res = await fetch(`http://localhost:8080/playergames/assign/${gameId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(playerIds)
    });

    let dto;
    try {
        dto = await res.json();
    } catch {
        throw new Error(`Respuesta inválida al asignar jugadores a la sala ${gameId}`);
    }

    if (!res.ok) {
        const msg = dto?.message || `Error desconocido al asignar jugadores a la sala`;
        throw new Error(`HTTP ${res.status}: ${msg}`);
    }

    return dto;
}
