const cartas = document.querySelectorAll(".carta");

cartas.forEach(carta => {
    carta.addEventListener('click', () => {
        const cantidad = parseInt(carta.getAttribute('data-id'), 10);
        localStorage.setItem('cantidadJugadores', cantidad);
        crearInputsJugadores(cantidad);
    });
});

function crearInputsJugadores(cantidad) {
    const contenedorViejo = document.getElementById('inputs-jugadores');
    if (contenedorViejo) contenedorViejo.remove();

    const container = document.createElement('div');
    container.id = 'inputs-jugadores';

    for (let i = 1; i <= cantidad; i++) {
        const inputDiv = document.createElement('div');
        inputDiv.className = 'mb-3';
        inputDiv.innerHTML = `
            <label for="jugador${i}" class="form-label">Nombre del jugador ${i}:</label>
            <input type="text" class="form-control" id="jugador${i}" name="jugador${i}" placeholder="Jugador ${i}">
        `;
        container.appendChild(inputDiv);
    }

    const btnDiv = document.createElement('div');
    btnDiv.style.display = "flex";
    btnDiv.style.justifyContent = "center";
    btnDiv.style.marginTop = "20px";
    btnDiv.innerHTML = `
        <button id="btnJugar" style="
            background: z#2EEF51;
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

    const salaJugadores = document.querySelector('.salaJugadores');
    salaJugadores.appendChild(container);

    document.getElementById('btnJugar').addEventListener('click', iniciarJuego);
}

async function iniciarJuego() {
    const cantidad = parseInt(localStorage.getItem('cantidadJugadores') || '0', 10);
    if (cantidad < 2) {
        alert('Selecciona la cantidad de jugadores primero.');
        return;
    }

    const nombres = [];
    for (let i = 1; i <= cantidad; i++) {
        const input = document.getElementById(`jugador${i}`);
        if (!input) {
            alert(`No se encontró el input del jugador ${i}.`);
            return;
        }
        const nombre = input.value.trim();
        if (!nombre) {
            alert(`Por favor ingresa el nombre del jugador ${i}.`);
            return;
        }
        nombres.push(nombre);
    }

    try {
        // Paso 1: Crear jugadores
        const playerIds = [];
        for (const nombre of nombres) {
            const player = await crearJugador(nombre);
            if (!player || typeof player.playerID !== 'number') {
                throw new Error(`No se pudo crear el jugador ${nombre}`);
            }
            playerIds.push(player.playerID);
            await new Promise(res => setTimeout(res, 200)); // delay opcional
        }

        // Paso 2: Crear sala
        const gameDTOs = await crearSala(cantidad);
        if (!Array.isArray(gameDTOs) || gameDTOs.length === 0 || typeof gameDTOs[0].gameID !== 'number') {
            throw new Error('No se pudo obtener el ID de la sala');
        }
        const gameID = gameDTOs[0].gameID;

        // Paso 3: Asignar jugadores a la sala
        const respuesta = await asignarJugadoresASala(gameID, playerIds);

        // Guardar en localStorage para usar en Juego.html
        localStorage.setItem('gameId', gameID);
        localStorage.setItem('jugadores', JSON.stringify(nombres));
        localStorage.setItem('playerIds', JSON.stringify(playerIds));

        // Redirigir al juego
        window.location.href = 'Juego.html';

    } catch (err) {
        console.error(err);
    }
}
async function crearJugador(playerName) {
    try {
        const res = await fetch('http://localhost:8080/Player/CreatePlayer', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ playerName })
        });

        const dto = await res.json();
        console.log('Respuesta crearJugador:', dto);

        if (!res.ok || !dto.data || typeof dto.data.playerID !== 'number') {
            throw new Error(dto.message || 'Error al crear jugador');
        }

        return dto.data;

    } catch (error) {
        console.error(`Error creando jugador "${playerName}":`, error);
        throw error;
    }
}



async function crearSala(cantidad) {
    const gameTime = "00:05:00";
    const res = await fetch('http://localhost:8080/game/CreateGame', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            cuantityPlayers: cantidad,
            gameTime: gameTime
        })
    });

    if (!res.ok) {
        const errorData = await res.json().catch(() => ({}));
        throw new Error(errorData.message || 'Error al crear la sala');
    }

    const dto = await res.json();

    if (!Array.isArray(dto.data)) {
        throw new Error(`Respuesta inválida al crear la sala: ${JSON.stringify(dto)}`);
    }

    return dto.data;
}
async function asignarJugadoresASala(gameId, playerIds) {
    // Validar que gameId sea número
    if (typeof gameId !== 'number' || isNaN(gameId)) {
        throw new Error('gameId inválido para asignar jugadores');
    }

    // Validar que playerIds sea array y solo contenga números
    if (!Array.isArray(playerIds) || playerIds.some(id => typeof id !== 'number')) {
        throw new Error('playerIds debe ser un array de números');
    }

    console.log('Asignando jugadores:', playerIds, 'a la sala:', gameId);

    const res = await fetch(`http://localhost:8080/playergames/assign/${gameId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(playerIds)  // Esto envía el array [1,2,3] tal cual
    });

    if (!res.ok) {
        const errorData = await res.json().catch(() => ({}));
        throw new Error(errorData.message || 'Error al asignar jugadores');
    }

    const dto = await res.json();
    console.log('Respuesta backend asignar jugadores:', dto);

    return dto;
}
